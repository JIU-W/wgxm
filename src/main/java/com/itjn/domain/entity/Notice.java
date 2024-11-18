package com.itjn.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * 公告信息表
*/
@ApiModel(value = "公告信息表")
@TableName(value = "notice")
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer id;

    private String title;

    private String content;
    //简介
    private String descr;
    //封面
    private String cover;

    private String tags;

    private Integer userId;

    private String date;

    private Integer readCount;

}
