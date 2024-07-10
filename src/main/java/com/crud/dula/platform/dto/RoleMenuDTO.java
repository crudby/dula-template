package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author crud
 * @date 2024/7/9 10:23
 */
@ApiModel(description = "")
@Data
public class RoleMenuDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private List<Long> menuIds;

}
