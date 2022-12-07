package com.yeff.blog.service;

import com.yeff.blog.dto.LabelOptionDTO;
import com.yeff.blog.dto.ResourceDTO;
import com.yeff.blog.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeff.blog.handler.Result;
import com.yeff.blog.vo.ConditionVO;
import com.yeff.blog.vo.ResourceVO;

import java.util.List;

/**
 * 资源业务层接口
 *
 * @author xoke
 * @date 2022/12/3
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 保存或更新资源
     *
     * @param resourceVO 资源信息
     * @return 返回结果
     */
    Result saveOrUpdateResource(ResourceVO resourceVO);

    /**
     * 删除资源
     *
     * @param resourceId 资源ID
     * @return 返回结果
     */
    Result deleteResource(Integer resourceId);

    /**
     * 根据条件查询资源列表
     *
     * @param conditionVO 查询条件
     * @return 返回结果
     */
    List<ResourceDTO> listResources(ConditionVO conditionVO);

    /**
     * 查看资源选项
     *
     * @return 资源选项
     */
    List<LabelOptionDTO> listResourceOption();
}
