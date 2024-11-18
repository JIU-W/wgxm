package com.itjn.job;


import com.itjn.domain.entity.Blog;
import com.itjn.service.BlogService;
import com.itjn.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 每隔10分钟把Redis的浏览量更新到MySql数据库中
 * @author JIU-W
 * @date 2024-11-18
 * @version 1.0
 */
@Slf4j
@Component
public class UpdateReadCountJob {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BlogService blogService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void UpdateReadCountJob(){
        log.info("执行定时任务");

        //获取Redis的数据
        Map<String, Integer> readCountMap = redisUtil.getCacheMap("blog:readCount");

        //遍历把数据更新到MySql数据库
        Set<Map.Entry<String, Integer>> entries = readCountMap.entrySet();

        List<Blog> blogs = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entries) {
            Blog blog = new Blog();
            //获取id
            blog.setId(Integer.parseInt(entry.getKey()));
            //获取浏览量
            blog.setReadCount(entry.getValue());
            blogs.add(blog);
        }
        //更新到数据库(这里不要把调数据库的操作放在循环里，否则就形成了循环调数据库，很耗性能)
        blogService.updateReadCount(blogs);


    }

}
