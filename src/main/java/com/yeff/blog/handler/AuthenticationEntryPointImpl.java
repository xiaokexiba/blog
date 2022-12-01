package com.yeff.blog.handler;

import com.alibaba.fastjson.JSON;
import com.yeff.blog.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理器
 * <p>
 * 如果是认证过程中出现的异常会被封装成AuthenticationException
 * 然后调用AuthenticationEntryPoint对象的方法去进行异常处理
 *
 * @author xoke
 * @date 2022/12/1
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = Result.fail("认证失败请重新登录");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}