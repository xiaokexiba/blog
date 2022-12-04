package com.yeff.blog.mapper;

import com.yeff.blog.dto.ResourceRoleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleMapperTest {

    @Resource
    private RoleMapper roleMapper;

    @Test
    void testListResourceRoles() {
        List<ResourceRoleDTO> resourceRoleDTOS = roleMapper.listResourceRoles();
        System.out.println(resourceRoleDTOS);
    }
}