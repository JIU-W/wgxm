package com.itjn.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.itjn.common.Result;
import com.sun.org.apache.bcel.internal.classfile.Code;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//异常处理器：处理表现层的异常       拦截所有controller的异常
@ControllerAdvice(basePackages="com.itjn.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();


    //统一异常处理@ExceptionHandler


    //拦截到其它异常,主要用于Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public Result error(HttpServletRequest request, Exception e){
        //log.error("异常信息：",e);
        log.info("异常信息：",e);
        return Result.error();//把异常信息返回给前端
    }

    //拦截自定义业务异常
    @ExceptionHandler(BusibessException.class)
    @ResponseBody//返回json串
    public Result customError(HttpServletRequest request, BusibessException e){
        return Result.error(e.getCode(), e.getMsg());//把异常信息返回给前端
    }

}
