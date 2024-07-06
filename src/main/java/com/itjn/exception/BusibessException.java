package com.itjn.exception;

import com.itjn.common.enums.ResultCodeEnum;

/**
 * 自定义全局异常(业务异常)
 */
public class BusibessException extends RuntimeException {
    private String code;
    private String msg;

    public BusibessException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.code;
        this.msg = resultCodeEnum.msg;
    }

    public BusibessException(String code, String msg) {
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