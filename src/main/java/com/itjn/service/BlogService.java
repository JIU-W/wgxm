package com.itjn.service;


import com.itjn.domain.entity.Blog;

import java.util.List;

public interface BlogService {

    /**
     * 查询所有博客
     * @param blog
     * @return
     */
    List<Blog> selectAll(Blog blog);

    /**
     * 根据id修改博客浏览量
     * @param id
     */
    void updateReadCountById(Integer id);

    /**
     * 更新博客浏览量
     * @param blogs
     */
    void updateReadCount(List<Blog> blogs);

    /**
     * 根据id查询博客浏览量
     * @param id
     * @return
     */
    Integer selectReadCountById(Integer id);


}
