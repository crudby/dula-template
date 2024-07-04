package com.crud.dula.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 未登录
 *
 * @author crud
 * @date 2024/6/4
 */
public class NotLoginAuthenticationException extends AuthenticationException {
    public NotLoginAuthenticationException(String msg) {
        super(msg);
    }

    public NotLoginAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
