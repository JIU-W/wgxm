package com.itjn.common.enums;

import lombok.Data;

public enum ResultCodeEnum {
    SUCCESS("200", "请求成功"),

    PARAM_ERROR("400", "参数异常"),

    TOKEN_INVALID_ERROR("401", "认证信息无效，请重新登录"),

    TOKEN_CHECK_ERROR("401", "token验证失败，请重新登录"),

    Address_Not_Exist("404", "请求地址不存在"),
    PARAM_LOST_ERROR("4001", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_EXIST_ERROR("5001", "用户名已存在"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "用户不存在"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),

    CHECK_CODE_LOST_ERROR("5006", "图片验证码缺失"),

    CHECK_CODE_ERROR("5007", "图片验证码错误"),

    AVATAR_UPLOAD_FAILED("5008", "头像上传失败");

    public String code;
    public String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
