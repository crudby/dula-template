package com.crud.dula.platform.service;

import com.mybatisflex.core.service.IService;
import com.crud.dula.platform.entity.SysUserRole;

import java.util.List;

/**
 * 用户角色关联表 服务层。
 *
 * @author crud
 * @since 2024-04-19
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 配置用户角色关联表
     *
     * @param userId 用户ID
     * @param roleIds 角色ID集合
     */
    void config(Long userId, List<Long> roleIds);
}
