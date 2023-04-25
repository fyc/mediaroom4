package com.sunmnet.mediaroom.brand.bean.response.dto;

public class ClassroomStatusDto {

    /**
     * weekNum : 1
     * classroomCode : null
     * classroomName : null
     * type : 0
     * startTime : 16:00
     * endTime : 16:50
     * accountName : null
     * courseTable : {"courseNo":"第9节","courseName":"大学英语","gradeName":"测试班级、有间学院文学系语文一班","studentCount":5,"teacherName":"何老师","teacherCode":"1999065519"}
     */

    /**
     * 周几(学周模式下使用) 1-7 周一到周日
     */
    private Integer weekNum;
    private String classroomCode;
    private String classroomName;
    /**
     * 类型（0-上课、1-预约、2-借用，空闲为null）
     */
    private Integer type;
    /**
     * 开始时间 格式 HH:mm
     */
    private String startTime;
    private String endTime;
    /**
     * 借用人/预约人姓名
     */
    private String accountName;
    /**
     * 课程信息
     */
    private CourseTableDto courseTable;

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CourseTableDto getCourseTable() {
        return courseTable;
    }

    public void setCourseTable(CourseTableDto courseTable) {
        this.courseTable = courseTable;
    }
}
