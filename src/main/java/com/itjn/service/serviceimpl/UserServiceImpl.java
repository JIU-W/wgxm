package com.itjn.service.serviceimpl;

import cn.hutool.core.util.ObjectUtil;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;
import com.itjn.exception.BusibessException;
import com.itjn.mapper.UserMapper;
import com.itjn.service.UserService;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(UserLoginDTO userLoginDTO) {
        User dbuser = userMapper.selectByUserName(userLoginDTO.getUserName());
        if (ObjectUtil.isNull(dbuser)) {
            throw new BusibessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        //String password = DigestUtils.md5DigestAsHex(userLoginDTO.getPassword().getBytes());
        String password = userLoginDTO.getPassword();
        if(!password.equals(dbuser.getPassword())){
            throw new BusibessException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }

        //TODO 后面补上jwt校验（jwt令牌生成）

        return dbuser;
    }

    //注册接口
        //对密码做MD5加密再存数据库

}
