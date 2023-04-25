package com.sunmnet.mediaroom.brand.bean.item;

import android.text.TextUtils;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.sunmnet.mediaroom.brand.bean.response.QueryCourseInfoResponse;

@SmartTable(name = "课表查询结果")
public class CourseQueryResultItem {

    @SmartColumn(id = 1, name = "学生姓名")
    private String studentName;

    @SmartColumn(id = 2, name = "课程名称")
    private String courseName;

    @SmartColumn(id = 3, name = "上课老师")
    private String teacherName;

    @SmartColumn(id = 4, name = "上课时间")
    private String time;

    @SmartColumn(id = 5, name = "班级名称")
    private String gradeName;

    @SmartColumn(id = 6, name = "上课教室")
    private String classroomName;

    public CourseQueryResultItem() {
    }

    public CourseQueryResultItem(String studentName, String courseName, String teacherName, String time, String gradeName, String classroomName) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.time = time;
        this.gradeName = gradeName;
        this.classroomName = classroomName;
    }

    public CourseQueryResultItem(QueryCourseInfoResponse.Result responseResult) {
        this.studentName = responseResult.getStudentName();
        this.courseName = responseResult.getCourseName();
        this.teacherName = responseResult.getTeacherName();
        this.time = responseResult.getClassTime();
        this.gradeName = responseResult.getClassName();
        this.classroomName = responseResult.getClassroomName();

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }
}
