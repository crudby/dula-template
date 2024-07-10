package com.crud.dula.platform.entity;

import com.crud.dula.common.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色信息 实体类。
 *
 * @author crud
 * @since 2024-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色icon
     */
    private String roleIcon;

    /**
     * 是否启用，0：否，1：是
     */
    private Boolean enabled;

}
