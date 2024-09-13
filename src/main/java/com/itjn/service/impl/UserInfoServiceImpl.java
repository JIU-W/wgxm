package com.itjn.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itjn.common.PageResult;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.common.enums.RoleEnum;
import com.itjn.domain.dto.ChangePasswordDTO;
import com.itjn.domain.dto.UserPageQueryDTO;
import com.itjn.domain.entity.User;
import com.itjn.exception.BusibessException;
import com.itjn.mapper.UserInfoMapper;
import com.itjn.mapper.UserMapper;
import com.itjn.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    //查询所有用户信息
    public List<User> selectAll(User user) {
        return userInfoMapper.selectAll(user);
    }

    //修改密码
    public void updatePassword(ChangePasswordDTO changePasswordDTO) {
        User dbUser = userInfoMapper.selectById(changePasswordDTO.getUserId());
        if (ObjectUtil.isNull(dbUser)) {
            throw new BusibessException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }

        String str = DigestUtils.md5DigestAsHex(changePasswordDTO.getPassword().getBytes());
        if (!str.equals(dbUser.getPassword())) {
            throw new BusibessException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        String newPassword = DigestUtils.md5DigestAsHex(changePasswordDTO.getNewPassword().getBytes());
        dbUser.setPassword(newPassword);
        userInfoMapper.updateUserInfo(dbUser);
    }

    //根据id查询用户信息
    public User selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }


    //修改用户信息
    public void updateUserInfo(User user) {
        userInfoMapper.updateUserInfo(user);
    }

    //新增用户(管理员新增用户)
    public void save(User user) {
        User dbUser = userInfoMapper.selectByUsername(user.getUserName());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new BusibessException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        //因为是管理员新增的，密码使用默认值abc123456
        //给密码加密
        String password = DigestUtils.md5DigestAsHex("abc123456".getBytes());
        user.setPassword(password);
        userInfoMapper.insert(user);
    }

    //根据UserId删除用户
    public void deleteByUserId(Integer userId) {
        userInfoMapper.deleteByUserId(userId);
    }

    //批量删除(千万不要循环调用数据库！！！)
    public void deleteBatch(List<Integer> ids) {
        userInfoMapper.deleteBatch(ids);
    }

    //分页查询
    public PageResult selectPage(UserPageQueryDTO userPageQueryDTO) {
        int pageNum = userPageQueryDTO.getPageNum();
        int pageSize = userPageQueryDTO.getPageSize();

        //使用分页插件PageHelper
        PageHelper.startPage(pageNum, pageSize);
        Page<User> page = userInfoMapper.selectPage(userPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }


}
