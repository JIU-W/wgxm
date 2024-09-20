package com.itjn.controller;

import cn.hutool.core.util.StrUtil;
import com.itjn.common.Result;
import com.itjn.common.annotation.Log;
import com.itjn.common.config.CaptchaConfig;
import com.itjn.common.constants.Constants;
import com.itjn.common.enums.BusinessType;
import com.itjn.common.enums.ResultCodeEnum;
import com.itjn.common.properties.JwtProperties;
import com.itjn.domain.dto.UserLoginDTO;
import com.itjn.domain.dto.UserRegisterDTO;
import com.itjn.domain.entity.User;
import com.itjn.domain.vo.UserLoginVO;
import com.itjn.exception.BusibessException;
import com.itjn.service.UserService;
import com.itjn.utils.CreateImageCodeUtil;
import com.itjn.utils.JwtUtil;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)//允许跨域
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private CaptchaConfig catchaConfig;

    //获取验证码
    @Log(title = "获取图片验证码信息", businessType = BusinessType.OTHER)
    @GetMapping("/checkCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateImageCodeUtil imageCode = new CreateImageCodeUtil(130,40,5,20);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = imageCode.getCode();
        //将code保存到session(前端因为跨越等多种问题导致验证码存不进session,
        //                              改用集合存,后面也可以尝试用集合存)
        //session.setAttribute(Constants.CHECK_CODE_KEY, code);
        catchaConfig.setCaptcha(Constants.CHECK_CODE_KEY, code);

        //调用CreateImageCode对象的write方法，将生成的验证码图片写入到response的输出流中，
        //这样客户端就可以接收到这个验证码图片并显示出来。
        imageCode.write(response.getOutputStream());
    }


    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @Log(title = "登录", businessType = BusinessType.LOGIN)
    @PostMapping("/login")
    public Result login(HttpSession session,@RequestBody UserLoginDTO userLoginDTO){
        //参数校验
        if (StrUtil.isBlank(userLoginDTO.getUserName()) || StrUtil.isBlank(userLoginDTO.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (StringUtils.isNullOrEmpty(userLoginDTO.getCheckCode())) {
            return Result.error(ResultCodeEnum.CHECK_CODE_LOST_ERROR);
        }
        //System.out.println((String)session.getAttribute(Constants.CHECK_CODE_KEY));
        //校验验证码
        /*if(!userLoginDTO.getCheckCode().equalsIgnoreCase((String)session.getAttribute(Constants.CHECK_CODE_KEY))){
            throw new BusibessException(ResultCodeEnum.CHECK_CODE_ERROR);
        }*/
        //校验验证码
        if(!userLoginDTO.getCheckCode().equalsIgnoreCase(catchaConfig.getCaptchaMap(Constants.CHECK_CODE_KEY))){
            throw new BusibessException(ResultCodeEnum.CHECK_CODE_ERROR);
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
    @Log(title = "注册", businessType = BusinessType.REGISTER)
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO){
        if (StrUtil.isBlank(userRegisterDTO.getUserName()) || StrUtil.isBlank(userRegisterDTO.getPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        userService.register(userRegisterDTO);
        return Result.success();
    }

    //退出接口（不用写）
    /*@PostMapping("/logout")
    public Result logout() {
        return Result.success();
    }*/

    //TODO 学会Swagger的使用

    //TODO 引入Redis

}
