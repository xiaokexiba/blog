package com.yeff.blog.exception;

import com.yeff.blog.enums.StatusCodeEnum;

import static com.yeff.blog.enums.StatusCodeEnum.FAIL;

/**
 * 自定义异常
 *
 * @author xoke
 * @date 2022/11/24
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String message;

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public BusinessException(String message) {
        this.code = FAIL.getCode();
        this.message = message;
    }

    public BusinessException(StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum.getDesc());
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }

    public BusinessException(StatusCodeEnum statusCodeEnum, String message) {
        super(statusCodeEnum.getDesc());
        this.code = statusCodeEnum.getCode();
        this.message = message;
    }
}
