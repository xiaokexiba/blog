package com.yeff.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeff.blog.dto.EmailDTO;
import com.yeff.blog.dto.UserDTO;
import com.yeff.blog.dto.UserDetailDTO;
import com.yeff.blog.entity.LoginUser;
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
import com.yeff.blog.utils.JwtUtils;
import com.yeff.blog.utils.RegexUtils;
import com.yeff.blog.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.yeff.blog.constant.CommonConst.DEFAULT_NICKNAME;
import static com.yeff.blog.constant.RedisPrefixConst.*;
import static com.yeff.blog.enums.RoleEnum.USER;

/**
 * 用户账号业务层接口实现类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Slf4j
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
    @Resource
    private AuthenticationManager authenticationManager;

    private static final String SALT = "*(sdlfj^$%";

    /**
     * 用户注册
     *
     * @param userVO 用户VO
     * @return 返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result register(UserVO userVO) {
        // 判断是否已经注册
        if (checkUser(userVO)) {
            throw new BusinessException("邮箱已被注册！");
        }
        try {
            LocalDateTime now = LocalDateTime.now();
            // 创建用户信息
            UserInfo userInfo = UserInfo.builder()
                    .email(userVO.getUsername())
                    .nickname(DEFAULT_NICKNAME + IdWorker.getId())
                    .avatar("")
                    .createTime(now)
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
                    .userInfoId(userInfo.getId())
                    .username(userVO.getUsername())
                    .password(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))
                    .createTime(now)
                    .build();
            // 新增用户账号
            userAuthMapper.insert(userAuth);
        } catch (Exception e) {
            log.info("这里好像有点问题！");
            e.printStackTrace();
        }
        return Result.ok();
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 返回结果
     */
    @Override
    public Result sendCode(String email) {
        // 校验手机号
        if (RegexUtils.isEmailInvalid(email)) {
            // 不符合，返回错误信息
            return Result.fail("邮箱格式错误，请输入正确的邮箱！");
        }
        // 符合，生成验证码（随机6位数字即可）
        String code = RandomUtil.randomNumbers(6);
        EmailDTO emailDTO = EmailDTO.builder()
                .email(email)
                .subject("验证码")
                .content("您的验证码为 " + code + " 有效期5分钟，请不要告诉他人哦！")
                .build();
        // 保存验证码到 redis
        redisService.set(USER_CODE_KEY + email, code, CODE_EXPIRE_TIME);
        // 发送验证码（由于业务有点复杂，后面研究一下怎么发短信，这里简单发送日志意思一下）
        log.info("发送验证码成功，验证码：{}", code);
        // 返回ok
        return Result.ok();
    }

    /**
     * 用户登入
     *
     * @param userVO 用户VO
     * @return 返回结果
     */
    @Override
    public Result login(UserVO userVO) {
        // 先从redis中判断验证码
        if (!userVO.getCode().equals(redisService.get(USER_CODE_KEY + userVO.getUsername()))) {
            throw new BusinessException("请重新输入验证码！");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userVO.getUsername(), userVO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 如果认证不通过，抛异常
        if (Objects.isNull(authenticate)) {
            throw new BusinessException("用户名或密码错误");
        }
        // 使用userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUserDetailDTO().getId().toString();
        String jwt = JwtUtils.createJWT(userId);
        // authenticate存入redis
        redisService.set(LOGIN_KEY + userId, loginUser);
        /*UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, userVO.getUsername()));
        if (userAuth == null || !BCrypt.checkpw(userVO.getPassword(), userAuth.getPassword())) {
            throw new BusinessException("用户名或者密码错误，请重新输入！");
        }
        // 随机生成 token，作为登入令牌
        String token = UUID.randomUUID().toString();
        // 将user对象脱敏后转化成HashMap进行存储
        UserDTO userDTO = BeanUtil.copyProperties(userVO, UserDTO.class);
        Map<String, Object> map = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 存储
        String tokenKey = LOGIN_USER_KEY + token;
        redisService.hSetAll(tokenKey, map);
        // 设置 token 有效期
        redisService.expire(tokenKey, LOGIN_USER_TTL);
        // 返回 token*/
        // 把token响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return Result.ok(map);
    }

    /**
     * 退出账号
     *
     * @return 返回结果
     */
    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        int userId = loginUser.getUserDetailDTO().getId();
        redisService.del("login:" + userId);
        return Result.ok();
    }


    /**
     * 校验用户数据是否合法
     *
     * @param userVO 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO userVO) {
        // 首先先判断验证码
        if (!userVO.getCode().equals(redisService.get(USER_CODE_KEY + userVO.getUsername()))) {
            throw new BusinessException("请重新输入验证码！");
        }
        // 查询用户名是否存在
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, userVO.getUsername()));
        return Objects.nonNull(userAuth);
    }
}




