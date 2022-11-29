package com.yeff.blog.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yeff.blog.dto.UserDetailDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.yeff.blog.constant.CommonConst.FALSE;

/**
 * 登入用户类
 *
 * @author xoke
 * @date 2022/11/29
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 用户
     */
    private UserDetailDTO userDetailDTO;

    /**
     * 存储权限信息
     */
    private List<String> permissions;


    public LoginUser(UserDetailDTO userDetailDTO, List<String> permissions) {
        this.userDetailDTO = userDetailDTO;
        this.permissions = permissions;
    }


    /**
     * 存储SpringSecurity所需要的权限信息的集合
     */
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }
        // 把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return userDetailDTO.getRoleList().stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());
//    }

    @Override
    public String getPassword() {
        return userDetailDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetailDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
