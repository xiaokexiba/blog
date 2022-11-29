package com.yeff.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yeff.blog.dto.UserDetailDTO;
import com.yeff.blog.entity.LoginUser;
import com.yeff.blog.entity.UserAuth;
import com.yeff.blog.entity.UserInfo;
import com.yeff.blog.exception.BusinessException;
import com.yeff.blog.mapper.UserAuthMapper;
import com.yeff.blog.mapper.UserInfoMapper;
import com.yeff.blog.service.RedisService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.yeff.blog.constant.RedisPrefixConst.*;

/**
 * 用户详细信息业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/29
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private HttpServletRequest request;
//    @Resource
//    private RoleMapper roleMapper;
    @Resource
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 判空
        if (ObjectUtil.isNull(username)) {
            throw new BusinessException("用户名不能为空！");
        }
        // 查询数据库中是否存在该账号
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, username));
        if (ObjectUtil.isNull(userAuth)) {
            throw new BusinessException("用户名或者密码错误！");
        }
        List<String> list = new ArrayList<>(Arrays.asList("test"));
//        return convertUserDetail(userAuth);
        return new LoginUser(BeanUtil.copyProperties(userAuth, UserDetailDTO.class), list);
    }

    /**
     * 封装用户登录信息
     *
     * @param userAuth 用户账号
     * @return 用户登入信息
     */
    public UserDetailDTO convertUserDetail(UserAuth userAuth) {
        // 查询账号信息
        UserInfo userInfo = userInfoMapper.selectById(userAuth.getUserInfoId());
        // 查询账号角色
//        List<String> roleList = roleMapper.listRolesByUserInfoId(userInfo.getId());
        // 查询账号点赞信息
        Set<Object> articleLikeSet = redisService.sMembers(ARTICLE_USER_LIKE + userInfo.getId());
        Set<Object> commentLikeSet = redisService.sMembers(COMMENT_USER_LIKE + userInfo.getId());
        Set<Object> talkLikeSet = redisService.sMembers(TALK_USER_LIKE + userInfo.getId());
        // 获取设备信息
//        String ipAddress = IpUtils.getIpAddress(request);
//        String ipSource = IpUtils.getIpSource(ipAddress);
//        UserAgent userAgent = IpUtils.getUserAgent(request);
        // 封装权限集合
        return UserDetailDTO.builder()
                .id(userAuth.getId())
                .loginType(userAuth.getLoginType())
                .userInfoId(userInfo.getId())
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .email(userInfo.getEmail())
//                .roleList(roleList)
                .nickname(userInfo.getNickname())
                .avatar(userInfo.getAvatar())
                .intro(userInfo.getIntro())
                .webSite(userInfo.getWebSite())
                .articleLikeSet(articleLikeSet)
                .commentLikeSet(commentLikeSet)
                .talkLikeSet(talkLikeSet)
//                .ipAddress(ipAddress)
//                .ipSource(ipSource)
                .isDisable(userInfo.getIsDisable())
//                .browser(userAgent.getBrowser().getName())
//                .os(userAgent.getOperatingSystem().getName())
                .lastLoginTime(LocalDateTime.now(ZoneId.of("Asia/Beijing")))
                .build();
    }
}
