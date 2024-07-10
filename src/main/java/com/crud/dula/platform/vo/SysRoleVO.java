package com.crud.dula.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author crud
 * @date 2024/4/29
 */
@ApiModel(description = "系统角色VO")
@Data
public class SysRoleVO {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;

    /**
     * 是否启用，0：否，1：是
     */
    @ApiModelProperty("是否启用，0：否，1：是")
    private Boolean enabled;

    /**
     * 菜单列表
     */
    @ApiModelProperty("菜单列表")
    private List<Long> menuList;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private Long creator;

    /**
     * 创建者姓名
     */
    @ApiModelProperty("创建者姓名")
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private Long reviser;

    /**
     * 更新者姓名
     */
    @ApiModelProperty("更新者姓名")
    private String reviserName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime reviseTime;

}
