package com.crud.dula.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author crud
 * @date 2024/4/30
 */
@ApiModel(description = "系统菜单树形VO")
@Data
public class SysMenuTreeVO {

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
    private String menuName;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
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
     * 菜单操作列表
     */
    private List<SysMenuOperateVO> menuOperates;

    /**
     * 子节点
     */
    @ApiModelProperty("子节点")
    private List<SysMenuTreeVO> children;

}
