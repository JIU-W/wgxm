package com.itjn.controller;


import cn.yam.sychatsdk.SyClient;
import cn.yam.sychatsdk.config.SyChatProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itjn.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * sy-chat
 */
@RestController
@RequestMapping("/sdk")
public class SdkController {

    @Resource
    private RestTemplate restTemplate;

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

    //封装个人账号的语雀AI
    @GetMapping("/yuQueAi")
    public Result yuQueAi(@RequestParam(value = "msg", required = true) String msg) throws Exception {

        // 设置 HTTP 头部信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", "*******");       //设置个人账号Cookie
        headers.set("x-csrf-token", "*******"); //设置个人账号x-csrf-token

        // 创建请求体（JSON数据）
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("content", msg);
        requestBody.put("type", "custom");
        requestBody.put("stream", false);
        requestBody.put("http", true);

        // 创建 HttpEntity 对象，包含请求头和请求体
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 发送请求并接收响应
        ResponseEntity<String> response = restTemplate.postForEntity(
                "*****",//请求地址
                entity,
                String.class);

        String result = "";
        // 检查响应状态码
        if (response.getStatusCode().is2xxSuccessful()) {
            // 解析 JSON 响应
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                result = jsonNode.get("data").get("result").asText();
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("请求失败，状态码: " + response.getStatusCodeValue());
        }

        return Result.success(result);
    }



}

