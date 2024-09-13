package com.itjn.mapper;

import com.github.pagehelper.Page;
import com.itjn.domain.dto.UserPageQueryDTO;
import com.itjn.domain.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoMapper {

    //查询所有用户信息
    List<User> selectAll(User user);

    //根据id查询用户信息
    @Select("select * from user where user_id = #{userId}")
    User selectById(Integer userId);

    //根据用户名查询
    @Select("select * from user where user_name = #{userName}")
    User selectByUsername(String userName);

    //修改用户信息
    void updateUserInfo(User user);

    //新增用户
    void insert(User user);

    //删除
    @Delete("delete from user where user_id = #{userId}")
    void deleteByUserId(Integer userId);


    void deleteBatch(List<Integer> ids);

    //分页查询
    Page<User> selectPage(UserPageQueryDTO userPageQueryDTO);
}
