package com.sunmnet.mediaroom.brand.bean;

import java.util.List;

public class ExamTimetable {


    private List<TimeTableBean> timeTable;

    public List<TimeTableBean> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(List<TimeTableBean> timeTable) {
        this.timeTable = timeTable;
    }

    public static class TimeTableBean {
        /**
         * classroomCode : T1-101
         * examLocation : S201
         * examRoom : 第一考场
         * examRound : 第一场
         * examDate : 2017-12-20
         * examTime :  09:00-11:00
         * examCourse : 大学英语
         * examCourseTeacher : 余慧敏
         * examClass : 计算机1班
         * examPeople : 40
         * examInvigilator : 余慧敏、张三
         */

        private String classroomCode;
        private String examLocation;
        private String examRoom;
        private String examRound;
        private String examDate;
        private String examTime;
        private String examCourse;
        private String examCourseTeacher;
        private String examClass;
        private String examPeople;
        private String examInvigilator;

        public String getClassroomCode() {
            return classroomCode;
        }

        public void setClassroomCode(String classroomCode) {
            this.classroomCode = classroomCode;
        }

        public String getExamLocation() {
            return examLocation;
        }

        public void setExamLocation(String examLocation) {
            this.examLocation = examLocation;
        }

        public String getExamRoom() {
            return examRoom;
        }

        public void setExamRoom(String examRoom) {
            this.examRoom = examRoom;
        }

        public String getExamRound() {
            return examRound;
        }

        public void setExamRound(String examRound) {
            this.examRound = examRound;
        }

        public String getExamDate() {
            return examDate;
        }

        public void setExamDate(String examDate) {
            this.examDate = examDate;
        }

        public String getExamTime() {
            return examTime;
        }

        public void setExamTime(String examTime) {
            this.examTime = examTime;
        }

        public String getExamCourse() {
            return examCourse;
        }

        public void setExamCourse(String examCourse) {
            this.examCourse = examCourse;
        }

        public String getExamCourseTeacher() {
            return examCourseTeacher;
        }

        public void setExamCourseTeacher(String examCourseTeacher) {
            this.examCourseTeacher = examCourseTeacher;
        }

        public String getExamClass() {
            return examClass;
        }

        public void setExamClass(String examClass) {
            this.examClass = examClass;
        }

        public String getExamPeople() {
            return examPeople;
        }

        public void setExamPeople(String examPeople) {
            this.examPeople = examPeople;
        }

        public String getExamInvigilator() {
            return examInvigilator;
        }

        public void setExamInvigilator(String examInvigilator) {
            this.examInvigilator = examInvigilator;
        }
    }
}
