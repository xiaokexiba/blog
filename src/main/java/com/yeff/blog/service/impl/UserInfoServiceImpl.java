package com.yeff.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.entity.UserInfo;
import com.yeff.blog.entity.UserRole;
import com.yeff.blog.exception.BusinessException;
import com.yeff.blog.handler.Result;
import com.yeff.blog.mapper.UserInfoMapper;
import com.yeff.blog.service.RedisService;
import com.yeff.blog.service.UserInfoService;
import com.yeff.blog.service.UserRoleService;
import com.yeff.blog.utils.UserUtils;
import com.yeff.blog.vo.EmailVO;
import com.yeff.blog.vo.UserDisableVO;
import com.yeff.blog.vo.UserInfoVO;
import com.yeff.blog.vo.UserRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static com.yeff.blog.constant.RedisPrefixConst.USER_CODE_KEY;

/**
 * 用户信息业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private UserRoleService userRoleService;

    /**
     * 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateUserInfo(UserInfoVO userInfoVO) {
        // 封装用户信息
        UserInfo userInfo = UserInfo.builder()
                .id(UserUtils.getLoginUser().getUserDetailDTO().getId())
                .nickname(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .webSite(userInfoVO.getWebSite())
                .build();
        // 保存到数据库中
        userInfoMapper.updateById(userInfo);
        return Result.ok();
    }

    /**
     * 更新绑定邮箱
     *
     * @param emailVO 邮箱信息
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateUserEmail(EmailVO emailVO) {
        // 验证验证码
        if (!emailVO.getCode().equals(redisService.get(USER_CODE_KEY + emailVO.getEmail()))) {
            throw new BusinessException("请重新输入验证码！");
        }
        // 封装用户信息
        UserInfo userInfo = UserInfo.builder()
                .id(UserUtils.getLoginUser().getUserDetailDTO().getUserInfoId())
                .email(emailVO.getEmail())
                .build();
        // 保存数据库
        userInfoMapper.updateById(userInfo);
        return Result.ok();
    }

    /**
     * 更新用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateUserRole(UserRoleVO userRoleVO) {
        // 封装信息
        UserInfo userInfo = UserInfo.builder()
                .id(userRoleVO.getUserInfoId())
                .nickname(userRoleVO.getNickname())
                .build();
        // 保存数据库
        userInfoMapper.updateById(userInfo);
        // 删除用户角色，并重新添加
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getUserInfoId()));
        // TODO 这个还有点不懂
        List<UserRole> userRoleList = userRoleVO.getRoleIdList().stream()
                .map(roleId -> UserRole.builder()
                        .roleId(roleId)
                        .userId(userRoleVO.getUserInfoId())
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return Result.ok();
    }

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        // 更新用户禁用状态
        UserInfo userInfo = UserInfo.builder()
                .id(userDisableVO.getId())
                .isDisable(userDisableVO.getIsDisable())
                .build();
        userInfoMapper.updateById(userInfo);
    }
}
