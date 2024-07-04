package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author crud
 * @date 2024/4/30
 */
@ApiModel(description = "系统菜单DTO")
@Data
public class SysMenuDTO {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 父级ID
     */
    @ApiModelProperty("父级ID")
    private Long parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;

    /**
     * 菜单路径
     */
    @ApiModelProperty("菜单路径")
    private String menuUrl;

    /**
     * 是否启用，0：否，1：是
     */
    @ApiModelProperty("是否启用，0：否，1：是")
    private Boolean enabled;

    /**
     * 菜单操作ID集合
     */
    @ApiModelProperty("菜单操作ID集合")
    private List<Long> menuOperateIds;
}
