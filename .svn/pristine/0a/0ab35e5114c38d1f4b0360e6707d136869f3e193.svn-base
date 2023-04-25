package com.sunmnet.mediaroom.common.tools;

import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.bean.course.DayCourseSchedule;
import com.sunmnet.mediaroom.common.enums.CourseListType;
import com.sunmnet.mediaroom.devicebean.new2.utils.JsonUtils;
import com.sunmnet.mediaroom.util.FileUtils;
import com.sunmnet.mediaroom.util.bean.course.CourseDto;
import com.sunmnet.mediaroom.util.bean.course.LessonDto;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseHelper {

    public static final String COURSE_DB_NAME = "course_json";
    public static final String COURSE_CONFIG_INFO = "course_config_info"; // 平台课程信息

    private static final CourseHelper defaultHelper = new CourseHelper();

    private List<CourseListener> mListeners = new ArrayList<>();

    public interface CourseListener {
        void onCourseLoaded();
    }

    public static CourseHelper getDefault() {
        return defaultHelper;
    }

    private CourseDto courseDto;

    private SparseArray<CourseTime> courseTimeArray;

    /**
     * 获取教学周
     *
     * @param date
     * @return
     */
    public int getWeekOfSemester(Date date) {
        if (this.courseDto != null) {
            return DateUtil.getWeekOfSemester(courseDto.getStartTime(), "yyyy-MM-dd", date);
        }
        return 0;
    }

    public int getWeekCount() {
        if (this.courseDto != null) {
            return TypeUtil.objToInt(courseDto.getWeekCount());
        }
        return 0;
    }

    public void setCourse(CourseDto dto) {
        if (dto != null) {
            String courseStr = JsonUtils.toJson(dto);
            SharePrefUtil.saveValue(BaseApplication.getInstance().getApplicationContext(), COURSE_DB_NAME, COURSE_CONFIG_INFO, courseStr);
            courseDto = dto;
            courseTimeArray = new SparseArray<>();
            String courseTime = courseDto.getCourseTime();
            String[] courses = courseTime.split(",");
            for (int i = 0; i < courses.length; i++) {
                String times = courses[i];
                String[] arr = times.split("-");
                if (arr.length == 3) {
                    CourseTime time = new CourseTime(arr[2], arr[0], arr[1]);
                    courseTimeArray.put(time.getKey(), time);
                }
            }
            for (CourseListener listener : mListeners) {
                listener.onCourseLoaded();
            }
        }
    }

    public void setCourse(File file) {
        setCourse(loadCourse(file));
    }

    public void setCourse(String courseString) {
        setCourse(loadCourse(courseString));
    }

    @Nullable
    public CourseDto loadCourse(String courseString) {
        return JacksonUtil.jsonStrToBean(courseString, CourseDto.class);
    }

    @Nullable
    public CourseDto loadCourse(File file) {
        StringBuilder builder = FileUtils.readFile(file, "utf8");
        return this.loadCourse(builder.toString());
    }


    public void addListener(CourseListener listener) {
        if (!mListeners.contains(listener))
            mListeners.add(listener);
    }

    public void cleanListener() {
        mListeners.clear();
    }

    public void removeListener(CourseListener listener) {
        mListeners.remove(listener);
    }

    public DayCourseSchedule getDayCourseByDate(Date date) {
        List<LessonDto> lessons = this.courseDto.getLessons();
        DayCourseSchedule daySchedule = null;
        try {
            daySchedule = new DayCourseSchedule(this.courseTimeArray, lessons, getWeekOfSemester(date), date);
        } catch (Exception e) {
            e.printStackTrace();
            RunningLog.error("创建DaySchedule出错: " + e.toString());
        }
        return daySchedule;
    }

    /**
     * 获取指定日期所在的周的课程安排
     *
     * @param date 指定日期
     */
    public List<DayCourseSchedule> getWeekSchedule(Date date) {
        List<DayCourseSchedule> weekSchedules = new ArrayList<>();
        int week = DateUtil.getWeekNo(date);
        while (week != 1) {
            date.setDate(date.getDate() - 1);
            week = DateUtil.getWeekNo(date);
        }
        for (int i = 0; i < 7; i++) {
            DayCourseSchedule schedule = getDayCourseByDate(date);
            weekSchedules.add(schedule);
            date.setDate(date.getDate() + 1);
        }
        return weekSchedules;
    }

    /**
     * 根据时间获取对应的上课时间CourseTime
     *      *
     * @param compareDate
     * @return
     */
    public CourseTime getCourseTime(Date compareDate) {
        String dateStr = DateUtil.formatDate(compareDate, DateUtil.DEFAULT_FORMAT_DATE);
        SparseArray<CourseTime> courseTimeArray = getCourseTimeArray();
        if (courseTimeArray != null) {
            for (int i = 0; i < courseTimeArray.size(); i++) {
                CourseTime courseTime = courseTimeArray.valueAt(i);
                if (DateUtil.isBetween(compareDate, courseTime.getStartTime(dateStr), courseTime.getEndTime(dateStr))) {
                    return courseTime;
                }
            }
        }
        return null;
    }

    /**
     * 根据时间获取下一节的上课时间CourseTime
     *
     * @param compareDate
     * @return
     */
    public CourseTime getNextCourseTime(Date compareDate) {
        String dateStr = DateUtil.formatDate(compareDate, DateUtil.DEFAULT_FORMAT_DATE);
        if(getCourseTimeArray()!=null){
            for (int i = 0; i < getCourseTimeArray().size(); i++) {
                CourseTime courseTime = getCourseTimeArray().valueAt(i);
                if (courseTime == null)
                    continue;
                //查找下一节课的时间
                if (DateUtil.isAfter(courseTime.getStartTime(dateStr), compareDate)) {
                    return courseTime;
                }
            }
        }
        return null;
    }

    public SparseArray<CourseTime> getCourseTimeArray() {
        return courseTimeArray;
    }

    /**
     * 是否加载完成课程表
     */
    public boolean isLoaded() {
        return this.courseDto != null;
    }

    public CourseDto getCourseDto() {
        if (courseDto == null) {
            String courseStr = SharePrefUtil.getString(BaseApplication.getInstance().getApplicationContext(), COURSE_DB_NAME, COURSE_CONFIG_INFO);
            if (courseStr != null) {
                courseDto = JSON.parseObject(courseStr, CourseDto.class);
            }
        }
        return courseDto;
    }

    /**
     * 获取指定日期所在教学周，某星期某课节的课程
     *
     * @param date    日期
     * @param week    星期 0为自动匹配，1-7对应周一到周日
     * @param classNo 课节 0为自动匹配，其余为匹配对应课节，双课节用逗号隔开
     * @return 课程 自动匹配只返回单课节课程。双课节会进行合并，课节以-分隔
     */
    public CourseSchedule getCourseSchedule(Date date, int week, String classNo) {
        return getCourseSchedule(date, week, classNo, 0);
    }

    /**
     * 获取指定日期所在教学周，某星期某课节的课程，可设置提前上课时间
     *
     * @param date         日期
     * @param week         星期 0为自动匹配，1-7对应周一到周日
     * @param classNo      课节 0为自动匹配，其余为匹配对应课节，双课节用逗号隔开
     * @param beforeMillis 提前上课时间，预备时间，单位毫秒
     * @return 课程 自动匹配只返回单课节课程。双课节会进行合并，课节以-分隔
     */
    public CourseSchedule getCourseSchedule(Date date, int week, String classNo, long beforeMillis) {
        CourseSchedule course = null;
        try {
            List<DayCourseSchedule> daySchedules = getWeekSchedule(date);
            DayCourseSchedule daySchedule;
            if (week == 0) {
                daySchedule = daySchedules.get(DateUtil.getWeekNoNow() - 1);
            } else {
                daySchedule = daySchedules.get(week - 1);
            }
            String[] clsNoArr = classNo.split(",");
            if (clsNoArr.length == 1 && TypeUtil.objToInt(clsNoArr[0]) == 0) {//自动匹配
                Date nowDate = new Date();
                Date nowDateEx = new Date(nowDate.getTime() + beforeMillis);
                String dateStr = DateUtil.formatDate(nowDate, DateUtil.DEFAULT_FORMAT_DATE);
                int clsNo = 0;
                //先判断当前在不在上课
                //不在上课期间的话，找出下一节课的时间
                //再判断当前时间加上偏差时间是否在下一节课的的上课时间之后
                for (int i = 0; i < getCourseTimeArray().size(); i++) {
                    CourseTime courseTime = getCourseTimeArray().valueAt(i);
                    if (DateUtil.isBetween(nowDate, courseTime.getStartTime(dateStr), courseTime.getEndTime(dateStr))) {
                        clsNo = TypeUtil.objToInt(courseTime.getClassNo());
                        break;
                    }
                }
                if (clsNo == 0) {
                    for (int i = 0; i < getCourseTimeArray().size(); i++) {
                        CourseTime courseTime = getCourseTimeArray().valueAt(i);
                        if (courseTime == null)
                            continue;
                        //查找第一个 上课开始时间在当前时间之后的课节，即下一节课的时间
                        if (DateUtil.isAfter(courseTime.getStartTime(dateStr), nowDate)) {
                            //判断加上偏差时间后在不在上课时间之后
                            if (DateUtil.isAfter(nowDateEx, courseTime.getStartTime(dateStr))) {
                                clsNo = TypeUtil.objToInt(courseTime.getClassNo());
                            }
                            break;
                        }
                    }
                }
                course = daySchedule.getCourse(clsNo);
            } else if (clsNoArr.length == 1) {//单课节
                course = daySchedule.getCourse(TypeUtil.objToInt(clsNoArr[0]));
            } else if (clsNoArr.length == 2) {//双课节
                //合并后的课程列表
                List<CourseSchedule> list = daySchedule.getCourseSchedule(CourseListType.FREE_COMBINE);
                String doubleClsNo = clsNoArr[0] + "-" + clsNoArr[1];
                for (CourseSchedule schedule : list) {
                    //对比
                    if (doubleClsNo.equals(schedule.getClassNo())) {
                        course = schedule;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

}
