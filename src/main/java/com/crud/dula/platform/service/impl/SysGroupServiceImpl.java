package com.crud.dula.platform.service.impl;

import com.crud.dula.common.base.BaseServiceImpl;
import com.crud.dula.platform.entity.SysGroup;
import com.crud.dula.platform.entity.table.SysGroupTable;
import com.crud.dula.platform.mapper.SysGroupMapper;
import com.crud.dula.platform.query.SysGroupQuery;
import com.crud.dula.platform.service.SysGroupService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author crud
 * @date 2024/4/30
 */
@Service
public class SysGroupServiceImpl extends BaseServiceImpl<SysGroupMapper, SysGroup, SysGroupQuery> implements SysGroupService {

    @Override
    public QueryWrapper newWrapper(SysGroupQuery query) {
        QueryWrapper wrapper = super.newWrapper(query);
        if (Objects.nonNull(query.getEnabled())) {
            wrapper.and(SysGroupTable.SYS_GROUP.ENABLED.eq(query.getEnabled()));
        }
        return wrapper;
    }

}
