package com.itjn.service;

import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.dto.UserRegisterDTO;
import com.itjn.domain.dto.UserResetPasswordDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;

public interface UserService {

    User login(UserLoginDTO userLoginDTO);

    void register(UserRegisterDTO userRegisterDTO);

    void updatePassword(UserResetPasswordDTO userResetPasswordDTO);

}
