package com.yeff.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author xoke
 * @date 2022/11/29
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
