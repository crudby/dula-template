package com.crud.dula.common.excel;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

import java.util.List;
import java.util.Map;

/**
 * 注释标记回写
 *
 * @author crud
 * @date 2023/3/17 13:23
 */
public class CommentWriteHandler implements RowWriteHandler {

    private final Map<Integer, List<CommentMark>> excelErrorMap;

    public CommentWriteHandler(Map<Integer, List<CommentMark>> excelErrorMap) {
        this.excelErrorMap = excelErrorMap;
    }

    private void setCellCommon(Sheet sheet, int rowIndex, int colIndex, String value) {
        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            return;
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        if (value == null) {
            cell.removeCellComment();
            return;
        }
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = sheet.getWorkbook().getCreationHelper();
        ClientAnchor anchor = factory.createClientAnchor();
        Row row1 = sheet.getRow(anchor.getRow1());
        if (row1 != null) {
            Cell cell1 = row1.getCell(anchor.getCol1());
            if (cell1 != null) {
                cell1.removeCellComment();
            }
        }
        Comment comment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(value);
        comment.setString(str);
        comment.setAuthor("CLPM");
        cell.setCellComment(comment);
        cell.setCellStyle(cellStyle);
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (!isHead) {
            Sheet sheet = writeSheetHolder.getSheet();
            int activeRowIndex = row.getRowNum();
            if (excelErrorMap.containsKey(activeRowIndex)) {
                List<CommentMark> excelErrors = excelErrorMap.get(activeRowIndex);
                excelErrors.forEach(obj -> setCellCommon(sheet, obj.getRow(), obj.getColumn(), obj.getComment()));
            }
        }
    }
}
