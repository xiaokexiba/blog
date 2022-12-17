package com.yeff.blog.mapper;

import com.yeff.blog.dto.ResourceRoleDTO;
import com.yeff.blog.dto.RoleDTO;
import com.yeff.blog.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yeff.blog.vo.ConditionVO;

import java.util.List;

/**
 * 角色持久层接口
 *
 * @author xoke
 * @date 2022/12/2
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询资源角色列表
     *
     * @return 资源角色列表
     */
    List<ResourceRoleDTO> listResourceRoles();

    /**
     * 查询角色列表
     *
     * @param current     页码
     * @param size        条数
     * @param conditionVO 条件
     * @return 角色列表
     */
    List<RoleDTO> listRoles(Long current, Long size, ConditionVO conditionVO);
}




