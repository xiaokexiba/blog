package com.yeff.blog.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yeff.blog.constant.CommonConst.APPLICATION_JSON;

/**
 * 登录失败处理
 *
 * @author xoke
 * @date 2022/12/1
 */
@Component
public class AuthenticationFailHandlerImpl implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.fail(exception.getMessage())));
    }
}
