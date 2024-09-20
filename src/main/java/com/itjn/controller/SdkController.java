package com.itjn.controller;


import cn.yam.sychatsdk.SyClient;
import cn.yam.sychatsdk.config.SyChatProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * sy-chat
 */
@RestController
@RequestMapping("/sdk")
public class SdkController {

    @Resource
    private SyChatProperties syChatProperties;//必须引入

    @GetMapping("/chat")
    public void chat(HttpServletResponse response, @RequestParam String msg) throws Exception {
        SyClient client = new SyClient(syChatProperties);
        String s = client.chat(msg);
        //System.out.println(response);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(s);
    }

}
