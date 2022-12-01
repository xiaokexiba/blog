package com.yeff.blog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('test')")
    public String hello() {
        return "hello";
    }
}
