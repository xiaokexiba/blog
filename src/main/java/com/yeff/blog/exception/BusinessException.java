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
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }
}
