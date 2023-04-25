package com.sunmnet.mediaroom.brand.control.table;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.TextDrawFormat;
import com.bin.david.form.utils.DrawUtils;
import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 表格文本绘制
 */
public class TableCellTextFormat extends TextDrawFormat<TableCell> {

    @Override
    public int measureWidth(Column<TableCell> column, int position, TableConfig config) {
        if(column.getDatas().get(position)!=null&&column.getDatas().get(position).getWidth()>0){
            return column.getDatas().get(position).getWidth();
        }
        return super.measureWidth(column, position, config);
    }

    @Override
    public int measureHeight(Column<TableCell> column, int position, TableConfig config) {
        if (column.getDatas().get(position) != null && column.getDatas().get(position).getHeight() > 0) {
            return column.getDatas().get(position).getHeight();
        }

        Paint paint = config.getPaint();
        config.getContentStyle().fillPaint(paint);
        int fontSize = getFontSize(column.getDatas().get(position));
        config.getPaint().setTextSize(fontSize * config.getZoom());
        setTypeface(paint, column.getDatas().get(position));

        String[] strArr = getSplitString(column.format(position));

        int textWidth = DrawUtils.getMultiTextWidth(paint, strArr);
        int cellWidth = measureWidth(column, position, config);

        if (cellWidth > 0 && textWidth > cellWidth) {//需要计算换行
            int textHeight = DrawUtils.getTextHeight(paint);
            int lengthPerRow = cellWidth / textHeight;
            int lineCount = 0;
            for (String aStrArr : strArr) {
                if (aStrArr.length() <= lengthPerRow) {
                    lineCount++;
                } else {
                    int row = (int) Math.ceil(aStrArr.length() / (float) lengthPerRow);
                    lineCount += row;
                }
            }
            return textHeight * lineCount;
        }
        return super.measureHeight(column, position, config);
    }

    private String[] getMultiTextString(Paint paint, String string, int rectWidth) {
        String[] values = getSplitString(string);
        int textWidth = DrawUtils.getMultiTextWidth(paint, values);
        if (textWidth > rectWidth) {//需要计算换行
            int textHeight = DrawUtils.getTextHeight(paint);
            int lengthPerRow = rectWidth / textHeight;
            List<String> list = new LinkedList<>();
            for (String value : values) {
                if (value.length() <= lengthPerRow) {
                    list.add(value);
                } else {
                    int row = (int) Math.ceil(value.length() / (float) lengthPerRow);
                    for (int j = 0; j < row; j++) {
                        int start = j * lengthPerRow;
                        int end = (j + 1) * lengthPerRow;
                        if (end > value.length()) {
                            end = value.length();
                        }
                        list.add(value.substring(start, end));
                    }
                }
            }
            values = new String[list.size()];
            values = list.toArray(values);
        }
        return values;
    }

    //先计算自动换行，再计算内容是否超出绘制范围，超出则缩小字体大小
    private String[] getMultiTextString(Paint paint, String string, int rectWidth, int rectHeight) {
        String[] values = getSplitString(string);
        int textWidth = DrawUtils.getMultiTextWidth(paint, values);
        if (textWidth > rectWidth) {//需要计算换行
            int textHeight = DrawUtils.getTextHeight(paint);
            int lengthPerRow = rectWidth / textHeight;
            List<String> list = new LinkedList<>();
            for (String value : values) {
                if (value.length() <= lengthPerRow) {
                    list.add(value);
                } else {
                    int row = (int) Math.ceil(value.length() / (float) lengthPerRow);
                    for (int j = 0; j < row; j++) {
                        int start = j * lengthPerRow;
                        int end = (j + 1) * lengthPerRow;
                        if (end > value.length()) {
                            end = value.length();
                        }
                        list.add(value.substring(start, end));
                    }
                }
            }
            int totalHeight = list.size() * textHeight;
            if (totalHeight > rectHeight) {
                if (paint.getTextSize() > 12) {
                    paint.setTextSize(paint.getTextSize() - 1);
                    //递归获取减小字体大小后的字符串数组
                    values = getMultiTextString(paint, string, rectWidth, rectHeight);
                } else {
                    boolean flag = false;
                    //截取能显示的行数
                    for (int i = 1; i < list.size(); i++) {
                        int height = (list.size() - i) * textHeight;
                        if (height <= rectHeight) {
                            values = new String[list.size() - i];
                            values = list.subList(0, list.size() - i).toArray(values);
                            flag = true;
                            break;
                        }
                    }
                    if (values.length > 0) {
                        if (flag) {
                            String s = values[values.length - 1];
                            if (s != null) {
                                for (int i = 0; i < s.length(); i++) {
                                    if (rectWidth >= (textHeight * (s.length() - i + 3))) {
                                        values[values.length - 1] = s.substring(0, s.length() - i) + "...";
                                        break;
                                    }
                                }
                            }
                        } else {
                            //连一行都装不下
                            values = new String[1];
                            values[0] = "...";
                        }
                    }
                }
            } else {
                values = new String[list.size()];
                values = list.toArray(values);
            }
        }
        return values;
    }

    @Override
    public void draw(Canvas c, Rect rect, CellInfo<TableCell> cellInfo, TableConfig config) {
        try {
            rect.top = rect.top + cellInfo.data.getBorderSize();
            rect.bottom = rect.bottom - cellInfo.data.getBorderSize();
            rect.left = rect.left + cellInfo.data.getBorderSize();
            rect.right = rect.right - cellInfo.data.getBorderSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.draw(c, rect, cellInfo, config);
    }

    @Override
    protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
//        super.drawText(c, value, rect, paint);
        DrawUtils.drawMultiText(c, paint, rect, getMultiTextString(paint, value, rect.right - rect.left,rect.bottom - rect.top));
    }

    @Override
    public void setTextPaint(TableConfig config, CellInfo<TableCell> cellInfo, Paint paint) {
        super.setTextPaint(config, cellInfo, paint);
        if (cellInfo.data != null) {
            config.getPaint().setTextAlign(getAlign(cellInfo.data));
            int fontSize = getFontSize(cellInfo.data);
            config.getPaint().setTextSize(fontSize * config.getZoom());
            paint.setColor(getTextColor(cellInfo.data));
            setTypeface(paint, cellInfo.data);
        }
    }

    protected Paint.Align getAlign(TableCell tableCell) {
        //单元格的文字对齐
        try {
            String alignment = tableCell.getTextStyle().getTextAlign();
            if (ITextStyle.ALIGN_LEFT.equals(alignment)) {
                return Paint.Align.LEFT;
            } else if (ITextStyle.ALIGN_RIGHT.equals(alignment)) {
                return Paint.Align.RIGHT;
            } else if (ITextStyle.ALIGN_LEFT.equals(alignment)) {
                return Paint.Align.CENTER;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Paint.Align.CENTER;
    }

    protected  int getFontSize(TableCell tableCell){
        try {
            return tableCell.getTextStyle().getFontSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 14;
    }

    protected  int getTextColor(TableCell tableCell){
        try {
            return ColorUtil.parseColor(tableCell.getTextStyle().getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Color.BLACK;
    }

    protected  void setTypeface(Paint paint, TableCell tableCell){
        int textStyle = 0;
        try {
            if (tableCell.getTextStyle().isBold()) {
                textStyle = textStyle | Typeface.BOLD;
            }
            if (tableCell.getTextStyle().isItalic()) {
                textStyle = textStyle | Typeface.ITALIC;
            }
            if (tableCell.getTextStyle().isUnderline()) {
                paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            } else {
                paint.setFlags(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Typeface typeface = ControlTextHelper.getFontTypeface(DeviceApp.getApp().getApplicationContext(), tableCell.getTextStyle().getFontFamily(), textStyle);
        if (typeface.getStyle() == textStyle)
            paint.setTypeface(typeface);
        else
            paint.setTypeface(Typeface.create(typeface, textStyle));
    }
}
