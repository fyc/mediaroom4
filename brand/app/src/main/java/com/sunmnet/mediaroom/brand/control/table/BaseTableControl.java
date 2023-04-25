package com.sunmnet.mediaroom.brand.control.table;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.bean.control.table.BaseTableControlProperty;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;

import java.util.List;

public class BaseTableControl<P extends BaseTableControlProperty> extends BaseFrameControl<P> {

    protected SmartTable<TableCell> table;

    protected TableCell[][] tableCells;
    protected int rows;
    protected int columns;

    public BaseTableControl(Context context) {
        super(context);
    }


    public BaseTableControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public BaseTableControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        table = new SmartTable<>(getContext());
        table.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        table.getConfig().setShowColumnTitle(false);
        table.getConfig().setShowTableTitle(false);
        table.getConfig().setShowXSequence(false);
        table.getConfig().setShowYSequence(false);
        table.getConfig().setHorizontalPadding(0);
        table.getConfig().setVerticalPadding(0);
        table.getConfig().getPaint().setAntiAlias(true);

        removeAllViews();
        addView(table);
    }

    @Override
    public void refreshControlData() {
        super.refreshControlData();
        tableCells = new TableCell[rows][columns];
        List<List<TableCell>> tableLL = property.getAttr().getCells();
        int perWidth = getControlWidth() / columns;
        int perHeight = getControlHeight() / rows;

        int widthRemainder = getControlWidth() % columns;
        int heightRemainder = getControlHeight() % rows;

        //如果是整除，则每行每列空出一点位置，避免出现边框被遮盖的情况
//        if (getControlWidth() % columns == 0)
//            perWidth = perWidth - 1;
//        if (getControlHeight() % rows == 0)
//            perHeight = perHeight - 1;

        try {
            for (int i = 0; i < tableLL.size(); i++) {
                List<TableCell> dl = tableLL.get(i);
                for (int j = 0; j < dl.size(); j++) {
                    TableCell tableCell = dl.get(j);
                    if (widthRemainder > 0) {
                        tableCell.setWidth(perWidth + 1);
                        widthRemainder--;
                    } else {
                        tableCell.setWidth(perWidth);
                    }

                    if (heightRemainder > 0) {
                        tableCell.setHeight(perHeight + 1);
                        heightRemainder--;
                    } else {
                        tableCell.setHeight(perHeight);
                    }
                    tableCell.setRow(i);
                    tableCell.setColumn(j);
                    tableCells[tableCell.getRow()][tableCell.getColumn()] = tableCell;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tableCells.length; i++) {
            for (int j = 0; j < tableCells[i].length; j++) {
                if (tableCells[i][j] == null) {
                    TableCell tableCell = new TableCell(i, j);
                    tableCells[i][j] = tableCell;
                }
            }
        }

        table.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                try {
                    if (!TextUtils.isEmpty(((TableCell) cellInfo.data).getHeightLightBackgroundColor())) {
                        return ColorUtil.parseColor(((TableCell) cellInfo.data).getHeightLightBackgroundColor());
                    }
                    return ColorUtil.parseColor(((TableCell) cellInfo.data).getFillColor());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        try {
            onCreateTableData(tableCells);
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.getConfig().setTableGridFormat(new TableCellBorderFormat(rows, columns));
        table.getConfig().setSequenceGridStyle(new LineStyle().setColor(0x00000000));
        table.getConfig().setContentGridStyle(new LineStyle().setColor(0x00000000));
        table.getConfig().setColumnTitleGridStyle(new LineStyle().setColor(0x00000000));

        //其实传入的二维数组数据只支持arr[col][row]，故需要对arr[row][col]进行转换
        ArrayTableData<TableCell> tableData = createTableData(table, "", ArrayTableData.transformColumnArray(tableCells), new TableCellTextFormat());
        table.setTableData(tableData);
    }

    protected void onCreateTableData(TableCell[][] tableCells) throws Exception {

    }

    protected ArrayTableData<TableCell> createTableData(SmartTable table, String tableName, TableCell[][] data, IDrawFormat<TableCell> drawFormat) {
        ArrayTableData<TableCell> tableData = ArrayTableData.create(table, tableName, data, drawFormat);
        tableData.setFormat(new IFormat<TableCell>() {
            @Override
            public String format(TableCell tableCell) {
                return tableCell.getText() == null ? "" : tableCell.getText();
            }
        });
        return tableData;
    }

    @Override
    public void setProperty(P property) {
        super.setProperty(property);
        List<List<TableCell>> tableLL = property.getAttr().getCells();
        try {
            rows = tableLL.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            columns = tableLL.get(0).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
