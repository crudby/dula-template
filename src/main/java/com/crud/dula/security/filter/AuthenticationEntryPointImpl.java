package com.crud.dula.security.filter;

import com.crud.dula.common.result.HttpResult;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.common.result.ResultUtil;
import com.crud.dula.security.exception.NotLoginAuthenticationException;
import com.crud.dula.security.exception.OffsiteLoginAuthenticationException;
import com.crud.dula.security.exception.TimeoutAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author crud
 * @date 2023/9/15
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof NotLoginAuthenticationException) {
            ResultUtil.writeResponseResult(response, HttpResult.fail(ResultCode.NOT_LOGIN.getCode(), authException.getMessage()));
            return;
        }
        if (authException instanceof TimeoutAuthenticationException) {
            ResultUtil.writeResponseResult(response, HttpResult.fail(ResultCode.LOGIN_TIMEOUT.getCode(), authException.getMessage()));
            return;
        }
        if (authException instanceof OffsiteLoginAuthenticationException) {
            ResultUtil.writeResponseResult(response, HttpResult.fail(ResultCode.OFFSITE_LOGIN.getCode(), authException.getMessage()));
            return;
        }
        ResultUtil.writeResponseResult(response, HttpResult.fail(ResultCode.NOT_LOGIN));
    }
}
