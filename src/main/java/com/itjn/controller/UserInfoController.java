package com.itjn.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itjn.common.Result;
import com.itjn.common.context.BaseContext;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.common.enums.RoleEnum;
import com.itjn.domain.dto.ChangePasswordDTO;
import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.entity.User;
import com.itjn.service.UserInfoService;
import com.itjn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {


    @Autowired
    private UserInfoService userInfoService;

    //查询用户信息(满足查询条件的均可以被查到，可能会有多个。如果没有查询条件就是查询所有)
    @GetMapping("/selectAll")
    public Result selectAll(User user){
        List<User> list = userInfoService.selectAll(user);
        return Result.success(list);
    }

    //根据userId查询用户信息
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userInfoService.selectById(id);
        return Result.success(user);
    }

    //修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userInfoService.updateUserInfo(user);
        return Result.success();
    }

    //修改用户密码
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        if (changePasswordDTO.getUserId() == null || StrUtil.isBlank(changePasswordDTO.getPassword())
                || ObjectUtil.isEmpty(changePasswordDTO.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userInfoService.updatePassword(changePasswordDTO);
        return Result.success();
    }

}