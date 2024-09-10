package com.itjn.mapper;

import com.itjn.domain.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoMapper {

    //查询所有用户信息
    List<User> selectAll(User user);

    //根据id查询用户信息
    @Select("select * from user where user_id = #{userId}")
    User selectById(Integer userId);

    //修改用户信息
    void updateUserInfo(User user);

}
