package com.yeff.blog.mapper;

import com.yeff.blog.entity.UserAuth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAuthMapperTest {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Test
    void testUser() {
        List<UserAuth> users = userAuthMapper.selectList(null);
        System.out.println(users);
    }
}