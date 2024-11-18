package com.itjn.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "")
@TableName(value = "blog")
public class Blog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String content;

    private String author;

    private String createTime;

    private String tags;

    private String category;

    private String status;

    private String cover;

    private String summary;





}
