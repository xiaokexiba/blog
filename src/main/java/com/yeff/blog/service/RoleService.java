package com.yeff.blog.service;

import com.yeff.blog.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeff.blog.handler.Result;
import com.yeff.blog.vo.RoleVO;

import java.util.List;

/**
 * 角色业务层接口
 *
 * @author xoke
 * @date 2022/12/2
 */
public interface RoleService extends IService<Role> {

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
     * @param roleIdList 角色id列表
     * @return 返回结果
     */
    Result deleteRoles(List<Integer> roleIdList);
}
