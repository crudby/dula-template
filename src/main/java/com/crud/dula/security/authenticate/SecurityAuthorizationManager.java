package com.crud.dula.security.authenticate;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Supplier;

/**
 * @author crud
 * @date 2024/4/29
 */
@Component
public class SecurityAuthorizationManager  implements AuthorizationManager<RequestAuthorizationContext> {


    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        // todo 校验角色的权限
        Object principal = authentication.get().getPrincipal();
        HttpServletRequest request = object.getRequest();
        return new AuthorizationDecision(true);
    }
}
