package com.crud.dula.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页参数
 *
 * @author crud
 * @date 2024/5/20 10:18
 */
@ApiModel(description = "分页参数")
@Data
public class BasePage {

    /**
     * 当前页码
     */
    @ApiModelProperty("当前页码")
    private Integer pageNumber = 1;

    /**
     * 每页条数
     */
    @ApiModelProperty("每页条数")
    private Integer pageSize = 15;

}
