package com.crud.dula.platform.service;

import com.mybatisflex.core.service.IService;
import com.crud.dula.platform.entity.SysRoleMenu;

import java.util.List;

/**
 * 角色菜单关联表 服务层。
 *
 * @author crud
 * @since 2024-04-19
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 角色菜单关联表配置。
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     */
    void config(Long roleId, List<Long> menuIds);
}
