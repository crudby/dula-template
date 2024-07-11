package com.crud.dula.common.excel;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;
import java.util.Map;

/**
 * @author changl@tsingyun.net
 * @date 2024/7/11 13:27
 */
public class SelectSheetWriteHandler implements SheetWriteHandler {

    private final Map<Integer, List<String>> selectMap;

    private final int index;

    private final char[] alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public SelectSheetWriteHandler(Map<Integer, List<String>> selectMap) {
        this.selectMap = selectMap;
        this.index = 1;
    }

    public SelectSheetWriteHandler(int index, Map<Integer, List<String>> selectMap) {
        this.index = index;
        this.selectMap = selectMap;
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        if (selectMap == null || selectMap.isEmpty()) {
            return;
        }
        // 需要设置下拉框的sheet页
        Sheet curSheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = curSheet.getDataValidationHelper();
        String dictSheetName = "字典sheet";

        Workbook workbook = writeWorkbookHolder.getWorkbook();

        // 数据字典的sheet页
        Sheet dictSheet = workbook.createSheet(dictSheetName);
        // 从下个工作簿开始隐藏，为了用户的友好性，将字典sheet隐藏掉
        // 设置隐藏
        workbook.setSheetHidden(this.index, true);
        for (Map.Entry<Integer, List<String>> entry : selectMap.entrySet()) {
            // 设置下拉单元格的首行、末行、首列、末列
            CellRangeAddressList rangeAddressList = new CellRangeAddressList(1, 65533, entry.getKey(), entry.getKey());
            int rowLen = entry.getValue().size();
            // 设置字典sheet页的值 每一列一个字典项
            for (int i = 0; i < rowLen; i++) {
                Row row = dictSheet.getRow(i);
                if (row == null) {
                    row = dictSheet.createRow(i);
                }
                row.createCell(entry.getKey()).setCellValue(entry.getValue().get(i));
            }
            String excelColumn = getExcelColumn(entry.getKey());
            // 下拉框数据来源 eg:字典sheet!$B1:$B2
            String refers = dictSheetName + "!$" + excelColumn + "$1:$" + excelColumn + "$" + rowLen;
            // 创建可被其他单元格引用的名称
            Name name = workbook.createName();
            // 设置名称的名字
            name.setNameName("dict" + entry.getKey());
            // 设置公式
            name.setRefersToFormula(refers);
            // 设置引用约束
            DataValidationConstraint constraint = helper.createFormulaListConstraint("dict" + entry.getKey());
            // 设置约束
            DataValidation validation = helper.createValidation(constraint, rangeAddressList);
            if (validation instanceof HSSFDataValidation) {
                validation.setSuppressDropDownArrow(false);
            } else {
                validation.setSuppressDropDownArrow(true);
                validation.setShowErrorBox(true);
            }
            // 阻止输入非下拉框的值
            validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            validation.createErrorBox("提示", "此值与单元格定义格式不一致！");
            // 添加下拉框约束
            writeSheetHolder.getSheet().addValidationData(validation);
        }

    }

    /**
     * 将数字列转化成为字母列
     *
     * @param num 数字列
     * @return 字母列
     */
    private String getExcelColumn(int num) {
        String column = "";
        int len = alphabet.length - 1;
        int first = num / len;
        int second = num % len;
        if (num <= len) {
            column = alphabet[num] + "";
        } else {
            column = alphabet[first - 1] + "";
            if (second == 0) {
                column = column + alphabet[len];
            } else {
                column = column + alphabet[second - 1];
            }
        }
        return column;

    }

}
