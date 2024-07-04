package com.crud.dula.platform.entity;

import com.crud.dula.common.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单操作信息 实体类。
 *
 * @author crud
 * @since 2024-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_menu_operate")
public class SysMenuOperate extends BaseEntity {

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String operateName;

    /**
     * 菜单编码
     */
    private String operateCode;

}
