package com.sunmnet.mediaroom.brand.control.table;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.bean.course.DayCourseSchedule;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.enums.CourseListType;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.bean.control.table.WeekCourseTableControlProperty;

import java.util.Date;
import java.util.List;

/**
 * 周课程表控件，课节、星期排列方式可变，星期数可变，课节数可变，可选单双课节
 */
public class WeekCourseTableControl extends CourseTableControl<WeekCourseTableControlProperty> {

//    protected final static Map<Integer, String> weekMap = new HashMap<>();
    protected final static SparseArray<String> weekMap = new SparseArray<>();

    //课程表显示的星期数，最小值5显示周一到周五 最大值7显示周一到周日
    protected int weekNum = 5;

    static {
        weekMap.put(1, "一");
        weekMap.put(2, "二");
        weekMap.put(3, "三");
        weekMap.put(4, "四");
        weekMap.put(5, "五");
        weekMap.put(6, "六");
        weekMap.put(7, "日");
    }


    public WeekCourseTableControl(Context context) {
        super(context);
    }

    public WeekCourseTableControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public WeekCourseTableControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        courseStyle = TypeUtil.objToStrDef(attrDataMap.get("courseStyle"), CourseTableStyle.SINGLE.getValue());
//        weekNum = TypeUtil.objToIntDef(attrDataMap.get("weekNum"), 5);
//    }

    @Override
    public void setProperty(WeekCourseTableControlProperty property) {
        super.setProperty(property);
        direction = TypeUtil.objToStrDef(property.getAttr().getDirection(), CourseTableDirection.COURSE_HEADER_COL.getValue());
        weekNum = TypeUtil.objToIntDef(property.getAttr().getWeekNum(), 5);
    }

    @Override
    protected void onCreateTableData(TableCell[][] tableCells) {
        try {
            tableCells[0][0].setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreateTableData(tableCells);
    }


    @Override
    protected void loadCourseDataCol() {
        boolean isDouble = CourseTableStyle.DOUBLE.getValue().equalsIgnoreCase(courseStyle);
        if (CourseHelper.getDefault().isLoaded()) {
            List<DayCourseSchedule> daySchedules = CourseHelper.getDefault().getWeekSchedule(new Date());
            try {
                for (int y = 1; y < tableCells[0].length; y++) {//星期几
                    tableCells[0][y].setText("星期" + weekMap.get(y));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Date nowDate = new Date();
            //当前课节的上下课时间 nullable
            CourseTime courseTimeNow = CourseHelper.getDefault().getCourseTime(nowDate);
            //下个课节的上下课时间 nullable
            CourseTime nextCourseTime = CourseHelper.getDefault().getNextCourseTime(nowDate);
            //下节课开始时间
            Date nextCourseStartDate = null;
            if(nextCourseTime != null)
                nextCourseStartDate = nextCourseTime.getStartTime(DateUtil.formatDate(nowDate, DateUtil.DEFAULT_FORMAT_DATE));
            //当前时间，加上预备上课时间差
            Date nowDateEx = new Date(nowDate.getTime() + CourseConfig.PRE_START_TIME);

            try {
                if (isDouble) {
                    for (int x = 1; x < tableCells.length; x++) {//课节数据
                        StringBuilder builder = new StringBuilder();
                        builder.append("第").append(x * 2 - 1).append("、");
                        builder.append(x * 2).append("节");
                        CourseTime courseTime = CourseHelper.getDefault().getCourseTimeArray().get(x * 2 - 1);
                        CourseTime courseTime2 = CourseHelper.getDefault().getCourseTimeArray().get(x * 2);
                        if (courseTime != null && courseTime2 != null) {
                            builder.append("\n").append(courseTime.getStart());
                            builder.append("-");
                            builder.append(courseTime2.getEnd());
                        }
                        tableCells[x][0].setText(builder.toString());
                    }
                } else {
                    for (int x = 1; x < tableCells.length; x++) {//课节数据
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("第").append(x).append("节");
                        CourseTime courseTime = CourseHelper.getDefault().getCourseTimeArray().get(x);
                        if (courseTime != null) {
                            buffer.append("\n").append(CourseHelper.getDefault().getCourseTimeArray().get(x).getStart());
                            buffer.append("-");
                            buffer.append(CourseHelper.getDefault().getCourseTimeArray().get(x).getEnd());
                        }
                        tableCells[x][0].setText(buffer.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                SparseArray<List<CourseSchedule>> sparseArray = null;
                if (isDouble) {
                    sparseArray = new SparseArray<>();
                }
                for (int x = 1; x < tableCells.length; x++) {//课程信息,同行同课节，同列同一天
                    for (int y = 1; y < tableCells[x].length; y++) {
                        StringBuilder builder = new StringBuilder();
                        DayCourseSchedule daySchedule = null;
                        if (daySchedules != null && y <= daySchedules.size())
                            daySchedule = daySchedules.get(y - 1);
                        CourseSchedule courseSchedule = null;
                        if (daySchedule != null) {
                            if (isDouble) {
                                if (sparseArray.indexOfKey(y - 1) >= 0) {
                                    List<CourseSchedule> list = sparseArray.get(y - 1);
                                    if (list != null && list.size() >= x)
                                        courseSchedule = list.get(x - 1);
                                } else {
                                    List<CourseSchedule> list = daySchedule.getCourseSchedule(CourseListType.FREE_COMBINE_BYCOUNT);
                                    sparseArray.put(y - 1, list);
                                    if (list != null && list.size() >= x)
                                        courseSchedule = list.get(x - 1);
                                }
                            } else {
                                courseSchedule = daySchedule.getCourse(x);
                            }
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
                        tableCells[x][y].setHeightLightBackgroundColor(null);
                        if (courseSchedule != null){
                            if (DateUtil.isBetween(nowDate, courseSchedule.getStartTime(), courseSchedule.getEndTime())) {
                                //当前有课时高亮当前课节
                                tableCells[x][y].setHeightLightBackgroundColor("#33acf3");
                            } else if (courseTimeNow == null && nextCourseStartDate != null) {
                                //当前没课时，判断是否今天的下节课，且在预备上课时间内
                                if (DateUtil.isBetween(nextCourseStartDate, courseSchedule.getStartTime(), courseSchedule.getEndTime())
                                        && DateUtil.isAfter(nowDateEx, nextCourseStartDate)) {
                                    tableCells[x][y].setHeightLightBackgroundColor("#33acf3");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            try {
                if (isDouble) {
                    for (int x = 1; x < tableCells.length; x++) {//课节数据
                        tableCells[x][0].setText("第" + (x * 2 - 1) + "、" + (x * 2) + "节");
                    }
                } else {
                    for (int x = 1; x < tableCells.length; x++) {//课节数据
                        tableCells[x][0].setText("第" + x + "节");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int y = 1; y < tableCells[0].length; y++) {//星期几
                    tableCells[0][y].setText("星期" + weekMap.get(y));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int x = 1; x < tableCells.length; x++) {//课程信息,行为课节，列为星期
                    for (int y = 1; y < tableCells[x].length; y++) {
                        tableCells[x][y].setText("空闲");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void loadCourseDataRow() {
        boolean isDouble = CourseTableStyle.DOUBLE.getValue().equalsIgnoreCase(courseStyle);
        if (CourseHelper.getDefault().isLoaded()) {
            List<DayCourseSchedule> daySchedules = CourseHelper.getDefault().getWeekSchedule(new Date());
            try {
                if (isDouble) {
                    for (int y = 1; y < tableCells[0].length; y++) {//课节数据
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("第").append(y * 2 - 1).append("、");
                        buffer.append(y * 2).append("节");
                        CourseTime courseTime = CourseHelper.getDefault().getCourseTimeArray().get(y * 2 - 1);
                        CourseTime courseTime2 = CourseHelper.getDefault().getCourseTimeArray().get(y * 2);
                        if (courseTime != null && courseTime2 != null) {
                            buffer.append("\n").append(courseTime.getStart());
                            buffer.append("-");
                            buffer.append(courseTime2.getEnd());
                        }
                        tableCells[0][y].setText(buffer.toString());
                    }
                } else {
                    for (int y = 1; y < tableCells[0].length; y++) {//课节数据
                        StringBuffer buffer = new StringBuffer();
                        buffer.append("第").append(y).append("节");
                        CourseTime courseTime = CourseHelper.getDefault().getCourseTimeArray().get(y);
                        if (courseTime != null) {
                            buffer.append("\n").append(CourseHelper.getDefault().getCourseTimeArray().get(y).getStart());
                            buffer.append("-");
                            buffer.append(CourseHelper.getDefault().getCourseTimeArray().get(y).getEnd());
                        }
                        tableCells[0][y].setText(buffer.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (int x = 1; x < tableCells.length; x++) {//星期几
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("星期").append(weekMap.get(x));
                    tableCells[x][0].setText(buffer.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Date nowDate = new Date();
            //当前课节的上下课时间 nullable
            CourseTime courseTimeNow = CourseHelper.getDefault().getCourseTime(nowDate);
            //下个课节的上下课时间 nullable
            CourseTime nextCourseTime = CourseHelper.getDefault().getNextCourseTime(nowDate);
            //下节课开始时间
            Date nextCourseStartDate = null;
            if(nextCourseTime != null)
                nextCourseStartDate = nextCourseTime.getStartTime(DateUtil.formatDate(nowDate, DateUtil.DEFAULT_FORMAT_DATE));
            //当前时间，加上预备上课时间差
            Date nowDateEx = new Date(nowDate.getTime() + CourseConfig.PRE_START_TIME);

            try {
                for (int x = 1; x < tableCells.length; x++) {//课程信息,行为星期，列为课节
                    List<CourseSchedule> list = null;
                    DayCourseSchedule daySchedule = null;

                    if (daySchedules.size() >= x)
                        daySchedule = daySchedules.get(x - 1);

                    if (isDouble && daySchedule != null) {
                        list = daySchedule.getCourseSchedule(CourseListType.FREE_COMBINE_BYCOUNT);
                    }

                    for (int y = 1; y < tableCells[x].length; y++) {
                        StringBuffer buffer = new StringBuffer();
                        CourseSchedule courseSchedule = null;
                        if (daySchedule != null) {
                            if (isDouble) {
                                if (list != null && list.size() >= y)
                                    courseSchedule = list.get(y - 1);
                            } else {
                                courseSchedule = daySchedule.getCourse(y);
                            }
                        }
                        if (courseSchedule != null) {
                            if (!TextUtils.isEmpty(courseSchedule.getCourseName())) {
                                buffer.append(courseSchedule.getCourseName());
                            } else {
                                buffer.append("空闲");
                            }
                            if (!TextUtils.isEmpty(courseSchedule.getTeacherName())) {
                                buffer.append("\n").append(courseSchedule.getTeacherName());
                            }
                            if (!TextUtils.isEmpty(courseSchedule.getGradeName())) {
                                buffer.append("\n").append(courseSchedule.getGradeName());
                            }
                        } else {
                            buffer.append("无");
                        }
                        tableCells[x][y].setText(buffer.toString());
                        tableCells[x][y].setHeightLightBackgroundColor(null);
                        if (courseSchedule != null) {
                            if (courseTimeNow != null && DateUtil.isBetween(nowDate, courseSchedule.getStartTime(), courseSchedule.getEndTime())) {
                                //当前有课时高亮当前课节
                                tableCells[x][y].setHeightLightBackgroundColor("#33acf3");
                            } else if (courseTimeNow == null && nextCourseStartDate != null) {
                                //当前没课时，判断是否今天的下节课，且在预备上课时间内
                                if (DateUtil.isBetween(nextCourseStartDate, courseSchedule.getStartTime(), courseSchedule.getEndTime())
                                        && DateUtil.isAfter(nowDateEx, nextCourseStartDate)) {
                                    tableCells[x][y].setHeightLightBackgroundColor("#33acf3");
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (isDouble) {
                    for (int y = 1; y < tableCells[0].length; y++) {//课节时间数据
                        tableCells[0][y].setText("第" + (y * 2 - 1) + "、" + (y * 2) + "节");
                    }
                } else {
                    for (int y = 1; y < tableCells[0].length; y++) {//课节时间数据
                        tableCells[0][y].setText("第" + y + "节");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (int x = 1; x < tableCells.length; x++) {//星期几
                    tableCells[x][0].setText("星期" + weekMap.get(x));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                for (int x = 1; x < tableCells.length; x++) {//课程信息,行为星期，列为课节
                    for (int y = 1; y < tableCells[x].length; y++) {
                        tableCells[x][y].setText("空闲");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
