package com.sunmnet.mediaroom.brand.table;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.utils.DrawUtils;
import com.sunmnet.mediaroom.brand.bean.item.ClassroomStatusCell;

import java.util.List;

/**
 * 教室查询状态条绘制格式
 */
public class ClassroomQueryDrawFormat implements IDrawFormat<ClassroomStatusCell> {

    private Rect tempRect;

    public ClassroomQueryDrawFormat() {
        tempRect = new Rect();
    }

    @Override
    public int measureWidth(Column<ClassroomStatusCell> column, int position, TableConfig config) {
        String content = column.format(position);
        if (TextUtils.isEmpty(content)) {
            return config.getMinTableWidth();
        } else {
            Paint paint = config.getPaint();
            config.getContentStyle().fillPaint(paint);
            return DrawUtils.getMultiTextWidth(paint, new String[]{content}) * 2;
        }
    }

    @Override
    public int measureHeight(Column<ClassroomStatusCell> column, int position, TableConfig config) {
        return Math.max(column.getMinHeight(), column.getDatas().get(position).getHeight());
    }

    @Override
    public void draw(Canvas c, Rect rect, CellInfo<ClassroomStatusCell> cellInfo, TableConfig config) {
        Paint paint = config.getPaint();
        if (TextUtils.isEmpty(cellInfo.value)) {
            paint.setStyle(Paint.Style.FILL);
            List<ClassroomStatusCell.DrawStatus> drawStatusList = cellInfo.data.getDrawStatusList();
            if (drawStatusList != null) {
                for (ClassroomStatusCell.DrawStatus drawStatus : drawStatusList) {
                    resetRectVertical(rect, 20);

                    int startOffset = (int) (rect.width() * drawStatus.getStartPercent());
                    int endOffset = (int) (rect.width() * drawStatus.getEndPercent());
                    if (startOffset == 0) {
                        startOffset -= config.getContentGridStyle().getWidth();
                    }
                    tempRect.left = rect.left + startOffset;
                    tempRect.right = rect.left + endOffset;
                    paint.setColor(drawStatus.getColor());
                    c.drawRect(tempRect, paint);
                }
            }
        } else {
            setTextPaint(config, cellInfo, paint);
            if (cellInfo.column.getTextAlign() != null) {
                paint.setTextAlign(cellInfo.column.getTextAlign());
            }
            DrawUtils.drawSingleText(c, paint, rect, cellInfo.value);
        }
    }

    private void setTextPaint(TableConfig config, CellInfo<ClassroomStatusCell> cellInfo, Paint paint) {
        config.getContentStyle().fillPaint(paint);
        ICellBackgroundFormat<CellInfo> backgroundFormat = config.getContentCellBackgroundFormat();
        if (backgroundFormat != null && backgroundFormat.getTextColor(cellInfo) != TableConfig.INVALID_COLOR) {
            paint.setColor(backgroundFormat.getTextColor(cellInfo));
        }
        paint.setTextSize(paint.getTextSize() * config.getZoom());

    }

    private void resetRectVertical(Rect srcRect, int verticalPadding) {
        tempRect.top = srcRect.top + verticalPadding;
        tempRect.bottom = srcRect.bottom - verticalPadding;
        if (tempRect.top > tempRect.bottom) {
            tempRect.top = tempRect.bottom = (tempRect.top - tempRect.bottom) / 2;
        }
    }

}
