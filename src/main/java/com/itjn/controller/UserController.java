package com.itjn.controller;

import com.itjn.common.Result;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;
import com.itjn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        User user = userService.login(userLoginDTO);

        //密码不返回给前端
        UserLoginVO userLoginVO = new UserLoginVO().builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .name(user.getName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .sex(user.getSex())
                .phone(user.getPhone())
                .info(user.getInfo())
                .birth(user.getBirth())
                .build();
        return Result.success(userLoginVO);
    }

    //TODO 文件上传到 阿里云/本地



}