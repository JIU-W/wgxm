package com.itjn.service;

import com.itjn.domain.dto.ChangePasswordDTO;
import com.itjn.domain.entity.User;

import java.util.List;

public interface UserInfoService {

    List<User> selectAll(User user);

    void updatePassword(ChangePasswordDTO changePasswordDTO);

    User selectById(Integer id);


    void updateUserInfo(User user);

}
