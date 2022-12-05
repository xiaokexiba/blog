package com.yeff.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.dto.ResourceDTO;
import com.yeff.blog.entity.Resource;
import com.yeff.blog.entity.RoleResource;
import com.yeff.blog.exception.BusinessException;
import com.yeff.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.yeff.blog.handler.Result;
import com.yeff.blog.mapper.RoleResourceMapper;
import com.yeff.blog.service.ResourceService;
import com.yeff.blog.mapper.ResourceMapper;
import com.yeff.blog.vo.ConditionVO;
import com.yeff.blog.vo.ResourceVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源业务层接口实现类
 *
 * @author xoke
 * @date 2022/12/4
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
        implements ResourceService {

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;
    @javax.annotation.Resource
    private RoleResourceMapper roleResourceMapper;
    @javax.annotation.Resource
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    /**
     * 保存或更新资源
     *
     * @param resourceVO 资源信息
     * @return 返回结果
     */
    @Override
    public Result saveOrUpdateResource(ResourceVO resourceVO) {
        // 更新资源信息
        Resource resource = BeanUtil.copyProperties(resourceVO, Resource.class);
        boolean result = saveOrUpdate(resource);
        // 重新加载角色资源信息
        filterInvocationSecurityMetadataSource.clearDataSource();
        return Result.ok(result);
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源ID
     * @return 返回结果
     */
    @Override
    public Result deleteResource(Integer resourceId) {
        // 查询是否有角色关联
        Long count = roleResourceMapper.selectCount(new LambdaQueryWrapper<RoleResource>()
                .eq(RoleResource::getId, resourceId));
        if (count > 0) {
            // 有角色存在关联
            throw new BusinessException("该资源下存在角色！");
        }
        // 删除子资源
        List<Integer> resourceIdList = resourceMapper.selectList(new LambdaQueryWrapper<Resource>()
                        .select(Resource::getId).
                        eq(Resource::getParentId, resourceId))
                .stream()
                .map(Resource::getId)
                .collect(Collectors.toList());
        resourceIdList.add(resourceId);
        int result = resourceMapper.deleteBatchIds(resourceIdList);
        return Result.ok(result);
    }

    /**
     * 根据条件查询资源列表
     *
     * @param conditionVO 查询条件
     * @return 返回结果
     */
    @Override
    public List<ResourceDTO> listResources(ConditionVO conditionVO) {

        return null;
    }
}




