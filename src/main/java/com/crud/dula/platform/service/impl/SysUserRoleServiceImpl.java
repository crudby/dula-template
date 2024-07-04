package com.crud.dula.platform.service.impl;


import com.crud.dula.common.base.BaseQuery;
import com.crud.dula.common.base.BaseServiceImpl;
import com.crud.dula.platform.entity.SysUserRole;
import com.crud.dula.platform.entity.table.SysUserRoleTable;
import com.crud.dula.platform.mapper.SysUserRoleMapper;
import com.crud.dula.platform.service.SysUserRoleService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author crud
 * @date 2023/9/15
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole, BaseQuery> implements SysUserRoleService {

    @Override
    @Transactional
    public void config(Long userId, List<Long> roleIds) {
        this.remove(QueryWrapper.create().where(SysUserRoleTable.SYS_USER_ROLE.USER_ID.eq(userId)));
        if (roleIds != null && !roleIds.isEmpty()) {
            roleIds.forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                this.save(sysUserRole);
            });
        }
    }
}
