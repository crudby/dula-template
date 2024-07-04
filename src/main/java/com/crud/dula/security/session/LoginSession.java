package com.crud.dula.security.session;

import com.crud.dula.common.result.BizException;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

/**
 * @author crud
 * @date 2023/9/18
 */
public interface LoginSession {

    /**
     * 获取用户信息
     * @param uid uid
     * @return LoginUser
     */
    LoginUser get(Long uid);

    /**
     * 存储用户信息
     * @param uid uid
     * @param user user
     */
    void set(Long uid, LoginUser user);

    /**
     * 移除用户信息
     * @param uid uid
     */
    void remove(Long uid);

    /**
     * 获取当前登录用户信息
     * @return Optional
     */
    default Optional<LoginUser> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof LoginUser) {
                return Optional.of((LoginUser) principal);
            }
        }
        return Optional.empty();
    }

    default LoginUser getLoginUser() {
        return getUser().orElseThrow(() -> new BizException(ResultCode.NOT_LOGIN));
    }

    default Long getUserId() {
        return getLoginUser().getSysUser().getId();
    }

}
