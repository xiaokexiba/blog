package com.yeff.blog.exception;

import com.yeff.blog.enums.StatusCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.yeff.blog.enums.StatusCodeEnum.FAIL;

/**
 * 自定义异常
 *
 * @author xoke
 * @date 2022/11/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String errorMessage;

    public BusinessException(String errorMessage) {
        this.code = FAIL.getCode();
        this.errorMessage = errorMessage;
    }

    public BusinessException(StatusCodeEnum statusCodeEnum) {
        super(statusCodeEnum.getDesc());
        this.code = statusCodeEnum.getCode();
        this.errorMessage = statusCodeEnum.getDesc();
    }

    public BusinessException(StatusCodeEnum statusCodeEnum, String errorMessage) {
        super(statusCodeEnum.getDesc());
        this.code = statusCodeEnum.getCode();
        this.errorMessage = errorMessage;
    }
}
