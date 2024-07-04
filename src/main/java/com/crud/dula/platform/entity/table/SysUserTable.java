package com.crud.dula.platform.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 用户信息 表定义层。
 *
 * @author crud
 * @since 2024-05-16
 */
public class SysUserTable extends TableDef {

    /**
     * 用户信息
     */
    public static final SysUserTable SYS_USER = new SysUserTable();

    /**
     * 主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 手机号码
     */
    public final QueryColumn PHONE = new QueryColumn(this, "phone");

    /**
     * 头像
     */
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");

    /**
     * 性别
     */
    public final QueryColumn GENDER = new QueryColumn(this, "gender");

    /**
     * openID
     */
    public final QueryColumn OPEN_ID = new QueryColumn(this, "open_id");

    /**
     * 创建人
     */
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");

    /**
     * 是否删除，0：否，1：是
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 帐号状态（1启用 0禁用）
     */
    public final QueryColumn ENABLED = new QueryColumn(this, "enabled");

    /**
     * 最后登录IP
     */
    public final QueryColumn LOGIN_IP = new QueryColumn(this, "login_ip");

    /**
     * 修改人
     */
    public final QueryColumn REVISER = new QueryColumn(this, "reviser");

    /**
     * 用户昵称
     */
    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");

    /**
     * 密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 租户
     */
    public final QueryColumn TENANT_ID = new QueryColumn(this, "tenant_id");

    /**
     * 用户姓名
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 最后登录时间
     */
    public final QueryColumn LOGIN_TIME = new QueryColumn(this, "login_time");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 是否注册 0：否，1：是
     */
    public final QueryColumn REGISTERED = new QueryColumn(this, "registered");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, OPEN_ID, USERNAME, NICKNAME, PHONE, EMAIL, PASSWORD, REGISTERED, GENDER, AVATAR, LOGIN_IP, LOGIN_TIME, ENABLED, DELETED, CREATE_TIME, CREATOR, REVISE_TIME, REVISER, TENANT_ID};

    public SysUserTable() {
        super("", "sys_user");
    }

}
