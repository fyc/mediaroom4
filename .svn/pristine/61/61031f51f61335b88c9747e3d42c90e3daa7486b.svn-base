package com.sunmnet.mediaroom.brand.control.table;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.grid.SimpleGridFormat;
import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;

/**
 * 表格边框绘制
 */

public class TableCellBorderFormat extends SimpleGridFormat {

    Path path = new Path();
    protected int rows;
    protected int columns;

    public TableCellBorderFormat(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public void drawContentGrid(Canvas canvas, int col, int row, Rect rect, CellInfo cellInfo, Paint paint) {
        //super.drawContentGrid(canvas, col, row, rect, cellInfo, paint);

        try {
        TableCell tableCell = (TableCell) cellInfo.data;

        //顶部
        paint.setColor(getColor(tableCell.getBorderColor()));
        paint.setStrokeWidth(tableCell.getBorderSize());


        path.rewind();
        if (row == 0) {
            path.moveTo(rect.left, rect.top + (float) tableCell.getBorderSize() / 2 + 0.5f);
            path.lineTo(rect.right, rect.top + (float) tableCell.getBorderSize() / 2 + 0.5f);
            canvas.drawPath(path, paint);
        } else {
            path.moveTo(rect.left, rect.top);
            path.lineTo(rect.right, rect.top);
            canvas.drawPath(path, paint);
        }

        //左边
        paint.setColor(getColor(tableCell.getBorderColor()));
        paint.setStrokeWidth(tableCell.getBorderSize());

        path.rewind();
        if (col == 0) {
            path.moveTo(rect.left + (float) tableCell.getBorderSize() / 2 + 0.5f, rect.top);
            path.lineTo(rect.left + (float) tableCell.getBorderSize() / 2 + 0.5f, rect.bottom);
            canvas.drawPath(path, paint);
        } else {
            path.moveTo(rect.left, rect.top);
            path.lineTo(rect.left, rect.bottom);
            canvas.drawPath(path, paint);
        }

        //底部
        paint.setColor(getColor(tableCell.getBorderColor()));
        paint.setStrokeWidth(tableCell.getBorderSize());

        path.rewind();
        if (row == rows - 1) {
            path.moveTo(rect.left, rect.bottom - (float) tableCell.getBorderSize() / 2 - 0.5f);
            path.lineTo(rect.right, rect.bottom - (float) tableCell.getBorderSize() / 2 - 0.5f);
            canvas.drawPath(path, paint);
        } else {
            path.moveTo(rect.left, rect.bottom);
            path.lineTo(rect.right, rect.bottom);
            canvas.drawPath(path, paint);
        }


        //右边
        paint.setColor(getColor(tableCell.getBorderColor()));
        paint.setStrokeWidth(tableCell.getBorderSize());

        path.rewind();
        if (col == columns - 1) {
            path.moveTo(rect.right - (float) tableCell.getBorderSize() / 2 - 0.5f, rect.top);
            path.lineTo(rect.right - (float) tableCell.getBorderSize() / 2 - 0.5f, rect.bottom);
            canvas.drawPath(path, paint);
        } else {
            path.moveTo(rect.right, rect.top);
            path.lineTo(rect.right, rect.bottom);
            canvas.drawPath(path, paint);
        }
        } catch (Exception e) {
            e.printStackTrace();
            super.drawContentGrid(canvas, col, row, rect, cellInfo, paint);
        }
    }

    private int getColor(String color) {
        try {
            return ColorUtil.parseColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
