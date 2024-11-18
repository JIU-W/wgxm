package com.itjn;

import cn.yam.sychatsdk.config.SyChatProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@EnableAsync        //开启异步调用
@EnableScheduling   //开启定时任务
@SpringBootApplication
@MapperScan("com.itjn.mapper")
//@ComponentScan("com.itjn.common.config")
@Slf4j
@EnableTransactionManagement //开启注解方式的事务管理
@EnableConfigurationProperties(SyChatProperties.class)//扫描读取yaml配置
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
