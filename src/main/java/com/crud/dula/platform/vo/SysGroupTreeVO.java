package com.crud.dula.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author crud
 * @date 2024/4/30
 */
@ApiModel(description = "系统组织树形VO")
@Data
public class SysGroupTreeVO {

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
    private String groupName;

    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    private String groupCode;

    /**
     * 是否启用，0：否，1：是
     */
    @ApiModelProperty("是否启用，0：否，1：是")
    private Boolean enabled;

    /**
     * 子组织
     */
    @ApiModelProperty("子组织")
    private List<SysGroupTreeVO> children;

}
