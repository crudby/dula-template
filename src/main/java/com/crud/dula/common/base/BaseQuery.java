package com.crud.dula.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 查询基类
 *
 * @author crud
 * @date 2023/11/22
 */
@Data
public class BaseQuery {

    /**
     * 创建时间 -开始
     */
    @ApiModelProperty("创建时间 -开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createStartTime;

    /**
     * 创建时间 -结束
     */
    @ApiModelProperty("创建时间 -结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createEndTime;

    /**
     * 修改时间 -开始
     */
    @ApiModelProperty("修改时间 -开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviseStartTime;

    /**
     * 修改时间 -结束
     */
    @ApiModelProperty("修改时间 -结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reviseEndTime;
}
