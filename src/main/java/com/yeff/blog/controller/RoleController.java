package com.yeff.blog.controller;

import com.yeff.blog.service.RoleService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色控制器
 *
 * @author xoke
 * @date 2022/12/7
 */
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;


}
