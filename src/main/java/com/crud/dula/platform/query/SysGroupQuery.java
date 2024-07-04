package com.crud.dula.platform.query;

import com.crud.dula.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author crud
 * @date 2024/4/30
 */
@ApiModel(description = "组织查询")
@EqualsAndHashCode(callSuper = true)
@Data
public class SysGroupQuery extends BaseQuery {

    /**
     * 是否启用，0：否，1：是
     */
    @ApiModelProperty("是否启用，0：否，1：是")
    private Boolean enabled;

}
