package com.itjn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itjn.domain.entity.Blog;
import com.itjn.mapper.BlogMapper;
import com.itjn.service.BlogService;
import com.itjn.service.UserService;
import com.itjn.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 查询所有博客
     * @param blog
     * @return
     */
    public List<Blog> selectAll(Blog blog) {
        return blogMapper.selectAll(blog);
    }

    /**
     * 在Redis根据id进行浏览量+1
     * @param id
     */
    public void updateReadCountById(Integer id) {
        //更新Redis中对应id的浏览量，浏览量加一
        redisUtil.incrementCacheMapValue("blog:readCount", id.toString(),1);
    }

    /**
     * 更新博客浏览量
     * @param blogs
     */
    public void updateReadCount(List<Blog> blogs) {
        blogMapper.update(blogs);
    }

    /**
     * 根据id查询浏览量
     * @param id
     * @return
     */
    public Integer selectReadCountById(Integer id) {
        Integer value = redisUtil.getCacheMapValue("blog:readCount", id.toString());
        return value;
    }


}
