package com.yeff.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.entity.UserInfo;
import com.yeff.blog.mapper.UserInfoMapper;
import com.yeff.blog.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * 用户信息业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
