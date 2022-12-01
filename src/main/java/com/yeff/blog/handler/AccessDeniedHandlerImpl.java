package com.yeff.blog.handler;

import com.alibaba.fastjson.JSON;
import com.yeff.blog.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yeff.blog.enums.StatusCodeEnum.AUTHORIZED;

/**
 * 自定义权限不足处理器
 * <p>
 * 如果是授权过程中出现的异常会被封装成AccessDeniedException
 * 然后调用AccessDeniedHandler对象的方法去进行异常处理
 *
 * @author xoke
 * @date 2022/12/1
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = Result.fail(AUTHORIZED);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}