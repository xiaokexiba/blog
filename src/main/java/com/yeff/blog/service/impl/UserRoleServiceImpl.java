package com.yeff.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.entity.UserRole;
import com.yeff.blog.mapper.UserRoleMapper;
import com.yeff.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

}




