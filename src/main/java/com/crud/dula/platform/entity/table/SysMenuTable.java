package com.crud.dula.platform.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 菜单信息 表定义层。
 *
 * @author crud
 * @since 2024-05-16
 */
public class SysMenuTable extends TableDef {

    /**
     * 菜单信息
     */
    public static final SysMenuTable SYS_MENU = new SysMenuTable();

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
     * 是否启用，0：否，1：是
     */
    public final QueryColumn ENABLED = new QueryColumn(this, "enabled");

    /**
     * 菜单路径
     */
    public final QueryColumn MENU_URL = new QueryColumn(this, "menu_url");

    /**
     * 修改人
     */
    public final QueryColumn REVISER = new QueryColumn(this, "reviser");

    /**
     * 菜单编码
     */
    public final QueryColumn MENU_CODE = new QueryColumn(this, "menu_code");

    /**
     * 菜单icon
     */
    public final QueryColumn MENU_ICON = new QueryColumn(this, "menu_icon");

    /**
     * 菜单名称
     */
    public final QueryColumn MENU_NAME = new QueryColumn(this, "menu_name");

    /**
     * 父级ID
     */
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PARENT_ID, MENU_ICON, MENU_NAME, MENU_CODE, MENU_URL, ENABLED, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public SysMenuTable() {
        super("", "sys_menu");
    }

}
