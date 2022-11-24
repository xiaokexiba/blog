package com.yeff.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户账号实体类
 *
 * @author xoke
 * @date 2022/11/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_auth")
public class UserAuth {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户信息id
     */
    private Integer userInfoId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录方式
     * 0 - 邮箱登入
     * 1 - QQ登入
     * 2 - 微信登入
     */
    private Integer loginType;

    /**
     * 用户登录ip地址
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;
}

