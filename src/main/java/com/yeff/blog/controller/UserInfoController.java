package com.yeff.blog.controller;

import com.yeff.blog.handler.Result;
import com.yeff.blog.service.UserInfoService;
import com.yeff.blog.vo.EmailVO;
import com.yeff.blog.vo.UserDisableVO;
import com.yeff.blog.vo.UserInfoVO;
import com.yeff.blog.vo.UserRoleVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户信息控制器
 *
 * @author xoke
 * @date 2022/12/7
 */
@RestController
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return 返回结果
     */
    @PutMapping("/user/info")
    public Result updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        return userInfoService.updateUserInfo(userInfoVO);
    }

    /**
     * 更新绑定邮箱
     *
     * @param emailVO 邮箱
     * @return 返回结果
     */
    @PostMapping("/user/email")
    public Result updateUserEmail(@Valid @RequestBody EmailVO emailVO) {
        return userInfoService.updateUserEmail(emailVO);
    }

    /**
     * 更新用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return 返回结果
     */
    @PutMapping("/admin/user/role")
    public Result updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        return userInfoService.updateUserRole(userRoleVO);
    }

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用信息
     */
    @PutMapping("/admin/user/disable")
    public Result updateUserDisable(UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.ok();
    }
}
