package com.crud.dula.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单操作视图对象
 *
 * @author crud
 * @date 2024/5/16
 */
@ApiModel(description = "菜单操作视图对象")
@Data
public class SysMenuOperateVO {

    /**
     * Id
     */
    @ApiModelProperty("Id")
    private Long id;

    /**
     * 菜单Id
     */
    @ApiModelProperty("菜单Id")
    private Long menuId;

    /**
     * 操作名称
     */
    @ApiModelProperty("操作名称")
    private String operateName;

    /**
     * 操作编码
     */
    @ApiModelProperty("操作编码")
    private String operateCode;

}
