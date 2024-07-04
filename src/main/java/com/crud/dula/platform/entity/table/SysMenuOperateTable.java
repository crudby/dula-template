package com.crud.dula.platform.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 菜单操作信息 表定义层。
 *
 * @author crud
 * @since 2024-05-16
 */
public class SysMenuOperateTable extends TableDef {

    /**
     * 菜单操作信息
     */
    public static final SysMenuOperateTable SYS_MENU_OPERATE = new SysMenuOperateTable();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 菜单ID
     */
    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");

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
     * 菜单编码
     */
    public final QueryColumn OPERATE_CODE = new QueryColumn(this, "operate_code");

    /**
     * 菜单名称
     */
    public final QueryColumn OPERATE_NAME = new QueryColumn(this, "operate_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, MENU_ID, OPERATE_NAME, OPERATE_CODE, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public SysMenuOperateTable() {
        super("", "sys_menu_operate");
    }

}
