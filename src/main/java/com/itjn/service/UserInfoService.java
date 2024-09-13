package com.itjn.service;

import com.itjn.common.PageResult;
import com.itjn.domain.dto.ChangePasswordDTO;
import com.itjn.domain.dto.UserPageQueryDTO;
import com.itjn.domain.entity.User;

import java.util.List;

public interface UserInfoService {

    List<User> selectAll(User user);

    void updatePassword(ChangePasswordDTO changePasswordDTO);

    User selectById(Integer id);


    void updateUserInfo(User user);

    void save(User user);


    void deleteByUserId(Integer userId);

    void deleteBatch(List<Integer> ids);

    PageResult selectPage(UserPageQueryDTO userPageQueryDTO);

}
