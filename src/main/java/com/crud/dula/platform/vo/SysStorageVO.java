package com.crud.dula.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件对象
 *
 * @author crud
 * @date 2024/5/16
 */
@ApiModel(description = "文件对象")
@Data
public class SysStorageVO {

    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String filename;

    /**
     * 访问地址
     */
    @ApiModelProperty("访问地址")
    private String filepath;

}
