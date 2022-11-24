package com.yeff.blog.handler;

import com.yeff.blog.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.yeff.blog.enums.StatusCodeEnum.FAIL;
import static com.yeff.blog.enums.StatusCodeEnum.SUCCESS;

import java.util.List;


/**
 * 统一返回类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Data
@AllArgsConstructor
public class Result {
    /**
     * 返回状态
     */
    private Boolean success;
    /**
     * 返回数据
     */
    private Object data;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;

    public static Result ok() {
        return new Result(true, null, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static Result ok(Object data) {
        return new Result(true, data, SUCCESS.getCode(), SUCCESS.getDesc());
    }

    public static Result ok(List<?> data, Integer code) {
        return new Result(true, data, code, SUCCESS.getDesc());
    }

    public static Result fail() {
        return new Result(false, null, FAIL.getCode(), FAIL.getDesc());
    }

    public static Result fail(StatusCodeEnum statusCodeEnum) {
        return new Result(false, null, statusCodeEnum.getCode(), statusCodeEnum.getDesc());
    }

    public static Result fail(String message) {
        return new Result(false, null, FAIL.getCode(), message);
    }
}
