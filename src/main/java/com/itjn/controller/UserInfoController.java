package com.itjn.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itjn.common.PageResult;
import com.itjn.common.Result;
import com.itjn.common.annotation.Log;
import com.itjn.common.context.BaseContext;
import com.itjn.common.enums.BusinessType;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.common.enums.RoleEnum;
import com.itjn.domain.dto.ChangePasswordDTO;
import com.itjn.domain.dto.UserDTO;
import com.itjn.domain.dto.UserPageQueryDTO;
import com.itjn.domain.entity.User;
import com.itjn.service.UserInfoService;
import com.itjn.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userInfo")
//@CrossOrigin(origins = "*", maxAge = 3600)//允许跨域
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    //查询所有用户信息(满足查询条件的均可以被查到，可能会有多个。如果没有查询条件就是查询所有)
    @Log(title = "查询所有用户信息", businessType = BusinessType.QUERY)
    @GetMapping("/selectAll")
    public Result selectAll(User user){
        List<User> list = userInfoService.selectAll(user);
        return Result.success(list);
    }

    //根据userId查询用户信息
    @Log(title = "根据userId查询用户信息", businessType = BusinessType.QUERY)
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userInfoService.selectById(id);
        return Result.success(user);
    }

    //修改用户信息
    @Log(title = "修改用户信息", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userInfoService.updateUserInfo(user);
        return Result.success();
    }

    //修改用户密码
    @Log(title = "修改用户密码", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        if (changePasswordDTO.getUserId() == null || StrUtil.isBlank(changePasswordDTO.getPassword())
                || ObjectUtil.isEmpty(changePasswordDTO.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userInfoService.updatePassword(changePasswordDTO);
        return Result.success();
    }

    //新增用户
    @Log(title = "新增用户", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        userInfoService.save(user);
        return Result.success();
    }

    //根据userId删除用户
    @Log(title = "根据userId删除用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{userId}")
    public Result deleteByUserId(@PathVariable Integer userId){
        userInfoService.deleteByUserId(userId);
        return Result.success();
    }

    //批量删除
    @Log(title = "批量删除用户", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        userInfoService.deleteBatch(ids);
        return Result.success();
    }

    //分页查询(用的sky-take-out那一套)
    @Log(title = "分页查询用户信息", businessType = BusinessType.QUERY)
    @GetMapping("/selectPage")
    public Result page(UserPageQueryDTO userPageQueryDTO){
        PageResult pageResult = userInfoService.selectPage(userPageQueryDTO);
        return Result.success(pageResult);
    }


}
