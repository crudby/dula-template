package com.crud.dula.platform.service.impl;

import com.crud.dula.common.base.BaseQuery;
import com.crud.dula.common.base.BaseServiceImpl;
import com.crud.dula.platform.entity.SysUserGroup;
import com.crud.dula.platform.entity.table.SysUserGroupTable;
import com.crud.dula.platform.mapper.SysUserGroupMapper;
import com.crud.dula.platform.service.SysUserGroupService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author crud
 * @date 2024/4/30
 */
@Service
public class SysUserGroupServiceImpl extends BaseServiceImpl<SysUserGroupMapper, SysUserGroup, BaseQuery> implements SysUserGroupService {

    @Override
    @Transactional
    public void config(Long userId, List<Long> groupIds) {
        this.remove(QueryWrapper.create().where(SysUserGroupTable.SYS_USER_GROUP.USER_ID.eq(userId)));
        if (groupIds != null && !groupIds.isEmpty()) {
            groupIds.forEach(groupId -> {
                SysUserGroup sysUserGroup = new SysUserGroup();
                sysUserGroup.setUserId(userId);
                sysUserGroup.setGroupId(groupId);
                this.save(sysUserGroup);
            });
        }
    }
}
