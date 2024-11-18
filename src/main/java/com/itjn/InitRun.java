package com.itjn;

import com.itjn.exception.BusibessException;
import com.itjn.mapper.BlogMapper;
import com.itjn.utils.RedisUtil;
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
 * @author JIU-W
 * @version 1.0
 * @description 启动项目时，执行该类
 * @date 2024-11-17
 */

@Slf4j
@Component("initRun")
public class InitRun implements ApplicationRunner {

    @Resource
    private DataSource dataSource;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BlogMapper blogMapper;

    //在项目启动时，把MySql中存储的博客浏览量量放入Redis中
    @Override
    public void run(ApplicationArguments args) {
        try {
            //查询博客信息  id : readCount
            blogMapper.selectAll(null).forEach(blog -> {
                //将博客的阅读量放入Redis中
                redisUtil.setCacheMapValue("blog:readCount", blog.getId().toString(), blog.getReadCount());
            });
            //测试数据库是否连接成功
            dataSource.getConnection();
            log.info("服务启动成功，可以开始愉快的开发了");
        } catch (Exception e) {
            log.error("Mysql数据库或者Redis设置失败，请检查配置");
            throw new BusibessException("500", "服务启动失败");
        }
    }

}
