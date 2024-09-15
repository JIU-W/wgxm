package com.itjn.common.aspect;

import cn.hutool.core.thread.threadlocal.NamedThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.itjn.common.annotation.Log;
import com.itjn.common.context.BaseContext;
import com.itjn.domain.entity.SysOperLog;
import com.itjn.common.enums.BusinessStatus;
import com.itjn.domain.entity.User;
import com.itjn.service.SysOperLogService;
import com.itjn.service.UserInfoService;
import com.itjn.utils.IpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 日志切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    /**
     * 定义需要排除在日志记录之外的属性名称数组
     */
    private static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    @Autowired
    private SysOperLogService sysOperLogService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 使用ThreadLocal维护一个线程局部变量，用于记录操作的耗时
     */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");



    /**
     * 返回通知
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        //调用处理日志的方法
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 异常通知
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(pointcut = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    /**
     * 处理请求前执行，此方法旨在记录方法的开始时间。
     *
     * @param joinPoint     切点
     * @param controllerLog 一个注解对象，表示目标方法上标注的注解。这里用于判断方法是否应该被此切面处理。
     */
    @Before(value = "@annotation(controllerLog)")
    public void boBefore(JoinPoint joinPoint, Log controllerLog) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理操作日志的逻辑。
     * 当方法执行完毕或发生异常时，此方法用于封装和记录操作日志。
     *
     * @param joinPoint     切点，用于获取目标方法的信息。
     * @param controllerLog 控制器上的日志注解，用于获取方法描述等信息。
     * @param e             异常对象，如果方法执行过程中抛出异常。
     * @param jsonResult    方法返回的对象，用于日志记录，此参数可能为null。
     */
    private void handleLog(JoinPoint joinPoint, Log controllerLog, Exception e, Object jsonResult) {
        try {
            // 获取当前请求的属性，包括HttpServletRequest对象。
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            // 如果请求属性为空，则直接返回，不处理日志。
            if (requestAttributes == null) {
                return;
            }
            // 将请求属性转换为ServletRequestAttributes，以便获取HttpServletRequest对象。
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            // 获取HttpServletRequest对象。
            HttpServletRequest request = servletRequestAttributes.getRequest();

            // 重新获取请求属性，目的是为了后续获取请求方法等信息。
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes http = (ServletRequestAttributes) attributes;
            // 再次获取HttpServletRequest对象。
            HttpServletRequest httpServletRequest = http.getRequest();

            // 创建SysOperLog对象，用于存储操作日志的信息。
            SysOperLog sysOperLog = new SysOperLog();
            // 默认设置操作状态为正常。
            sysOperLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 如果方法执行过程中抛出异常，则将操作状态设置为异常。
            if (e != null) {
                // 设置状态为异常
                sysOperLog.setStatus(BusinessStatus.FAIL.ordinal());
                // 设置异常信息。
                sysOperLog.setErrorMsg(e.getMessage());
            }
            // 获取ip地址
            String ipAddress = IpUtil.getIpAddress(request);
            // 设置ip地址
            sysOperLog.setOperIp(ipAddress);
            // 设置请求地址
            sysOperLog.setOperUrl(request.getRequestURI());

            //获取当前登录的用户信息。
            if(BaseContext.getCurrentId() != null){
                Integer currentUserId = BaseContext.getCurrentId();
                User currentUser = userInfoService.selectById(currentUserId);
                //获取用户名
                String username = currentUser.getUserName();
                //设置操作者名称,设置操作人员
                sysOperLog.setOperName(username);
            }else{
                //因为登录,注册和获取图片验证码接口不被JWT拦截器拦截,(只有接口被拦截后当前用户id才会存入ThreadLocal)
                //走这三个接口的时候不能拿到userId，所以走这三个接口的日志信息的“操作人员”字段暂时存不了。
                //其他接口的都可以。
                sysOperLog.setOperName("目前未知");
            }

            // 获取并设置请求方法，例如GET、POST等。
            sysOperLog.setRequestMethod(request.getMethod());

            // 获取目标对象的类名。
            String className = joinPoint.getTarget().getClass().getName();
            // 获取方法名
            String methodName = joinPoint.getSignature().getName();
            // 设置方法名称
            sysOperLog.setMethod(className + "." + methodName + "()");
            // 获取注解中对方法的描述信息
            getControllerMethodDescription(joinPoint, controllerLog, jsonResult, sysOperLog);
            // 计算执行时长(毫秒)
            long executeTime = System.currentTimeMillis() - TIME_THREADLOCAL.get();
            sysOperLog.setExecuteTime(executeTime);
            // 设置操作时间。
            sysOperLog.setOperTime(new Date());
            // 保存操作日志
            sysOperLogService.insert(sysOperLog);
        } catch (Exception ex) {
            // 记录处理日志过程中发生的异常。
            ex.printStackTrace();
        }
    }

    /**
     * 从注解中获取控制器方法的描述信息，并填充到操作日志对象中。
     *
     * @param joinPoint     切点对象，用于获取方法名和参数信息。
     * @param controllerLog 控制器日志注解对象，包含标题、业务类型等配置信息。
     * @param jsonResult    方法的返回结果，用于判断是否需要记录响应数据。
     * @param sysOperLog    系统操作日志对象，此处将从controllerLog中获取的信息填充到该对象中。
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log controllerLog, Object jsonResult, SysOperLog sysOperLog) {
        //设置操作模块
        sysOperLog.setTitle(controllerLog.title());
        //设置业务类型
        sysOperLog.setBusinessType(controllerLog.businessType().name());

        // 判断是否需要保存请求数据，如果需要，则调用setRequestValue方法进行处理
        if (controllerLog.isSaveRequestData()) {
            //调用设置请求数据的方法
            setRequestValue(joinPoint, sysOperLog, controllerLog.excludeParamNames());
        }

        // 判断是否需要保存响应数据且返回结果不为空，如果满足条件，则将返回结果转为JSON字符串并保存到操作日志中
        if (controllerLog.isSaveResponseData() && !StringUtils.isEmpty(jsonResult)) {
            //设置响应数据
            sysOperLog.setJsonResult(JSON.toJSONString(jsonResult));
        }
    }


    /**
     * 设置操作日志的请求参数信息。
     *
     * @param joinPoint         切点，用于获取方法参数。
     * @param operLog           操作日志对象，用于设置请求参数信息。
     * @param excludeParamNames 需要排除的参数名数组，这些参数不会被记录在日志中。
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog, String[] excludeParamNames) {
        // 获取当前请求的属性
        Map<String, String[]> parameterMap = getParameterMap();
        // 如果参数不为空且不为空集合
        if (parameterMap != null && !parameterMap.isEmpty()) {
            // 将参数转换为JSON字符串，通过excludePropertyPreFilter过滤掉不需要记录的参数
            String params = JSONObject.toJSONString(parameterMap, excludePropertyPreFilter(excludeParamNames));
            // 设置操作日志的请求参数，截取前2000个字符以防止过长
            operLog.setOperParam(org.apache.commons.lang3.StringUtils.substring(params, 0, 2000));
        } else {
            // 如果请求参数为空，尝试从方法参数中获取信息
            Object args = joinPoint.getArgs();
            // 如果方法参数不为空
            if (args != null) {
                // 将方法参数转换为字符串，同样支持排除某些参数名
                String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
                // 设置操作日志的请求参数，同样截取前2000个字符
                operLog.setOperParam(org.apache.commons.lang3.StringUtils.substring(params, 0, 2000));
            }
        }
    }

    /**
     * 获取当前HTTP请求的参数
     *
     * @return 一个Map，映射参数名称到参数值数组。这允许处理多值参数。
     */
    private static Map<String, String[]> getParameterMap() {
        // 从Spring的RequestContextHolder中获取当前请求的属性
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 将RequestAttributes强制转换为ServletRequestAttributes，以便访问HTTP请求特定的属性
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        // 从ServletRequestAttributes中获取当前HTTP请求对象
        HttpServletRequest request = (HttpServletRequest) servletRequestAttributes.getRequest();
        // 获取请求的所有参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap;
    }


    /**
     * 忽略敏感属性
     *
     * @param excludeParamNames 需要排除的参数名数组
     * @return {@link PropertyPreFilters.MySimplePropertyPreFilter }
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter(String[] excludeParamNames) {
        return new PropertyPreFilters().addFilter().addExcludes(ArrayUtils.addAll(EXCLUDE_PROPERTIES, excludeParamNames));
    }

    /**
     * 将对象数组转换为字符串，排除指定的参数名（敏感参数）。
     *
     * @param paramsArray       参数数组，可以包含任意类型的对象。
     * @param excludeParamNames 需要排除的参数名数组，这些参数不会被转换为字符串。
     * @return 返回转换后的参数字符串，各参数间以空格分隔。
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        // 使用StringBuilder来构建最终的参数字符串
        StringBuilder params = new StringBuilder();
        // 检查参数数组是否为空或长度为0，避免不必要的处理
        if (paramsArray != null) {
            // 遍历参数数组中的每个对象
            for (Object o : paramsArray) {
                // 检查对象是否为空且不属于被过滤的类型
                if (o != null && !isFilterObject(o)) {
                    try {
                        // 将对象转换为JSON字符串，排除指定的属性
                        Object jsonObj = JSONObject.toJSONString(o, excludePropertyPreFilter(excludeParamNames));
                        // 将转换后的JSON字符串追加到参数字符串中，并以空格分隔各个参数
                        params.append(jsonObj).append(" ");
                    } catch (Exception ignored) {
                        // 忽略转换过程中的异常，确保方法的健壮性
                    }
                }
            }
        }
        return params.toString().trim();
    }


    /**
     * 判断传入的对象是否需要被过滤。
     * 这个方法主要用于处理上传文件时，判断接收的参数是否为文件类型或其他特定类型。
     *
     * @param o 待检查的对象
     * @return 如果对象需要被过滤（即对象为MultipartFile或其他特定类型），则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        // 获取对象的类类型
        Class<?> clazz = o.getClass();

        // 检查对象是否为数组类型
        if (clazz.isArray()) {
            // 如果数组的组件类型可以被MultipartFile类转换，则返回true
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            // 如果对象是集合类型，将其转换为Collection接口实例
            Collection collection = (Collection) o;
            // 遍历集合中的每个元素，如果任意元素是MultipartFile实例，则返回true
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            // 如果对象是Map类型，将其转换为Map接口实例
            Map map = (Map) o;
            // 遍历Map中的每个条目，如果任意条目的值是MultipartFile实例，则返回true
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        // 如果对象不是数组、集合或Map类型，检查它是否为MultipartFile、HttpServletRequest、HttpServletResponse或BindingResult实例
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
