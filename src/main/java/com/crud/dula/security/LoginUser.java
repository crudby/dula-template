package com.crud.dula.security;

import com.crud.dula.platform.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author crud
 * @date 2023/9/14
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 用户信息
     */
    private SysUser sysUser;

    /**
     * 登录戳
     */
    private String signStamp;

    /**
     * 用户权限
     */
    private List<String> permissions;

    public LoginUser(SysUser sysUser, List<String> permissions) {
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(permissions).map(list -> list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())).orElse(new ArrayList<>());
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return sysUser.getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.getEnabled();
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getEnabled();
    }
}
