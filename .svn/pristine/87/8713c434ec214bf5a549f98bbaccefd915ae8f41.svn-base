package com.sunmnet.mediaroom.common.bean.course;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.util.bean.course.LessonDto;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by dengzl_pc on 2018/1/31.
 */

public class CourseSchedule implements Serializable {
    public static String formateClassNo(String classNo) {
        String[] classNoToChinese = new String[]{
                "第一节", "第二节", "第三节", "第四节", "第五节", "第六节",
                "第七节", "第八节", "第九节", "第十节", "第十一节", "第十二节",
                "第十三节", "第十四节", "第十五节", "第十六节", "第十七节", "第十八节",
                "第十九节", "第二十节", "第二十一节", "第二十二节", "第二十三节", "第二十四节"
        };
        try {
            int claszzNo = Integer.parseInt(classNo);
            claszzNo--;
            if (claszzNo < classNoToChinese.length)
                return classNoToChinese[claszzNo];
            else return classNo;
        } catch (Exception e) {
            RunningLog.error(e);
            return classNo;
        }
    }

    /**
     * 教学周
     */
    private int weekOfSemester;

    /**
     * 星期几，1-7对应星期一到星期日
     */
    private int weekNo;

    /**
     * 课节，多课节时，用-连接
     */
    private String classNo;

    /**
     * 课节长度
     */
    private int courseLength;

    private Date startTime;
    private Date endTime;

    private String courseCode;
    private String accountId;
    private String jobCode;
    private String gradeCode;
    private String gradeName;
    private String majorName;
    private String departmentName;
    private String academyName;
    private String teacherName;
    private String studentNum;
    private String courseName;

    private String startTimeStr;

    private String endTimeStr;

    public CourseSchedule() {
    }

    public CourseSchedule(CourseSchedule courseSchedule) {
        setWeekOfSemester(courseSchedule.getWeekOfSemester());
        setClassNo(courseSchedule.getClassNo());
        setCourseTimeData(courseSchedule.getStartTime(), courseSchedule.getStartTimeStr(), courseSchedule.getEndTimeStr());
        courseCode = courseSchedule.getCourseCode();
        accountId = courseSchedule.getAccountId();
        jobCode = courseSchedule.getJobCode();
        gradeCode = courseSchedule.getGradeCode();
        gradeName = courseSchedule.getGradeName();
        majorName = courseSchedule.getMajorName();
        departmentName = courseSchedule.getDepartmentName();
        academyName = courseSchedule.getAcademyName();
        teacherName = courseSchedule.getTeacherName();
        studentNum = courseSchedule.getStudentNum();
        courseName = courseSchedule.getCourseName();
    }

    public CourseSchedule(Date date, int weekOfSemester, String classNo, String startTime, String endTime) {
        this.weekOfSemester = weekOfSemester;
        setClassNo(classNo);
        setCourseTimeData(date, startTime, endTime);
    }

    public CourseSchedule(Date date, int weekOfSemester, CourseTime courseTime) {
        this(date, weekOfSemester, courseTime.getClassNo(), courseTime.getStart(), courseTime.getEnd());
    }

    public CourseSchedule(Date date, int weekOfSemester, CourseTime courseTime, LessonDto lessonDto) {
        this(date, weekOfSemester, courseTime.getClassNo(), courseTime.getStart(), courseTime.getEnd(), lessonDto);
    }

    public CourseSchedule(Date date, int weekOfSemester, String classNo, String startTime, String endTime, LessonDto lessonDto) {
        this.weekOfSemester = weekOfSemester;
        setClassNo(classNo);
        setCourseTimeData(date, startTime, endTime);
        setCourseInfoData(lessonDto);
    }

    public void setCourseTimeData(Date date, String startTime, String endTime) {
        startTimeStr = startTime;
        endTimeStr = endTime;
        Date startTimePart = DateUtil.parseDateStr(startTime, CourseTime.COURSE_TIME_FORMAT);
        Date endTimePart = DateUtil.parseDateStr(endTime, CourseTime.COURSE_TIME_FORMAT);
        Date datePart = DateUtil.getMidnightDate(date);
        int offset = TimeZone.getDefault().getOffset(startTimePart.getTime());
        this.startTime = new Date(datePart.getTime() + startTimePart.getTime() + offset);
        this.endTime = new Date(datePart.getTime() + endTimePart.getTime() + offset);
        this.weekNo = DateUtil.getWeekNo(date);
    }

    public void setCourseInfoData(LessonDto lessonDto) {
        courseCode = lessonDto.getCourseCode();
        accountId = lessonDto.getAccountId();
        jobCode = lessonDto.getJobCode();
        gradeCode = lessonDto.getGradeCode();
        gradeName = lessonDto.getGradeName();
        majorName = lessonDto.getMajorName();
        departmentName = lessonDto.getDepartmentName();
        academyName = lessonDto.getAcademyName();
        teacherName = lessonDto.getTeacherName();
        studentNum = lessonDto.getStudentNum();
        courseName = lessonDto.getCourseName();
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
        if (!TextUtils.isEmpty(classNo)) {
            String[] classNoArr = classNo.split("-");
            if (classNoArr.length == 1) {
                courseLength = 1;
                return;
            } else if (classNoArr.length > 1) {
                int startNo = TypeUtil.objToInt(classNoArr[0]);
                int endNo = TypeUtil.objToInt(classNoArr[classNoArr.length - 1]);
                if (startNo > 0 && endNo >= startNo) {
                    courseLength = endNo - startNo + 1;
                    return;
                }
            }
        }
        courseLength = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getWeekOfSemester() {
        return weekOfSemester;
    }

    public void setWeekOfSemester(int weekOfSemester) {
        this.weekOfSemester = weekOfSemester;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(int weekNo) {
        this.weekNo = weekNo;
    }

    public int getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(int courseLength) {
        this.courseLength = courseLength;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    String courseTime;
    public void setCourseTime(String courseTime){
        this.courseTime=courseTime;
    }
    public String getCourseTime() {
        if(courseTime!=null){
            return courseTime;
        }
        StringBuilder sb = new StringBuilder();
        if (TextUtils.isEmpty(startTimeStr)) {
            sb.append("?").append("-");
        } else {
            sb.append(startTimeStr).append("-");
        }
        if (TextUtils.isEmpty(endTimeStr)) {
            sb.append("?");
        } else {
            sb.append(endTimeStr);
        }
        return sb.toString();
    }
}
