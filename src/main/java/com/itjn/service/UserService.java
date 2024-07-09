package com.itjn.service;

import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;

public interface UserService {

    User login(UserLoginDTO userLoginDTO);

}
