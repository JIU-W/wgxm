package com.itjn.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.internal.ws.developer.SchemaValidation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志记录（AOP+注解操作日志）
 *
 * @date 2024/07/14
 */
@Data
@TableName(value = "sys_oper_log")
public class SysOperLog implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String businessType;

    private String method;

    private String requestMethod;

    private String operatorType;

    private String operName;

    private String operUrl;

    private String operIp;

    private String operParam;

    private String jsonResult;

    private Integer status;

    private String errorMsg;

    private Date operTime;

    private long executeTime;

}
