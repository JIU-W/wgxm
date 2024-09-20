package com.itjn.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


//配置类WebMvcConfiguration已经配置了跨域，这里就不再配置了。
/**
 * 跨域配置
 */
/*@Configuration
@Slf4j
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        log.info("开始进行跨域配置");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 允许所有源地址
        corsConfiguration.addAllowedHeader("*"); // 2 允许所有请求头
        corsConfiguration.addAllowedMethod("*"); // 3 允许所有请求方法
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}*/
