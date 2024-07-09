package com.itjn.controller;


import com.itjn.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
@Slf4j
public class TestController {

    //测试
    @GetMapping("/test")
    public Result test(){
        log.info("测试成功");
        return Result.success();
    }


}