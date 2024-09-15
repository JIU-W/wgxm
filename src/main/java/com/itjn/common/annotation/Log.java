package com.itjn.common.annotation;

import com.itjn.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 */        //@Log可以应用于方法（METHOD）或方法的参数（PARAMETER）上
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)//@Retention指定了注解的生命周期为运行时，意味着可以在运行时通过反射访问。
@Documented
public @interface Log {
    /**
     * 模块名称
     */
    String title() default "";

    /**
     * 业务操作类型
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应数据
     */
    boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    public String[] excludeParamNames() default {};

}
