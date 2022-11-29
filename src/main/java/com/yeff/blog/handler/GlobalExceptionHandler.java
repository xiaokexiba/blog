package com.yeff.blog.handler;

import com.yeff.blog.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.yeff.blog.enums.StatusCodeEnum.SYSTEM_ERROR;

/**
 * 全局异常处理器
 *
 * @author xiaoke
 * @date 2022/11/29
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public Result businessExceptionHandler(BusinessException e) {
        log.error("businessException:" + e.getMessage(), e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result runtimeExceptionHandler(BusinessException e) {
        log.error("runtimeException:", e);
        return Result.fail(SYSTEM_ERROR);
    }
}
