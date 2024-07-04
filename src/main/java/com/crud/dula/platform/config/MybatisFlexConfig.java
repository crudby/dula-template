package com.crud.dula.platform.config;


import com.crud.dula.common.base.BaseEntity;
import com.crud.dula.common.id.GlobalUtil;
import com.crud.dula.security.session.LoginSession;
import com.crud.dula.security.LoginUser;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.tenant.TenantFactory;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * mybatis-flex 配置
 *
 * @author crud
 * @date 2023/9/15
 */
@Configuration
public class MybatisFlexConfig implements MyBatisFlexCustomizer {

    @Resource
    private LoginSession loginSession;

    @Override
    public void customize(FlexGlobalConfig flexGlobalConfig) {
        // 新增填充
        flexGlobalConfig.registerInsertListener(obj -> {
            if (obj instanceof BaseEntity) {
                if (Objects.nonNull(((BaseEntity) obj).getId())) {
                    return;
                }
                LoginUser user = loginSession.getLoginUser();
                Long userId = user.getSysUser().getId();
                String tenantId = user.getSysUser().getTenantId();

                LocalDateTime now = LocalDateTime.now();
                ((BaseEntity) obj).setId(GlobalUtil.getNextId());
                ((BaseEntity) obj).setCreateTime(now);
                ((BaseEntity) obj).setReviseTime(now);
                ((BaseEntity) obj).setCreator(userId);
                ((BaseEntity) obj).setReviser(userId);
                ((BaseEntity) obj).setTenantId(tenantId);
            }
        }, BaseEntity.class);
        // 更新填充
        flexGlobalConfig.registerUpdateListener(obj -> {
            if (obj instanceof BaseEntity) {
                loginSession.getUser().ifPresent(user -> {
                    LocalDateTime now = LocalDateTime.now();
                    ((BaseEntity) obj).setReviseTime(now);
                    ((BaseEntity) obj).setReviser(user.getSysUser().getId());
                });
            }
        }, BaseEntity.class);

        flexGlobalConfig.setTenantColumn("tenant_id");
    }

    @Bean
    public TenantFactory tenantFactory(){
        return new SystemTenantFactory(loginSession);
    }
}
