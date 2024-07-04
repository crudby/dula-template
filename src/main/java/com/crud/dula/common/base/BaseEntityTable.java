package com.crud.dula.common.base;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 基础表定义层。
 *
 * @author crud
 * @since 2023-11-21
 */
public class BaseEntityTable extends TableDef {

    /**
     * 物品分类
     */
    public static final BaseEntityTable BASE_ENTITY_TABLE = new BaseEntityTable();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 创建人
     */
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");

    /**
     * 是否删除，0：否，1：是
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 修改人
     */
    public final QueryColumn REVISER = new QueryColumn(this, "reviser");

    /**
     * 租户
     */
    public final QueryColumn TENANT_ID = new QueryColumn(this, "tenant_id");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 修改时间
     */
    public final QueryColumn REVISE_TIME = new QueryColumn(this, "revise_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public BaseEntityTable() {
        super("", "");
    }

}
