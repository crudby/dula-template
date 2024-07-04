package com.crud.dula.common.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author crud
 * @date 2023/9/15
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public HttpResult<String> exceptionHandler(Exception e) {
        log.error("系统发生异常", e);
        if (e instanceof BizException) {
            return HttpResult.fail((BizException) e);
        }

        if (e instanceof AuthenticationException) {
            if (e instanceof UsernameNotFoundException || e instanceof InternalAuthenticationServiceException) {
                // 用户不存在
                return HttpResult.fail(ResultCode.USER_NOT_EXIST);
            }
            if (e instanceof BadCredentialsException) {
                // 密码错误
                return HttpResult.fail(ResultCode.USER_CREDENTIALS_ERROR);
            }
            if (e instanceof LockedException) {
                // 账号锁定
                return HttpResult.fail(ResultCode.USER_ACCOUNT_LOCKED);
            }
            if (e instanceof DisabledException) {
                // 账号不可用
                return HttpResult.fail(ResultCode.USER_ACCOUNT_DISABLE);
            }
            if (e instanceof AccountExpiredException) {
                // 账号过期
                return HttpResult.fail(ResultCode.USER_ACCOUNT_EXPIRED);
            }
            if (e instanceof CredentialsExpiredException) {
                // 密码过期
                return HttpResult.fail(ResultCode.USER_CREDENTIALS_EXPIRED);
            }
        }
        return HttpResult.fail(ResultCode.SYSTEM_ERROR);
    }
}
