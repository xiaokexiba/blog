package com.yeff.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.entity.Role;
import com.yeff.blog.entity.RoleResource;
import com.yeff.blog.entity.UserRole;
import com.yeff.blog.exception.BusinessException;
import com.yeff.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.yeff.blog.handler.Result;
import com.yeff.blog.mapper.RoleMapper;
import com.yeff.blog.mapper.UserRoleMapper;
import com.yeff.blog.service.RoleMenuService;
import com.yeff.blog.service.RoleResourceService;
import com.yeff.blog.service.UserRoleService;
import com.yeff.blog.vo.RoleVO;
import com.yeff.blog.entity.RoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yeff.blog.constant.CommonConst.FALSE;

/**
 * 用户角色关联业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RoleResourceService roleResourceService;
    @Resource
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色信息
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result saveOrUpdateRole(RoleVO roleVO) {
        // 判断是否存在
        Role existRole = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (ObjectUtil.isNotNull(existRole) && !existRole.getId().equals(roleVO.getId())) {
            throw new BusinessException("该用户名已存在！");
        }
        // 封装信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(FALSE)
                .build();
        // 保存数据库
        roleMapper.updateById(role);
        // 更新角色资源关系
        if (Objects.nonNull(roleVO.getResourceIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleResourceService.remove(new LambdaQueryWrapper<RoleResource>()
                        .eq(RoleResource::getRoleId, roleVO.getId()));
            }
            List<RoleResource> roleResourceList = roleVO.getResourceIdList().stream()
                    .map(resourceId -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(resourceId)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }
        // 更新角色菜单关系
        if (Objects.nonNull(roleVO.getMenuIdList())) {
            if (Objects.nonNull(roleVO.getId())) {
                roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleVO.getId()));
            }
            List<RoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(menuId -> RoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(menuId)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param roleIdList 角色ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        // 判断角色下是否有用户
        Long count = userRoleMapper.selectCount(new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getRoleId, roleIdList));
        if (count > 0) {
            throw new BusinessException("该角色下存在用户");
        }
        roleMapper.deleteBatchIds(roleIdList);
    }
}




