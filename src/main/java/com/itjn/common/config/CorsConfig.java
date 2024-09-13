package com.itjn.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//跨域配置
@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ComponentScan("com.itjn.common.config")
@Slf4j
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
                //设置允许跨域的路径
        registry.addMapping("/**")//任意路径
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")//任意域名
                //是否允许cookie
                .allowCredentials(true)
                //设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT","OPTIONS")
                //设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }

}
