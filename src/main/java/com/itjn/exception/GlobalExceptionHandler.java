package com.itjn.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.itjn.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages="com.itjn.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();


    //统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public Result error(HttpServletRequest request, Exception e){
        log.error("异常信息：",e);
        return Result.error();
    }

    //拦截自定义业务异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json串
    public Result customError(HttpServletRequest request, CustomException e){
        return Result.error(e.getCode(), e.getMsg());
    }

}
