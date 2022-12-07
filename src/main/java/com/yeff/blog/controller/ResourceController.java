package com.yeff.blog.controller;

import com.yeff.blog.handler.Result;
import com.yeff.blog.service.ResourceService;
import com.yeff.blog.vo.ConditionVO;
import com.yeff.blog.vo.ResourceVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 资源控制器
 *
 * @author xoke
 * @date 2022/12/7
 */
@RestController
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 保存或更新资源
     *
     * @param resourceVO 资源信息
     * @return 返回结果
     */
    @GetMapping("/admin/resources")
    public Result saveOrUpdateResource(ResourceVO resourceVO) {
        return resourceService.saveOrUpdateResource(resourceVO);
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源ID
     * @return 返回结果
     */
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result deleteResource(@PathVariable("resourceId") Integer resourceId) {
        return resourceService.deleteResource(resourceId);
    }

    /**
     * 根据条件查询资源列表
     *
     * @param conditionVO 查询条件
     * @return 返回结果
     */
    @GetMapping("/admin/resources")
    public Result listResources(ConditionVO conditionVO) {
        return Result.ok(resourceService.listResources(conditionVO));
    }

    /**
     * 查看资源选项
     *
     * @return 资源选项
     */
    @GetMapping("/admin/role/resources")
    public Result listResourceOption() {
        return Result.ok(resourceService.listResourceOption());
    }
}
