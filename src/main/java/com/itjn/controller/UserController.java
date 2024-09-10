package com.itjn.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.util.StringUtil;
import com.itjn.common.Result;
import com.itjn.common.context.BaseContext;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.common.properties.JwtProperties;
import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.dto.UserRegisterDTO;
import com.itjn.domain.dto.UserResetPasswordDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;
import com.itjn.service.UserService;
import com.itjn.utils.JwtUtil;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

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

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        //密码不返回给前端
        UserLoginVO userLoginVO = new UserLoginVO().builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .name(user.getName())
                .token(token)
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

    //TODO 修改用户信息接口(修改接口)

    //TODO 获取用户信息接口(查询接口)

    //TODO  分页查询(用sky-take-out项目那一套完整的写法)

    //退出接口（不用写）
    /*@PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }*/

    //TODO 获取用户头像接口(用户头像存在阿里云)(文件上传接口)

    //TODO 文件上传到 阿里云/本地

    //学会Swagger的使用

}
