package com.yeff.blog.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.yeff.blog.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.yeff.blog.enums.StatusCodeEnum.AUTHORIZED;
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
        return Result.fail(e.getErrorMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public Result accessDeniedException(AccessDeniedException e) {
        log.error("不允许访问！原因是:" + e.getMessage(), e);
        return Result.fail(AUTHORIZED);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public Result authenticationException(AuthenticationException e) {
        log.error("AuthenticationException:" + e.getMessage(), e);
        return Result.fail("认证失败请重新登录！");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException:", e);
        return Result.fail(SYSTEM_ERROR);
    }

//    @ExceptionHandler(value = Exception.class)
//    public Result error(Exception e) {
//        log.error("Exception:" + e.getMessage(), e);
//        return Result.fail("发生异常");
//    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Result error(BadSqlGrammarException e) {
        log.error("SQLException:" + e.getMessage(), e);
        return Result.fail("sql语法错误");
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result error(JsonParseException e) {
        log.error("SQLException:" + e.getMessage(), e);
        return Result.fail("json解析异常");
    }
}
