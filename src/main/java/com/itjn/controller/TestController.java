package com.itjn.controller;


import com.itjn.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("")
public class TestController {

    @GetMapping("/test")
    public Result test(){
        log.info("测试成功");
        return Result.success();
    }

}