package com.crud.dula.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author crud
 * @date 2024/4/30
 */
@ApiModel(description = "系统组织DTO")
@Data
public class SysGroupDTO {

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
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    @NotBlank(message = "组织名称不能为空")
    private String groupName;

    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    @NotBlank(message = "组织编码不能为空")
    private String groupCode;

    /**
     * 是否启用，0：否，1：是
     */
    @ApiModelProperty("是否启用，0：否，1：是")
    private Boolean enabled;

}
