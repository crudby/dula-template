package com.crud.dula.common.base;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;

import java.util.List;

/**
 * 服务层基类
 *
 * @author crud
 * @date 2023/11/22
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity, Query extends BaseQuery> extends ServiceImpl<M, T> implements BaseService<T, Query> {

    @Override
    public List<T> list(Query query) {
        return super.list(newWrapper(query).orderBy(this.orderByParams()));
    }

    @Override
    public Page<T> page(BasePage page, Query query) {
        Page<T> objectPage = Page.of(page.getPageNumber(), page.getPageSize());
        return super.page(objectPage, newWrapper(query).orderBy(this.orderByParams()));
    }
}
