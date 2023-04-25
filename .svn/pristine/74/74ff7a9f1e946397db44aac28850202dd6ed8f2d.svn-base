package com.sunmnet.mediaroom.common.bean.course;

import android.text.TextUtils;
import android.util.SparseArray;

import com.sunmnet.mediaroom.common.enums.CourseListType;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.util.bean.course.LessonDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 指定日期的课程安排
 */
public class DayCourseSchedule {
    private Date date;
    private int weekOfSemester;
    private SparseArray<CourseSchedule> course;

    /***
     * @param times 每节课的时间安排
     * @param lessons 所有课程
     * @param date 指定日期课程
     * @param weekOfSemester 教学周
     * */
    public DayCourseSchedule(SparseArray<CourseTime> times, List<LessonDto> lessons, int weekOfSemester, Date date) {
        this.course = new SparseArray<>();
        this.weekOfSemester = weekOfSemester;
        String weekRange = weekOfSemester + "";
        String weekOfDate = DateUtil.getWeekNo(date) + "";
        this.date = DateUtil.getMidnightDate(date);

        // 根据课程时间，生成课节
        for (int i = 0; i < times.size(); i++) {
            int key = times.keyAt(i);
            CourseTime time = times.get(key);
            CourseSchedule sc = new CourseSchedule(this.date, weekOfSemester, time);
            this.course.put(time.getKey(), sc);
        }

        // 同个课程多个班级的映射
        Map<String, Set<String>> sameGradeCodeMap = new HashMap<>();
        // 根据课表数据，设置当天的课程
        for (int i = 0; i < lessons.size(); i++) {
            LessonDto lessonDto = lessons.get(i);
            String scRange = lessonDto.getCycleRange();
            String scWeek = lessonDto.getWeek();
            if (scWeek.contains(weekOfDate)) {
                if (isInRange(scRange, weekRange)) {
                    String[] classNoArr = lessonDto.getClassNo().split(",");
                    for (String classNo : classNoArr) {
                        int courseNo = Integer.parseInt(classNo);
                        CourseSchedule schedule = this.course.get(courseNo);
                        if (schedule != null) {
                            if (schedule.getCourseCode() != null) {
                                if (!sameGradeCodeMap.containsKey(schedule.getCourseCode())) {
                                    Set<String> gradeCodeSet = new HashSet<>();
                                    gradeCodeSet.add(schedule.getGradeCode());
                                    sameGradeCodeMap.put(schedule.getCourseCode(), gradeCodeSet);
                                }
                                if (!sameGradeCodeMap.get(schedule.getCourseCode()).contains(lessonDto.getGradeCode())) {
                                    sameGradeCodeMap.get(schedule.getCourseCode()).add(lessonDto.getGradeCode());
                                    String gradeName = schedule.getGradeName() + "、" + lessonDto.getGradeName();
                                    schedule.setCourseInfoData(lessonDto);
                                    schedule.setGradeName(gradeName);
                                } else {
                                    String gradeName = schedule.getGradeName();
                                    schedule.setCourseInfoData(lessonDto);
                                    schedule.setGradeName(gradeName);
                                }
                            } else {
                                schedule.setCourseInfoData(lessonDto);
                            }
                        } else {
                            CourseTime time = times.get(TypeUtil.objToInt(classNo));
                            schedule = new CourseSchedule(this.date, weekOfSemester, time, lessonDto);
                            this.course.put(courseNo, schedule);
                        }
                    }
                }
            }
        }
    }

    /**
     * 课程是否在课表周几内
     */
    private boolean isInWeek(String weekRange, String weekNum) {
        boolean isInWeek = false;
        String[] weeks = weekRange.split("-");
        if (weeks.length == 1) {
            isInWeek = weeks[0].equals(weekNum);
        } else if (weeks.length == 2) {
            int from = Integer.parseInt(weeks[0]), to = Integer.parseInt(weeks[1]), now = Integer.parseInt(weekNum);
            isInWeek = now >= from && now <= to;
        }
        return isInWeek;
    }

    /**
     * 课表是否在学期中的第几周内
     */
    private boolean isInRange(String cycleRange, String weekNum) {
        boolean isInRange = false;
        if (!cycleRange.contains(",")) {
            isInRange = isInWeek(cycleRange, weekNum);
        } else {
            String[] args = cycleRange.split(",");
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                isInRange = isInWeek(arg, weekNum);
                if (isInRange) break;
            }
        }
        return isInRange;
    }

    public void setCourse(SparseArray<CourseSchedule> course) {
        this.course = course;
    }


    /**
     * 获取当天所有课程
     *
     * @param type 排序方式
     */
    public List<CourseSchedule> getCourseSchedule(CourseListType type) {
        return this.getCourseSchedule(type, 2);
    }

    /**
     * 获取当天课程列表，默认方式
     */
    public List<CourseSchedule> getCourseSchedule() {
        CourseListType type = CourseListType.FREE_LIST;
        return this.getCourseSchedule(type);
    }

    /**
     * 获取当天所有课程
     *
     * @param type  数据合并排序方式
     * @param count 数据合并数量
     */
    public List<CourseSchedule> getCourseSchedule(CourseListType type, int count) {
        count = count <= 0 ? 1 : count;
        List<CourseSchedule> schedules = new ArrayList<>();
        boolean isFree = false;
        boolean isCombine = false;
        boolean byCount = false;
        switch (type) {
            case FREE_COMBINE_BYCOUNT:
                byCount = true;
            case FREE_COMBINE:
                isCombine = true;
            case FREE_LIST://不合并，显示空闲
                isFree = true;
                break;
            case NO_FREE_COMBINE_BYCOUNT://合并、不显示空闲
                byCount = true;
            case NO_FREE_COMBINE://合并、不显示空闲
                isCombine = true;
            case NO_FREE_LIST://不合并、不显示空闲
                isFree = false;
                break;
        }
        CourseSchedule last = null;
        int counter = 0;
        for (int i = 0; i < this.course.size(); i++) {
            int key = this.course.keyAt(i);
            CourseSchedule schedule = this.course.get(key);
            if (!isFree && (schedule == null || TextUtils.isEmpty(schedule.getCourseCode())
                    || "0".equals(schedule.getCourseCode()))) {
                continue;
            }
            if (isCombine) {
                if (last == null) {
                    last = new CourseSchedule(schedule);
                    schedules.add(last);
                    counter++;
                } else {
                    if (TextUtils.equals(last.getCourseCode(), schedule.getCourseCode()) || byCount) {
                        bind(last, schedule);
                        counter++;
                    }
                    if (byCount) {
                        if (!TextUtils.equals(last.getCourseCode(), schedule.getCourseCode())) {
                            String lastName = last.getCourseName();
                            String scheduleName = schedule.getCourseName();
                            lastName = lastName == null ? "空闲" : lastName;
                            scheduleName = scheduleName == null ? "空闲" : scheduleName;
                            if (lastName.equals(scheduleName)) {
                                last.setCourseName(lastName);
                            } else {
                                last.setCourseName(lastName + "," + scheduleName);
                            }
                        }

                        if (!TextUtils.equals(last.getTeacherName(), schedule.getTeacherName())) {
                            String lastName = last.getTeacherName();
                            String scheduleName = schedule.getTeacherName();
                            lastName = lastName == null ? "无" : lastName;
                            scheduleName = scheduleName == null ? "无" : scheduleName;
                            if (lastName.equals(scheduleName)) {
                                last.setTeacherName(lastName);
                            } else {
                                last.setTeacherName(lastName + "," + scheduleName);
                            }
                        }

                        if (!TextUtils.equals(last.getGradeCode(), schedule.getGradeCode())) {
                            String lastName = last.getGradeName();
                            String scheduleName = schedule.getGradeName();
                            lastName = lastName == null ? "无" : lastName;
                            scheduleName = scheduleName == null ? "无" : scheduleName;
                            if (lastName.equals(scheduleName))
                                last.setGradeName(lastName);
                            else
                                last.setGradeName(lastName + "," + scheduleName);
                        }
                    }
                }
                if (byCount) {
                    if (counter == count) {
                        counter = 0;
                        last = null;
                    }
                } else if (counter == 2) {
                    counter = 0;
                    last = null;
                }
            } else {
                schedules.add(schedule);
            }
        }
        return schedules;
    }


    /**
     * 合并相同的数据
     *
     * @param first  合并目标
     * @param second 合并元数据
     */
    private void bind(CourseSchedule first, CourseSchedule second) {
        //1.合并节号 2.合并课程时间 3.合并开始和结束时间
        first.setEndTimeStr(second.getEndTimeStr());
        first.setEndTime(second.getEndTime());

        String classNo = first.getClassNo();
        if (classNo.contains("-")) {
            //截取
            String[] numbers = classNo.split("-");
            classNo = numbers[0] + "-" + second.getClassNo();
        } else {
            classNo = classNo + "-" + second.getClassNo();
        }
        first.setClassNo(classNo);
    }

    private LessonDto combineLesson(LessonDto first, LessonDto second) {
        if (first == null && second == null)
            return null;
        Map<String, String> firstMap = null;
        Map<String, String> secondMap = null;
        if (first != null)
            firstMap = JacksonUtil.jsonStrToMap_SS(JacksonUtil.objToJsonStr(first));
        if (second != null)
            secondMap = JacksonUtil.jsonStrToMap_SS(JacksonUtil.objToJsonStr(second));
        Set<String> keySet = firstMap != null ? firstMap.keySet() : null;
        keySet = (keySet == null && secondMap != null) ? secondMap.keySet() : null;
        if (keySet != null) {
            Map<String, String> combineMap = new HashMap<>();
            for (String key : keySet) {
                String valueFirst = firstMap != null ? firstMap.get(key) : null;
                String valueSecond = secondMap != null ? secondMap.get(key) : null;
                if (!TextUtils.equals(valueFirst, valueSecond)) {
                    combineMap.put(key, valueFirst + "," + valueSecond);
                } else {
                    combineMap.put(key, valueFirst);
                }
            }
            return JacksonUtil.jsonStrToBean(JacksonUtil.mapToJsonStr(combineMap), LessonDto.class);
        }
        return null;
    }

    public CourseSchedule getCourse(int classNo) {
        CourseSchedule courseSchedule = this.course.get(classNo, null);
        return courseSchedule;
    }

    public CourseSchedule getCourse(String classNo) {
        return getCourse(Integer.parseInt(classNo));
    }


    public int getWeekOfSemester() {
        return weekOfSemester;
    }

    public void setWeekOfSemester(int weekOfSemester) {
        this.weekOfSemester = weekOfSemester;
    }

}
