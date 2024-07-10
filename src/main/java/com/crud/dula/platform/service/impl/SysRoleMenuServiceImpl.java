package com.crud.dula.platform.service.impl;

import com.crud.dula.common.base.BaseQuery;
import com.crud.dula.common.base.BaseServiceImpl;
import com.crud.dula.platform.entity.SysRoleMenu;
import com.crud.dula.platform.entity.table.SysRoleMenuTable;
import com.crud.dula.platform.mapper.SysRoleMenuMapper;
import com.crud.dula.platform.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author crud
 * @date 2024/4/19
 */
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu, BaseQuery> implements SysRoleMenuService {

    @Override
    @Transactional
    public void config(Long roleId, List<Long> menuIds) {
        this.physicalRemove(SysRoleMenuTable.SYS_ROLE_MENU.ROLE_ID.eq(roleId));
        if (menuIds != null && !menuIds.isEmpty()) {
            menuIds.forEach(menuId -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menuId);
                this.save(sysRoleMenu);
            });
        }
    }
}
