package com.crud.dula.common.base;

import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryOrderBy;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;

import java.util.List;
import java.util.Objects;

/**
 * 服务层接口
 *
 * @author crud
 * @date 2023/11/22
 */
public interface BaseService<T extends BaseEntity, Query extends BaseQuery> extends IService<T> {

    default QueryOrderBy[] orderByParams() {
        return new QueryOrderBy[]{BaseEntityTable.BASE_ENTITY_TABLE.CREATE_TIME.desc()};
    }

    default QueryWrapper newWrapper(Query query) {
        QueryWrapper wrapper = query();
        if (Objects.nonNull(query.getCreateStartTime())) {
            wrapper.and(BaseEntityTable.BASE_ENTITY_TABLE.CREATE_TIME.ge(query.getCreateStartTime()));
        }
        if (Objects.nonNull(query.getCreateEndTime())) {
            wrapper.and(BaseEntityTable.BASE_ENTITY_TABLE.CREATE_TIME.le(query.getCreateEndTime()));
        }
        if (Objects.nonNull(query.getReviseStartTime())) {
            wrapper.and(BaseEntityTable.BASE_ENTITY_TABLE.CREATE_TIME.ge(query.getReviseStartTime()));
        }
        if (Objects.nonNull(query.getReviseEndTime())) {
            wrapper.and(BaseEntityTable.BASE_ENTITY_TABLE.CREATE_TIME.le(query.getReviseEndTime()));
        }
        return wrapper;
    }

    default void physicalRemove(QueryCondition condition) {
        LogicDeleteManager.execWithoutLogicDelete(() -> {
            this.remove(condition);
        });
    }

    default void physicalRemove(QueryWrapper wrapper) {
        LogicDeleteManager.execWithoutLogicDelete(() -> {
            this.remove(wrapper);
        });
    }

    List<T> list(Query query);

    Page<T> page(BasePage page, Query query);
}
