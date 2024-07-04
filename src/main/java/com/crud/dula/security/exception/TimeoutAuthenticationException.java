package com.crud.dula.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 会话超时
 *
 * @author crud
 * @date 2024/6/4
 */
public class TimeoutAuthenticationException extends AuthenticationException {
    public TimeoutAuthenticationException(String msg) {
        super(msg);
    }

    public TimeoutAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
