package com.sunmnet.mediaroom.brand.bean.response;


import com.sunmnet.mediaroom.common.bean.Result;

import java.io.Serializable;
import java.util.List;

public class UserCourseScheduleResponse extends Result<List<UserCourseScheduleResponse.Result>> {

    public static class Result implements Serializable {

        /**
         * ids : ["88c0699828084f65a0c01408aacd5a9d"]
         * classroomCode : A1-0222
         * classroomName : A1-0222教室
         * courseCode : DXYY
         * classNo : 3
         * courseName : 大学英语
         * week : 6
         * gradeCodes : ["YOUWENXUEYUWENONE"]
         * gradeName : 有间学院文学系语文一班
         * studentCount : 3
         * teacherId : 0ee07637b45945a1ace7c73882bbc2bc
         * teacherName : 测试1
         * teacherCode : 00001
         */
        private String classroomCode;
        private String classroomName;
        private String courseCode;
        private int classNo;
        private String courseName;
        private int week;
        private String gradeName;
        private int studentCount;
        private String teacherId;
        private String teacherName;
        private String teacherCode;
        private List<String> ids;
        private List<String> gradeCodes;

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

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public int getClassNo() {
            return classNo;
        }

        public void setClassNo(int classNo) {
            this.classNo = classNo;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
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

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
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

        public List<String> getIds() {
            return ids;
        }

        public void setIds(List<String> ids) {
            this.ids = ids;
        }

        public List<String> getGradeCodes() {
            return gradeCodes;
        }

        public void setGradeCodes(List<String> gradeCodes) {
            this.gradeCodes = gradeCodes;
        }
    }
}
