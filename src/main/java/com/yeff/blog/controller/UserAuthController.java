package com.yeff.blog.controller;

import com.yeff.blog.handler.Result;
import com.yeff.blog.service.UserAuthService;
import com.yeff.blog.vo.UserVO;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 用户账号控制器
 *
 * @author xoke
 * @date 2022/11/26
 */
@RestController
public class UserAuthController {

    @Resource
    private UserAuthService userAuthService;

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 返回结果
     */
    @GetMapping("/user/code")
    public Result sendCode(String email) {
        return userAuthService.sendCode(email);
    }

    /**
     * 用户注册
     *
     * @param userVO 用户信息
     * @return 返回结果
     */
    @PostMapping("/register")
    public Result register(UserVO userVO) {
        return userAuthService.register(userVO);
    }

    /**
     * 用户登入
     *
     * @param userVO 用户VO
     * @return 返回结果
     */
    @PostMapping("/user/login")
//    @PreAuthorize("hasAuthority('test')")
    public Result login(@RequestBody UserVO userVO) {
        return userAuthService.login(userVO);
    }

    @RequestMapping("/user/logout")
    public Result logout() {
        return userAuthService.logout();
    }
}
