package com.itjn.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@TableName("blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 简介 */
    private String descr;
    /** 封面 */
    private String cover;
    /** 标签 */
    private String tags;
    /** 发布人ID */
    private Integer userId;
    /** 发布日期 */
    private String date;
    /** 浏览量 */
    private Integer readCount;

    private Integer categoryId;

}
