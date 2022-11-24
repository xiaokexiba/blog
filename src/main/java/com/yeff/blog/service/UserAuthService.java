package com.yeff.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeff.blog.entity.UserAuth;
import com.yeff.blog.handler.Result;
import com.yeff.blog.vo.UserVO;

/**
 * 用户账号业务层接口
 *
 * @author xoke
 * @date 2022/11/24
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 用户注册
     *
     * @param userVO 用户VO
     * @return 返回结果
     */
    Result register(UserVO userVO);
}
