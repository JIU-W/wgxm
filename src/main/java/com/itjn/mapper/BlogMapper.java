package com.itjn.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itjn.domain.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogMapper{

    //查询所有博客
    @Select("select * from blog")
    List<Blog> selectAll(Blog blog);

    void update(@Param("blogs") List<Blog> blogs);

}
