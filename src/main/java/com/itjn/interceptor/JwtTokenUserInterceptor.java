package com.itjn.interceptor;

import com.itjn.common.context.BaseContext;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.common.properties.JwtProperties;
import com.itjn.exception.BusibessException;
import com.itjn.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * jwt校验
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //跨域OPTIONS请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            //response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        //System.out.println("当前线程的id：" + Thread.currentThread().getId());

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Integer userId = Integer.valueOf(claims.get("userId").toString());
            //log.info("当前用户的id：", userId);
            BaseContext.setCurrentId(userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {//如果在JWT校验和解析过程中发生异常（例如，JWT无效、过期、签名错误等），则捕获异常。
            //4、不通过，响应401状态码
            //response.setStatus(401);(不用加!!!)
            throw new BusibessException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    /*public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //用完之后，移除线程变量中的数据，防止内存泄露
        BaseContext.removeCurrentId();
    }*/


}
