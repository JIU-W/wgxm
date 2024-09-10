package com.itjn.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.dto.UserRegisterDTO;
import com.itjn.domain.dto.UserResetPasswordDTO;
import com.itjn.domain.entity.User;
import com.itjn.exception.BusibessException;
import com.itjn.mapper.UserMapper;
import com.itjn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    //登录
    public User login(UserLoginDTO userLoginDTO) {
        User dbuser = userMapper.selectByUserName(userLoginDTO.getUserName());
        if (ObjectUtil.isNull(dbuser)) {
            throw new BusibessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        String password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        if(!password.equals(dbuser.getPassword())){
            throw new BusibessException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        //设置默认头像
        //dbuser.setAvatar();
        return dbuser;
    }

    /**
     * 注册
     * @param userRegisterDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegisterDTO userRegisterDTO) {
        User dbuser = userMapper.selectByUserName(userRegisterDTO.getUserName());
        if (ObjectUtil.isNotNull(dbuser)) {
            throw new BusibessException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        User user = new User();
        user.setUserName(userRegisterDTO.getUserName());
        String password = userRegisterDTO.getPassword();
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setName("请设置昵称");
        userMapper.insert(user);
        }

}
