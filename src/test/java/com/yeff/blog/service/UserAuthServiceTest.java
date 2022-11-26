package com.yeff.blog.service;

import com.yeff.blog.handler.Result;
import com.yeff.blog.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAuthServiceTest {

    @Resource
    private UserAuthService userAuthService;

    @Test
    void testRegister() {
        UserVO userVO = new UserVO();
        userVO.setUsername("yjmqaz@126.com");
        userVO.setPassword("123123");
        userVO.setCode("762725");
        String result = userAuthService.register(userVO).toString();
        System.out.println(result);
    }

    @Test
    void testSendCode() {
        String email = "yjmqaz@126.com";
        Result result = userAuthService.sendCode(email);
        System.out.println(result.toString());
    }
}