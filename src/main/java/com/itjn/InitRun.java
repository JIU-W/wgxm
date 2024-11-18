package com.itjn;

import com.itjn.exception.BusibessException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @description 启动项目时，执行该类
 * @author JIU-W
 * @date 2024-11-17
 * @version 1.0
 */

@Slf4j
@Component("initRun")
public class InitRun implements ApplicationRunner {

    @Resource
    private DataSource dataSource;

    //@Resource
    //private RedisComponent redisComponent;

    @Override
    public void run(ApplicationArguments args) {






        try {
            dataSource.getConnection();
            //redisComponent.getSysSettingsDto();
            log.info("服务启动成功，可以开始愉快的开发了");
        } catch (Exception e) {
            log.error("Mysql数据库或者Redis设置失败，请检查配置");
            throw new BusibessException("500", "服务启动失败");
        }
    }
}
