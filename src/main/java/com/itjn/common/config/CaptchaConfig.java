package com.itjn.common.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//存储验证码的集合
//将对象交给spring管理
@Component
public class CaptchaConfig {
    private final Map<String,String> CAPTCHA_MAP = new HashMap<>();

    public void setCaptcha(String key,String value){
        CAPTCHA_MAP.put(key,value);
    }
    public String getCaptchaMap(String key) {
        return CAPTCHA_MAP.get(key);
    }
}
