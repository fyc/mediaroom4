package com.sunmnet.mediaroom.brand.enums;

public enum TemplateRight {

    CourseTable("课程表", "classSchedule"),
    AskForLeave("请假", "askForLeave"),
    AttendanceResult("考勤结果", "attendanceResult"),
    ControlCenter("控制中心", "controlCenter"),
    Monitor("视频监控", "videoMonitor"),
    ReportFault("故障申报", "reportFault"),
    QueryClassroomStatus("教室查询", "classroomStatusQuery"),
    BorrowClassroom("教室借用", "borrowClassroom"),
    TeachingInspection("教学巡查", "teachingInspection");

    String name;
    String code;

    TemplateRight(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static TemplateRight getRightByCode(String code) {
        for (TemplateRight right : values()) {
            if (right.code.equalsIgnoreCase(code)) {
                return right;
            }
        }
        return null;
    }
}
