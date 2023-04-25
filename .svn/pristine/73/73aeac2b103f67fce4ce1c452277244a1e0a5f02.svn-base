package com.sunmnet.mediaroom.brand.control.table;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.format.IFormat;
import com.bin.david.form.data.format.draw.IDrawFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.data.table.ArrayTableData;
import com.bin.david.form.utils.DrawUtils;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.bean.course.DayCourseSchedule;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.brand.bean.TemplateCourseTableCell;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.bean.control.table.CourseTableControlProperty;

import java.util.Date;
import java.util.List;

public class TemplateCourseTableControl extends BaseFrameControl<CourseTableControlProperty> {

    static String[] titleName = new String[]{"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    protected SmartTable<TemplateCourseTableCell> table;

    protected TemplateCourseTableCell[][] tableCells;
    protected int rows;//需根据课程表确定一共几行
    protected int columns = 8;//一周7天，加上课节列，一共8列

    private TableDataListener mTableDataListener;

    public void setTableDataListener(TableDataListener tableDataListener) {
        this.mTableDataListener = tableDataListener;
    }

    boolean measured = false;

    private int width;
    private int height;

    public TemplateCourseTableControl(Context context) {
        super(context);
    }


    public TemplateCourseTableControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public TemplateCourseTableControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        table = new SmartTable<>(getContext());
        table.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        table.getConfig()
                .setShowColumnTitle(true)
                .setShowTableTitle(false)
                .setShowXSequence(false)
                .setShowYSequence(false)
                .setHorizontalPadding(0)
                .setVerticalPadding(0)
                .setContentStyle(new FontStyle())
                .setContentGridStyle(new LineStyle().setColor(0xFF91C9FF).setWidth(2))
                .setColumnTitleStyle(new FontStyle(){
                    @Override
                    public void fillPaint(Paint paint) {
                        super.fillPaint(paint);
                        paint.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                }.setTextColor(0xFF3EF2C3).setTextSize(20))
                .setColumnTitleGridStyle(new LineStyle().setColor(0xFF91C9FF).setWidth(2))
                .setFixedTitle(true);
        removeAllViews();
        addView(table);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!measured) {
            measured = true;
            width = right - left;
            height = bottom - top;
            refreshControlData();
        }
    }

    @Override
    public void refreshControlData() {
        super.refreshControlData();

        if (!CourseHelper.getDefault().isLoaded()) {
            return;
        }
        rows = CourseHelper.getDefault().getCourseTimeArray().size();
        tableCells = new TemplateCourseTableCell[rows][columns];

        int perWidth = width / columns;

        Paint paint = table.getConfig().getPaint();
        table.getConfig().getColumnTitleStyle().fillPaint(paint);
        int titleHeight = DrawUtils.getTextHeight(table.getConfig().getColumnTitleStyle(), paint);
        int minHeight = ((height - titleHeight) / rows) + 1;

        int widthRemainder = width % columns;

        for (int i = 0; i < tableCells.length; i++) {
            for (int j = 0; j < tableCells[i].length; j++) {
                if (tableCells[i][j] == null) {
                    TemplateCourseTableCell tableCell = new TemplateCourseTableCell();
                    if (widthRemainder > 0) {
                        tableCell.setWidth(perWidth + 1);
                        widthRemainder--;
                    } else {
                        tableCell.setWidth(perWidth);
                    }
                    tableCell.setTextSize(16);
                    tableCells[i][j] = tableCell;
                }
            }
        }

        try {
            onCreateTableData(tableCells);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //其实传入的二维数组数据只支持arr[col][row]，故需要对arr[row][col]进行转换
        ArrayTableData<TemplateCourseTableCell> tableData = createTableData("", ArrayTableData.transformColumnArray(tableCells), new TemplateCourseTableCellTextFormat());
        tableData.setMinHeight(minHeight);
        if (mTableDataListener != null) {
            mTableDataListener.afterTableDataCreated(tableData);
        }
        table.setTableData(tableData);

    }

    protected void onCreateTableData(TemplateCourseTableCell[][] tableCells) throws Exception {
        if (mTableDataListener != null && mTableDataListener.onCreateTableData(tableCells))
            return;
        if (CourseHelper.getDefault().isLoaded()) {
            List<DayCourseSchedule> daySchedules = CourseHelper.getDefault().getWeekSchedule(new Date());

            try {
                for (int x = 0; x < tableCells.length; x++) {//课节数据
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("第").append(x + 1).append("节");
                    CourseTime courseTime = CourseHelper.getDefault().getCourseTimeArray().get(x + 1);
                    if (courseTime != null) {
                        buffer.append("\n").append(CourseHelper.getDefault().getCourseTimeArray().get(x + 1).getStart());
                        buffer.append("-");
                        buffer.append(CourseHelper.getDefault().getCourseTimeArray().get(x + 1).getEnd());
                    }
                    tableCells[x][0].setText(buffer.toString());
                    tableCells[x][0].setBold(true);
                    tableCells[x][0].setTextColor("#3EF2C3");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (int x = 0; x < tableCells.length; x++) {//课程信息,同行同课节，同列同一天
                    for (int y = 1; y < tableCells[x].length; y++) {
                        StringBuilder builder = new StringBuilder();
                        DayCourseSchedule daySchedule = null;
                        if (daySchedules != null && y <= daySchedules.size())
                            daySchedule = daySchedules.get(y - 1);
                        CourseSchedule courseSchedule = null;
                        if (daySchedule != null) {
                            courseSchedule = daySchedule.getCourse(x + 1);
                        }
                        if (courseSchedule != null) {
                            if (!TextUtils.isEmpty(courseSchedule.getCourseName())) {
                                builder.append(courseSchedule.getCourseName());
                            } else {
                                builder.append("空闲");
                            }
                            if (!TextUtils.isEmpty(courseSchedule.getTeacherName())) {
                                builder.append("\n").append(courseSchedule.getTeacherName());
                            }
                            if (!TextUtils.isEmpty(courseSchedule.getGradeName())) {
                                builder.append("\n").append(courseSchedule.getGradeName());
                            }
                        } else {
                            builder.append("无");
                        }
                        tableCells[x][y].setText(builder.toString());
                        tableCells[x][y].setTextColor("#FFFFFF");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                for (int x = 0; x < tableCells.length; x++) {//课节数据
                    int classNo = x + 1;
                    tableCells[x][0].setText("第" + classNo + "节");
                    tableCells[x][0].setBold(true);
                    tableCells[x][0].setTextColor("#3EF2C3");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int x = 0; x < tableCells.length; x++) {//课程信息,行为课节，列为星期
                    for (int y = 1; y < tableCells[x].length; y++) {
                        tableCells[x][y].setText("空闲");
                        tableCells[x][y].setTextColor("#FFFFFF");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    protected ArrayTableData<TemplateCourseTableCell> createTableData(String tableName, TemplateCourseTableCell[][] data, IDrawFormat<TemplateCourseTableCell> drawFormat) {
        ArrayTableData<TemplateCourseTableCell> tableData = ArrayTableData.create(tableName, titleName, data, drawFormat);
        tableData.setFormat(new IFormat<TemplateCourseTableCell>() {
            @Override
            public String format(TemplateCourseTableCell tableCell) {
                return tableCell.getText() == null ? "" : tableCell.getText();
            }
        });
        tableData.setMinHeight(60);
        tableData.getArrayColumns().get(0).setFixed(true);
        return tableData;
    }

    public SmartTable<TemplateCourseTableCell> getTable() {
        return table;
    }

    public interface TableDataListener {
        /**
         *
         * @param tableCells
         * @return 返回true时，不执行原代码
         */
        boolean onCreateTableData(TemplateCourseTableCell[][] tableCells);

        /**
         *
         * @param tableData
         * @return 返回true时，不执行原代码
         */
        void afterTableDataCreated(ArrayTableData<TemplateCourseTableCell> tableData);
    }
}
