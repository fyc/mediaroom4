package com.sunmnet.mediaroom.brand.control.table;


import android.content.Context;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.bean.control.table.TableControlProperty;

import java.util.List;

public class TableControl extends BaseTableControl<TableControlProperty> {

    public TableControl(Context context) {
        super(context);
    }


    public TableControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public TableControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreateTableData(TableCell[][] tableCells) throws Exception {
        for (TableCell[] tableCellArr : tableCells) {
            for (TableCell cell : tableCellArr) {
                cell.setText(cell.getContent());
            }
        }
    }
}
