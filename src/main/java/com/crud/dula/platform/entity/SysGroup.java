package com.crud.dula.platform.entity;

import com.crud.dula.common.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织信息 实体类。
 *
 * @author crud
 * @since 2024-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_group")
public class SysGroup extends BaseEntity {

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 组织icon
     */
    private String groupIcon;

    /**
     * 组织名称
     */
    private String groupName;

    /**
     * 组织编码
     */
    private String groupCode;

    /**
     * 是否启用，0：否，1：是
     */
    private Boolean enabled;

}
