package com.crud.dula.platform.entity;

import com.crud.dula.common.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单信息 实体类。
 *
 * @author crud
 * @since 2024-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 菜单icon
     */
    private String menuIcon;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单路径
     */
    private String menuUrl;

    /**
     * 是否启用，0：否，1：是
     */
    private Boolean enabled;

}
