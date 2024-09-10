package com.itjn.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.domain.dto.ChangePasswordDTO;
import com.itjn.domain.entity.User;
import com.itjn.exception.BusibessException;
import com.itjn.mapper.UserInfoMapper;
import com.itjn.mapper.UserMapper;
import com.itjn.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    //查询所有用户信息
    public List<User> selectAll(User user) {
        return userInfoMapper.selectAll(user);
    }

    //修改密码
    public void updatePassword(ChangePasswordDTO changePasswordDTO) {
        User dbUser = userInfoMapper.selectById(changePasswordDTO.getUserId());
        if (ObjectUtil.isNull(dbUser)) {
            throw new BusibessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        String str = DigestUtils.md5DigestAsHex(changePasswordDTO.getPassword().getBytes());
        if (!str.equals(dbUser.getPassword())) {
            throw new BusibessException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        String newPassword = DigestUtils.md5DigestAsHex(changePasswordDTO.getNewPassword().getBytes());
        dbUser.setPassword(newPassword);
        userInfoMapper.updateUserInfo(dbUser);
    }

    //根据id查询用户信息
    public User selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }


    //修改用户信息
    public void updateUserInfo(User user) {
        userInfoMapper.updateUserInfo(user);
    }



}
