package com.crud.dula.platform.config;

import com.crud.dula.security.session.LoginSession;
import com.crud.dula.security.LoginUser;
import com.mybatisflex.core.tenant.TenantFactory;

import java.util.Optional;

/**
 * 系统用户租户工厂
 *
 * @author crud
 * @date 2023/11/21
 */
public class SystemTenantFactory implements TenantFactory {

    private final LoginSession loginSession;

    public SystemTenantFactory(LoginSession loginSession) {
        this.loginSession = loginSession;
    }

    @Override
    public Object[] getTenantIds() {
        Optional<LoginUser> optional = loginSession.getUser();
        if (optional.isPresent()) {
            return new String[]{optional.get().getSysUser().getTenantId()};
        }
        return new Object[0];
    }
}
