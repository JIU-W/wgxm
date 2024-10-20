package com.itjn.common.config;

import com.itjn.interceptor.JwtTokenAdminInterceptor;
import com.itjn.interceptor.JwtTokenUserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 注册自定义拦截器
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {

        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**",
                        "/webjars/**", "/v2/**", "/v3/**", "/swagger-ui.html/**",
                        "/doc.html/**")            //排除Swagger相关路径
                .excludePathPatterns("/sdk/chat")//sy-chat
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/checkCode")
                .excludePathPatterns("/file/getAvatar/**")
                .excludePathPatterns("/file/uploadAvatar")
                .excludePathPatterns("/**/*.{OPTIONS}"); //排除所有的 OPTIONS 请求

        /*registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");*/

    }


    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射...");      //swagger文档得以访问
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 全局跨域配置
     * @param registry
     */
    protected void addCorsMappings(CorsRegistry registry) {
        log.info("开始配置全局跨域...");
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



    /*
     * 扩展spring mvc框架的消息转换器
     *
     * */
    /*@Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }*/

}
