package com.itjn.mapper;

import com.itjn.domain.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where user_name = #{userName}")
    User selectByUserName(String userName);



}
