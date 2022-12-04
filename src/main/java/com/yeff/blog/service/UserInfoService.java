package com.yeff.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeff.blog.entity.UserInfo;
import com.yeff.blog.handler.Result;
import com.yeff.blog.vo.EmailVO;
import com.yeff.blog.vo.UserDisableVO;
import com.yeff.blog.vo.UserInfoVO;
import com.yeff.blog.vo.UserRoleVO;

/**
 * 用户信息业务层接口
 *
 * @author xoke
 * @date 2022/11/24
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return 返回结果
     */
    Result updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 更新绑定邮箱
     *
     * @param emailVO 邮箱
     * @return 返回结果
     */
    Result updateUserEmail(EmailVO emailVO);

    /**
     * 更新用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return 返回结果
     */
    Result updateUserRole(UserRoleVO userRoleVO);

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用信息
     */
    void updateUserDisable(UserDisableVO userDisableVO);
}
