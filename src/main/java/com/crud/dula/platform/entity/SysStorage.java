package com.crud.dula.platform.entity;

import com.crud.dula.common.base.BaseEntity;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统文件表 实体类。
 *
 * @author crud
 * @since 2024-05-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "sys_storage")
public class SysStorage extends BaseEntity {

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 访问路径
     */
    private String filepath;

    /**
     * 存储方式
     */
    private String storageType;

}
