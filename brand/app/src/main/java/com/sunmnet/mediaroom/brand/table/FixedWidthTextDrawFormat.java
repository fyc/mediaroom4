package com.sunmnet.mediaroom.brand.table;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.draw.TextDrawFormat;

/**
 * 固定宽度文本格式
 */
public class FixedWidthTextDrawFormat<T> extends TextDrawFormat<T> {

    private int width;

    public FixedWidthTextDrawFormat() {
    }

    public FixedWidthTextDrawFormat(int width) {
        this.width = width;
    }

    @Override
    public int measureWidth(Column<T> column, int position, TableConfig config) {
        if (width > 0) {
            return width;
        }
        return super.measureWidth(column, position, config);
    }
}
