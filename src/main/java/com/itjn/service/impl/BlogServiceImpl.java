package com.itjn.service.impl;

import com.itjn.mapper.BlogMapper;
import com.itjn.service.BlogService;
import com.itjn.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;




}
