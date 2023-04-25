package com.sunmnet.mediaroom.brand.control.table;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.enums.CourseListType;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.brand.bean.TableCell;
import com.sunmnet.mediaroom.brand.bean.control.table.CourseTableControlProperty;

import java.util.Date;
import java.util.List;

/**
 * 日课程表控件
 */
public class DayCourseTableControl extends CourseTableControl<CourseTableControlProperty> {


    public DayCourseTableControl(Context context) {
        super(context);
    }

    public DayCourseTableControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public DayCourseTableControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onCreateTableData(TableCell[][] tableCells) {
        super.onCreateTableData(tableCells);
    }

    @Override
    protected void loadCourseDataCol() {
        boolean isSingle = CourseTableStyle.SINGLE.getValue().equals(courseStyle);
        try {
            tableCells[0][0].setText("课节");
            tableCells[0][1].setText("时间");
            tableCells[0][2].setText("课程名称");
            tableCells[0][3].setText("上课班级");
            tableCells[0][4].setText("任课老师");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CourseHelper.getDefault().isLoaded()) {
            List<CourseSchedule> scheduleList;
            if (isSingle) {
                scheduleList = CourseHelper.getDefault().getDayCourseByDate(new Date()).getCourseSchedule(CourseListType.FREE_LIST);
            } else {
                scheduleList = CourseHelper.getDefault().getDayCourseByDate(new Date()).getCourseSchedule(CourseListType.FREE_COMBINE_BYCOUNT);
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
                for (int x = 1; x < tableCells.length; x++) {
                    CourseSchedule courseSchedule = null;
                    try {
                        if(scheduleList != null && scheduleList.size() >= x)
                            courseSchedule = scheduleList.get(x - 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int y = 0; y < tableCells[x].length; y++) {
                        StringBuffer buffer = new StringBuffer();
                        switch (y) {
                            case 0:
                                if (isSingle) {
                                    buffer.append("第");
                                    buffer.append(x);
                                    buffer.append("节");
                                } else {
                                    buffer.append("第");
                                    buffer.append(x * 2 - 1).append("、").append(x * 2);
                                    buffer.append("节");
                                }
                                break;
                            case 1:
                                buffer.append(courseSchedule == null ? "无" : courseSchedule.getCourseTime());
                                break;
                            case 2:
                                buffer.append(courseSchedule == null || TextUtils.isEmpty(courseSchedule.getCourseName()) ? "空闲" : courseSchedule.getCourseName());
                                break;
                            case 3:
                                buffer.append(courseSchedule == null || TextUtils.isEmpty(courseSchedule.getGradeName()) ? "无" : courseSchedule.getGradeName());
                                break;
                            case 4:
                                buffer.append(courseSchedule == null || TextUtils.isEmpty(courseSchedule.getTeacherName()) ? "无" : courseSchedule.getTeacherName());
                                break;
                            default:
                                buffer.append("无");
                                break;
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
                for (int x = 1; x < tableCells.length; x++) {
                    for (int y = 0; y < tableCells[x].length; y++) {
                        StringBuffer buffer = new StringBuffer();
                        switch (y) {
                            case 0:
                                if (isSingle) {
                                    buffer.append("第");
                                    buffer.append(x);
                                    buffer.append("节");
                                } else {
                                    buffer.append("第");
                                    buffer.append(x * 2 - 1).append("、").append(x * 2);
                                    buffer.append("节");
                                }
                                break;
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            default:
                                buffer.append("无");
                                break;
                        }
                        tableCells[x][y].setText(buffer.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void loadCourseDataRow() {
        boolean isSingle = CourseTableStyle.SINGLE.getValue().equals(courseStyle);
        try {
            tableCells[0][0].setText("课节");
            tableCells[1][0].setText("时间");
            tableCells[2][0].setText("课程名称");
            tableCells[3][0].setText("上课班级");
            tableCells[4][0].setText("任课老师");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CourseHelper.getDefault().isLoaded()) {
            List<CourseSchedule> scheduleList;
            if (isSingle) {
                scheduleList = CourseHelper.getDefault().getDayCourseByDate(new Date()).getCourseSchedule(CourseListType.FREE_LIST);
            } else {
                scheduleList = CourseHelper.getDefault().getDayCourseByDate(new Date()).getCourseSchedule(CourseListType.FREE_COMBINE_BYCOUNT);
            }

            try {
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

                for (int x = 0; x < tableCells.length; x++) {
                    for (int y = 1; y < tableCells[x].length; y++) {
                        StringBuffer buffer = new StringBuffer();
                        CourseSchedule courseSchedule = null;
                        try {
                            if(scheduleList!= null && scheduleList.size() > y)
                                courseSchedule = scheduleList.get(y - 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        switch (x) {
                            case 0:
                                if (isSingle) {
                                    buffer.append("第");
                                    buffer.append(y);
                                    buffer.append("节");
                                } else {
                                    buffer.append("第");
                                    buffer.append(y * 2 - 1).append("、").append(y * 2);
                                    buffer.append("节");
                                }
                                break;
                            case 1:
                                buffer.append(courseSchedule == null ? "无" : courseSchedule.getCourseTime());
                                break;
                            case 2:
                                buffer.append(courseSchedule == null || TextUtils.isEmpty(courseSchedule.getCourseName()) ? "空闲" : courseSchedule.getCourseName());
                                break;
                            case 3:
                                buffer.append(courseSchedule == null || TextUtils.isEmpty(courseSchedule.getGradeName()) ? "无" : courseSchedule.getGradeName());
                                break;
                            case 4:
                                buffer.append(courseSchedule == null || TextUtils.isEmpty(courseSchedule.getTeacherName()) ? "无" : courseSchedule.getTeacherName());
                                break;
                            default:
                                buffer.append("无");
                                break;
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
                for (int x = 1; x < tableCells.length; x++) {
                    for (int y = 0; y < tableCells[x].length; y++) {
                        StringBuffer buffer = new StringBuffer();
                        switch (y) {
                            case 0:
                                if (isSingle) {
                                    buffer.append("第");
                                    buffer.append(y);
                                    buffer.append("节");
                                } else {
                                    buffer.append("第");
                                    buffer.append(y * 2 - 1).append("、").append(y * 2);
                                    buffer.append("节");
                                }
                                break;
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            default:
                                buffer.append("无");
                                break;
                        }
                        tableCells[x][y].setText(buffer.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
