package com.itjn.common;

import com.itjn.common.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class Result {
    private String code;
    private String msg;
    private Object data;

    public Result(Object data) {
        this.data = data;
    }
    public Result() {
    }
    public static Result success() {
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.code);
        result.setMsg(ResultCodeEnum.SUCCESS.msg);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result (data);
        result.setCode(ResultCodeEnum.SUCCESS.code);
        result.setMsg(ResultCodeEnum.SUCCESS.msg);
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setCode(ResultCodeEnum.SYSTEM_ERROR.code);
        result.setMsg(ResultCodeEnum.SYSTEM_ERROR.msg);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(ResultCodeEnum resultCodeEnum) {
        Result result = new Result();
        result.setCode(resultCodeEnum.code);
        result.setMsg(resultCodeEnum.msg);
        return result;
    }

}
