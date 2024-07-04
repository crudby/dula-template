package com.crud.dula.platform.service;

import com.mybatisflex.core.service.IService;
import com.crud.dula.platform.entity.SysUserGroup;

import java.util.List;

/**
 * 用户组织关联表 服务层。
 *
 * @author crud
 * @since 2024-04-30
 */
public interface SysUserGroupService extends IService<SysUserGroup> {

    /**
     * 配置用户组织关联表
     *
     * @param userId   用户ID
     * @param groupIds 组织ID集合
     */
    void config(Long userId, List<Long> groupIds);
}
