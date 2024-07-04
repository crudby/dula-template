package com.crud.dula.platform.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 用户角色关联表 表定义层。
 *
 * @author crud
 * @since 2024-05-16
 */
public class SysUserRoleTable extends TableDef {

    /**
     * 用户角色关联表
     */
    public static final SysUserRoleTable SYS_USER_ROLE = new SysUserRoleTable();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色ID
     */
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");

    /**
     * 用户ID
     */
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, USER_ID, ROLE_ID, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public SysUserRoleTable() {
        super("", "sys_user_role");
    }

}
