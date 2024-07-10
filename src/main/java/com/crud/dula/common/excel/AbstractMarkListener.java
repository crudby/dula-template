package com.crud.dula.common.excel;

import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标注标记处理
 *
 * @author crud
 * @date 2023/3/17 10:41
 */
public abstract class AbstractMarkListener<T> extends AnalysisEventListener<T> {

    /**
     * 记录所有数据
     */
    private final List<T> excelDataList = new ArrayList<>();

    /**
     * 记录所以标注
     */
    private final Map<Integer, List<CommentMark>> excelCommentMap = new HashMap<>();

    /**
     * getExcelDataList
     *
     * @return excelDataList
     */
    public List<T> getExcelDataList() {
        return excelDataList;
    }

    /**
     * getExcelCommentMap
     *
     * @return excelCommentMap
     */
    public Map<Integer, List<CommentMark>> getExcelCommentMap() {
        return excelCommentMap;
    }

    /**
     * 添加数据
     *
     * @param data data
     */
    public void addExcelData(T data) {
        excelDataList.add(data);
    }

    /**
     * 添加标注
     *
     * @param row     row
     * @param column  column
     * @param comment comment
     */
    public void addExcelComment(int row, int column, String comment) {
        if (excelCommentMap.containsKey(row)) {
            List<CommentMark> excelCommentMarks = excelCommentMap.get(row);
            if (excelCommentMarks.stream().noneMatch(item -> column == item.getColumn())) {
                excelCommentMarks.add(new CommentMark(row, column, comment));
            } else {
                excelCommentMarks.stream().filter(item -> column == item.getColumn()).findFirst()
                        .ifPresent(commentMark -> commentMark.setComment(commentMark.getComment() + "\n" + comment));
            }
        } else {
            List<CommentMark> excelCommentMarks = new ArrayList<>();
            excelCommentMarks.add(new CommentMark(row, column, comment));
            excelCommentMap.put(row, excelCommentMarks);
        }
    }

    /**
     * 是否备注
     *
     * @return bool
     */
    public boolean marked() {
        return !excelCommentMap.isEmpty();
    }

}
