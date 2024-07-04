package com.crud.dula.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 异地登录
 *
 * @author crud
 * @date 2024/6/4
 */
public class OffsiteLoginAuthenticationException extends AuthenticationException {
    public OffsiteLoginAuthenticationException(String msg) {
        super(msg);
    }

    public OffsiteLoginAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
