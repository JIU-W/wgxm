package com.itjn.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/*通过使用@ConfigurationProperties注解。
注解允许将YML/JSON/Properties文件中的配置属性绑定到一个Java对象上*/
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "wgxm.jwt")
@Data
public class JwtProperties {

    /**
     * 用户端用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

    /**
     * 管理端用户人员生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

}
