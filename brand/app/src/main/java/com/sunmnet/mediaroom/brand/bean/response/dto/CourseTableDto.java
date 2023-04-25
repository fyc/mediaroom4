package com.sunmnet.mediaroom.brand.bean.response.dto;

public class CourseTableDto {

    /**
     * courseNo : 第9节
     * courseName : 大学英语
     * gradeName : 测试班级、有间学院文学系语文一班
     * studentCount : 5
     * teacherName : 何老师
     * teacherCode : 1999065519
     */
    private String courseNo;
    private String courseName;
    private String gradeName;
    private int studentCount;
    private String teacherName;
    private String teacherCode;

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }
}
