package com.yeff.blog.handler;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.yeff.blog.dto.UserInfoDTO;
import com.yeff.blog.entity.UserAuth;
import com.yeff.blog.mapper.UserAuthMapper;
import com.yeff.blog.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yeff.blog.constant.CommonConst.APPLICATION_JSON;

/**
 * 登入成功处理器
 *
 * @author xoke
 * @date 2022/12/1
 */
@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Resource
    private UserAuthMapper userAuthMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 返回登录信息
        UserInfoDTO userLoginDTO = BeanUtil.copyProperties(UserUtils.getLoginUser(), UserInfoDTO.class);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.ok(userLoginDTO)));
        // 更新用户ip，最近登录时间
        log.info("登入成功");
        updateUserInfo();
    }

    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getLoginUser().getUserDetailDTO().getId())
                .ipAddress(UserUtils.getLoginUser().getUserDetailDTO().getIpAddress())
                .ipSource(UserUtils.getLoginUser().getUserDetailDTO().getIpSource())
                .lastLoginTime(UserUtils.getLoginUser().getUserDetailDTO().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }
}
