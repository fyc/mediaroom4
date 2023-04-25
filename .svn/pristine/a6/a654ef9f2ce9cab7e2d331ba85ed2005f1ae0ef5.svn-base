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
import com.sunmnet.mediaroom.brand.bean.TemplateCourseTableCell;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * 表格文本绘制
 */
public class TemplateCourseTableCellTextFormat extends TextDrawFormat<TemplateCourseTableCell> {

    @Override
    public int measureWidth(Column<TemplateCourseTableCell> column, int position, TableConfig config) {
        if(column.getDatas().get(position)!=null&&column.getDatas().get(position).getWidth()>0){
            return column.getDatas().get(position).getWidth();
        }
        return super.measureWidth(column, position, config);
    }

    @Override
    public int measureHeight(Column<TemplateCourseTableCell> column, int position, TableConfig config) {
        if (column.getDatas().get(position) != null && column.getDatas().get(position).getHeight() != 0) {
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
            return Math.max(column.getMinHeight(), textHeight * lineCount);
        }
        return Math.max(column.getMinHeight(), super.measureHeight(column, position, config));
    }

    @Override
    protected void drawText(Canvas c, String value, Rect rect, Paint paint) {
        DrawUtils.drawMultiText(c, paint, rect, getMultiTextString(paint, value, rect.right - rect.left,rect.bottom - rect.top));
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
    public void setTextPaint(TableConfig config, CellInfo<TemplateCourseTableCell> cellInfo, Paint paint) {
        super.setTextPaint(config, cellInfo, paint);
        if (cellInfo.data != null) {
            int fontSize = getFontSize(cellInfo.data);
            config.getPaint().setTextSize(fontSize * config.getZoom());
            paint.setColor(getTextColor(cellInfo.data));
            setTypeface(paint, cellInfo.data);
        }
    }

    protected  int getFontSize(TemplateCourseTableCell tableCell){
        try {
            return tableCell.getTextSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 14;
    }

    protected  int getTextColor(TemplateCourseTableCell tableCell){
        try {
            return ColorUtil.parseColor(tableCell.getTextColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Color.BLACK;
    }

    protected  void setTypeface(Paint paint, TemplateCourseTableCell tableCell){
        int textStyle = 0;
        try {
            if (tableCell.isBold()) {
                textStyle = textStyle | Typeface.BOLD;
            }
            if (tableCell.isItalic()) {
                textStyle = textStyle | Typeface.ITALIC;
            }
            if (tableCell.isUnderline()) {
                paint.setFlags(Paint.UNDERLINE_TEXT_FLAG);
            } else {
                paint.setFlags(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Typeface typeface = ControlTextHelper.getFontTypeface(DeviceApp.getApp().getApplicationContext(), null, textStyle);
        if (typeface.getStyle() == textStyle)
            paint.setTypeface(typeface);
        else
            paint.setTypeface(Typeface.create(typeface, textStyle));
    }

}
