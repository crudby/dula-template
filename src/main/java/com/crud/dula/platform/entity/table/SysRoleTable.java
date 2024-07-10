package com.crud.dula.platform.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 角色信息 表定义层。
 *
 * @author changl
 * @since 2024-07-09
 */
public class SysRoleTable extends TableDef {

    /**
     * 角色信息
     */
    public static final SysRoleTable SYS_ROLE = new SysRoleTable();

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
     * 修改人
     */
    public final QueryColumn REVISER = new QueryColumn(this, "reviser");

    /**
     * 角色编码
     */
    public final QueryColumn ROLE_CODE = new QueryColumn(this, "role_code");

    /**
     * 角色icon
     */
    public final QueryColumn ROLE_ICON = new QueryColumn(this, "role_icon");

    /**
     * 角色名称
     */
    public final QueryColumn ROLE_NAME = new QueryColumn(this, "role_name");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_NAME, ROLE_CODE, ROLE_ICON, ENABLED, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public SysRoleTable() {
        super("", "sys_role");
    }

}
