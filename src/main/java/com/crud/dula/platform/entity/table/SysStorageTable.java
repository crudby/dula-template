package com.crud.dula.platform.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 系统文件表 表定义层。
 *
 * @author crud
 * @since 2024-05-16
 */
public class SysStorageTable extends TableDef {

    /**
     * 系统文件表
     */
    public static final SysStorageTable SYS_STORAGE = new SysStorageTable();

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
     * 文件名称
     */
    public final QueryColumn FILENAME = new QueryColumn(this, "filename");

    /**
     * 访问路径
     */
    public final QueryColumn FILEPATH = new QueryColumn(this, "filepath");

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
     * 存储方式
     */
    public final QueryColumn STORAGE_TYPE = new QueryColumn(this, "storage_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, FILENAME, FILEPATH, STORAGE_TYPE, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public SysStorageTable() {
        super("", "sys_storage");
    }

}
