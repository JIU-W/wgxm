package com.itjn.service.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itjn.common.Result;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.dto.UserRegisterDTO;
import com.itjn.domain.dto.UserResetPasswordDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;
import com.itjn.exception.BusibessException;
import com.itjn.mapper.UserMapper;
import com.itjn.service.UserService;
import com.sun.xml.internal.bind.v2.TODO;
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

        //TODO 后面补上jwt校验（jwt令牌生成）   (用sky-take-out项目那一套完整的写法)

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

    /**
     * 修改密码
     * @param userResetPasswordDTO
     */
    public void updatePassword(UserResetPasswordDTO userResetPasswordDTO) {
        User dbUser = userMapper.selectByUserName(userResetPasswordDTO.getUserName());
        if (ObjectUtil.isNull(dbUser)) {
            throw new BusibessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        String str = DigestUtils.md5DigestAsHex(userResetPasswordDTO.getPassword().getBytes());
        if (!str.equals(dbUser.getPassword())) {
            throw new BusibessException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        String newPassword = DigestUtils.md5DigestAsHex(userResetPasswordDTO.getNewPassword().getBytes());
        dbUser.setPassword(newPassword);
        userMapper.updateById(dbUser);
    }


}
