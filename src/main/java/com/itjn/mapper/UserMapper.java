package com.itjn.mapper;

import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where user_name = #{userName}")
    User selectByUserName(String userName);


    void insert(User user);


    User selectById(UserDTO userDTO);

    void updateById(User user);

}
