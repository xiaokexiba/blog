package com.yeff.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.entity.UserAuth;
import com.yeff.blog.entity.UserInfo;
import com.yeff.blog.entity.UserRole;
import com.yeff.blog.exception.BusinessException;
import com.yeff.blog.handler.Result;
import com.yeff.blog.mapper.UserAuthMapper;
import com.yeff.blog.mapper.UserInfoMapper;
import com.yeff.blog.mapper.UserRoleMapper;
import com.yeff.blog.service.RedisService;
import com.yeff.blog.service.UserAuthService;
import com.yeff.blog.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

import static com.yeff.blog.constant.CommonConst.DEFAULT_NICKNAME;
import static com.yeff.blog.constant.RedisPrefixConst.USER_CODE_KEY;
import static com.yeff.blog.enums.RoleEnum.USER;

/**
 * 用户账号业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth>
        implements UserAuthService {

    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisService redisService;

    /**
     * 用户注册
     *
     * @param userVO 用户VO
     * @return 返回结果
     */
    @Override
    public Result register(UserVO userVO) {
        // 判断是否已经注册
        if (checkUser(userVO)) {
            throw new BusinessException("邮箱已被注册！");
        }
        // 创建用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(userVO.getUsername())
                .nickname(DEFAULT_NICKNAME + IdWorker.getId())
                .avatar("")
                .build();
        // 新增用户信息
        userInfoMapper.insert(userInfo);
        // 创建用户角色关联
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(USER.getRoleId())
                .build();
        // 新增用户角色关联
        userRoleMapper.insert(userRole);
        // 创建用户账号
        UserAuth userAuth = UserAuth.builder()
                .build();
        // 新增用户账号
        userAuthMapper.insert(userAuth);
        return Result.ok();
    }

    /**
     * 校验用户数据是否合法
     *
     * @param userVO 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO userVO) {
        if (!userVO.getCode().equals(redisService.get(USER_CODE_KEY + userVO.getUsername()))) {
            throw new BusinessException("验证码错误！");
        }
        //查询用户名是否存在
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, userVO.getUsername()));
        return Objects.nonNull(userAuth);
    }
}




