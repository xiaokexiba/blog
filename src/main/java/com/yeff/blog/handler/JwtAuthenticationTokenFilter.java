package com.yeff.blog.handler;

import com.yeff.blog.entity.LoginUser;
import com.yeff.blog.exception.BusinessException;
import com.yeff.blog.service.RedisService;
import com.yeff.blog.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.yeff.blog.constant.RedisPrefixConst.LOGIN_KEY;

/**
 * 认证过滤器
 * <p>
 * 我们需要自定义一个过滤器，这个过滤器会去获取请求头中的token，
 * 对token进行解析取出其中的userid。使用userid去redis中获取对应的LoginUser对象。
 * 然后封装Authentication对象存入SecurityContextHolder
 *
 * @author xoke
 * @date 2022/11/29
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String userId;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        // 从redis中获取用户信息
        String redisKey = LOGIN_KEY + userId;
        LoginUser loginUser = (LoginUser) redisService.get(redisKey);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        // 存入SecurityContextHolder
        // TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}