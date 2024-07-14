package com.itjn.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.util.StringUtil;
import com.itjn.common.Result;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.dto.UserRegisterDTO;
import com.itjn.domain.dto.UserResetPasswordDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;
import com.itjn.service.UserService;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        //参数校验
        if (StrUtil.isBlank(userLoginDTO.getUserName()) || StrUtil.isBlank(userLoginDTO.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
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

    /**
     * 注册
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        if (StrUtil.isBlank(userRegisterDTO.getUserName()) || StrUtil.isBlank(userRegisterDTO.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.register(userRegisterDTO);
        return Result.success();
    }

    //重置密码接口
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserResetPasswordDTO userResetPasswordDTO){
        if (StrUtil.isBlank(userResetPasswordDTO.getUserName()) ||
                StrUtil.isBlank(userResetPasswordDTO.getPassword()) ||
                StrUtil.isBlank(userResetPasswordDTO.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.updatePassword(userResetPasswordDTO);
        return Result.success();
    }

    //TODO 修改用户信息接口(修改接口)

    //TODO 获取用户信息接口(查询接口)

    //TODO 登出接口

    //TODO 获取用户头像接口(用户头像存在阿里云)(文件上传接口)

    //TODO 文件上传到 阿里云/本地

}
