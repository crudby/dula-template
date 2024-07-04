package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author crud
 * @date 2024/4/29
 */
@ApiModel(description = "系统角色DTO")
@Data
public class SysRoleDTO {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 是否启用，0：否，1：是
     */
    @ApiModelProperty("是否启用，0：否，1：是")
    private Boolean enabled;
}
