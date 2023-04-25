package com.sunmnet.mediaroom.brand.control.table;

import android.content.Context;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.brand.bean.TableCell;

/**
 * 周课程表控件，首行为星期，首列为课节
 */

public class WeekCourseTableControl2 extends WeekCourseTableControl {

    public WeekCourseTableControl2(Context context) {
        super(context);
    }

    public WeekCourseTableControl2(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public WeekCourseTableControl2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreateTableData(TableCell[][] tableCells) {
        try {
            tableCells[0][0].setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadCourseDataCol();
    }

}
