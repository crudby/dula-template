package com.crud.dula.common.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Objects;

/**
 * @author crud
 * @date 2023/3/30 14:48
 */
public class RowMergeWriterHandler extends AbstractMergeStrategy {

    private Integer unionIndex;

    private final List<Integer> mergeCols;

    public RowMergeWriterHandler(List<Integer> mergeCols) {
        this.mergeCols = mergeCols;
    }

    public RowMergeWriterHandler(Integer unionIndex, List<Integer> mergeCols) {
        this.unionIndex = unionIndex;
        this.mergeCols = mergeCols;
    }

    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        if (mergeCols.isEmpty() || !mergeCols.contains(cell.getColumnIndex())) {
            return;
        }
        int rowIndex = cell.getRowIndex(), colIndex = cell.getColumnIndex();
        Row preRow = sheet.getRow(rowIndex - 1);
        if (Objects.nonNull(unionIndex)) {
            Cell curUniCell = sheet.getRow(rowIndex).getCell(unionIndex);
            Cell preUniCell = preRow.getCell(unionIndex);
            if (!compareCell(preUniCell, curUniCell)) {
                return;
            }
        }
        Cell preCell = preRow.getCell(colIndex);
        this.mergeCell(sheet, preCell, cell);
    }

    private void mergeCell(Sheet sheet, Cell preCell, Cell curCell) {
        Object preCellValue = getCellValue(preCell), curCellValue = getCellValue(curCell);

        if (Objects.equals("", preCellValue)) {
            if (!preCellValue.equals(curCellValue)) {
                return;
            }
            curCell.setBlank();
            sheet.addMergedRegion(new CellRangeAddress(preCell.getRowIndex(), curCell.getRowIndex(), preCell.getColumnIndex(), curCell.getColumnIndex()));
            return;
        }

        List<CellRangeAddress> list = sheet.getMergedRegions();
        CellRangeAddress rangeAddress = list.stream().filter(e -> compareColAndRow(e, preCell)).findFirst().orElse(null);
        if (Objects.isNull(rangeAddress)) {
            if (Objects.equals("", curCellValue)) {
                curCell.setBlank();
                sheet.addMergedRegion(new CellRangeAddress(preCell.getRowIndex(), curCell.getRowIndex(), preCell.getColumnIndex(), curCell.getColumnIndex()));
                return;
            }
            return;
        }
        int firstRow = rangeAddress.getFirstRow(), firstColumn = rangeAddress.getFirstColumn();
        String value = String.valueOf(getCellValue(sheet.getRow(firstRow).getCell(firstColumn)));
        if (!value.equals(curCellValue)) {
            return;
        }
        int lastRow = curCell.getRowIndex(), lastColumn = curCell.getColumnIndex();
        for (int i = 0; i < list.size(); i++) {
            if (rangeAddress.equals(list.get(i))) {
                sheet.removeMergedRegion(i);
                curCell.setBlank();
                sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));
                return;
            }
        }
    }

    private boolean compareColAndRow(CellRangeAddress cellRangeAddress, Cell cell) {
        return cellRangeAddress.getFirstColumn() <= cell.getColumnIndex() && cellRangeAddress.getLastColumn() >= cell.getColumnIndex()
                && cellRangeAddress.getFirstRow() <= cell.getRowIndex() && cellRangeAddress.getLastRow() >= cell.getRowIndex();
    }

    private boolean compareCell(Cell c1, Cell c2) {
        return Objects.nonNull(c1) && Objects.nonNull(c2) && Objects.equals(getCellValue(c1), getCellValue(c2));
    }

    private Object getCellValue(Cell cell) {
        if (Objects.isNull(cell)) {
            return "";
        }
        CellType cellTypeEnum = cell.getCellType();
        switch (cellTypeEnum) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            default:
                return "";
        }
    }

}
