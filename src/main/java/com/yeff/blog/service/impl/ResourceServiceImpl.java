package com.yeff.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.entity.Resource;
import com.yeff.blog.service.ResourceService;
import com.yeff.blog.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
 * 资源业务层接口实现类
 *
 * @author xoke
 * @date 2022/12/4
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService {

}




