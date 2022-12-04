package com.yeff.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yeff.blog.entity.UserRole;
import com.yeff.blog.handler.Result;
import com.yeff.blog.vo.RoleVO;

import java.util.List;

/**
 * 用户角色关联业务层接口
 *
 * @author xoke
 * @date 2022/11/24
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色信息
     * @return 返回结果
     */
    Result saveOrUpdateRole(RoleVO roleVO);

    /**
     * 删除角色
     *
     * @param roleIdList 角色ID列表
     */
    void deleteRoles(List<Integer> roleIdList);
}
