package com.crud.dula.security.provider;

import com.crud.dula.common.result.BizException;
import com.crud.dula.common.result.ResultCode;
import com.crud.dula.security.service.WechatService;
import com.crud.dula.security.third.WechatAuthenticationToken;
import com.crud.dula.security.LoginUser;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 微信验证
 * @author crud
 * @date 2023/9/14
 */
@Getter
public class WechatAuthenticationProvider implements AuthenticationProvider {

    private final WechatService wechatService;

    public WechatAuthenticationProvider(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object principal = authentication.getPrincipal();
        LoginUser loginUser = wechatService.loginByCode((String) principal);
        if (null == loginUser) {
            throw new BizException(ResultCode.NOT_LOGIN.code, ResultCode.NOT_LOGIN.message);
        }
        return new WechatAuthenticationToken(loginUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WechatAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
