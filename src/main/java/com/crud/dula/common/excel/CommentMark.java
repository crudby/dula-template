package com.crud.dula.common.excel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 注释标记
 *
 * @author crud
 * @date 2023/3/17 13:38
 */
@AllArgsConstructor
@Data
public class CommentMark {

    /**
     * 行号
     */
    private int row;

    /**
     * 列号
     */
    private int column;

    /**
     * 标注信息
     */
    private String comment;

}
