package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.util.List;

public class ExamDetailResponse extends Result<List<ExamDetailResponse.ExamDetail>> {

    public static class ExamDetail {

        /**
         * id : 916c2299d4f14c8aa0d04e6da642a320
         * examId : 0b79bb182b664cd0826fd0d67ad6bf07
         * classroomCode : T1-101
         * examLocation : 第一教学楼101
         * examRoom : 第一考场
         * examRound : 第一场
         * examDate : 2020-07-21
         * examTimeStart : 10:30
         * examTimeEnd : 12:00
         * examCourse : 大学数学
         * examCourseTeacher : 玉慧敏
         * examClass : 计算机2班
         * examInvigilator : 余慧敏、黄琪琪
         * classroomName : null
         * examinees : [{"id":"6275bf8ccfca41588dd8f0d3c0651c0a","examDetailId":"916c2299d4f14c8aa0d04e6da642a320","name":"考生1","no":"2020000966","idNumber":"440100200202299999","registrationNumber":"0508064478924","seatNumber":"01"}]
         */
        private String id;
        private String examId;
        private String classroomCode;
        private String examLocation;
        private String examRoom;
        private String examRound;
        private String examDate;
        private String examTimeStart;
        private String examTimeEnd;
        private String examCourse;
        private String examCourseTeacher;
        private String examClass;
        private String examInvigilator;
        private Object classroomName;
        private List<Examinees> examinees;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

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

        public String getExamTimeStart() {
            return examTimeStart;
        }

        public void setExamTimeStart(String examTimeStart) {
            this.examTimeStart = examTimeStart;
        }

        public String getExamTimeEnd() {
            return examTimeEnd;
        }

        public void setExamTimeEnd(String examTimeEnd) {
            this.examTimeEnd = examTimeEnd;
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

        public String getExamInvigilator() {
            return examInvigilator;
        }

        public void setExamInvigilator(String examInvigilator) {
            this.examInvigilator = examInvigilator;
        }

        public Object getClassroomName() {
            return classroomName;
        }

        public void setClassroomName(Object classroomName) {
            this.classroomName = classroomName;
        }

        public List<Examinees> getExaminees() {
            return examinees;
        }

        public void setExaminees(List<Examinees> examinees) {
            this.examinees = examinees;
        }

        public static class Examinees {
            /**
             * id : 6275bf8ccfca41588dd8f0d3c0651c0a
             * examDetailId : 916c2299d4f14c8aa0d04e6da642a320
             * name : 考生1
             * no : 2020000966
             * idNumber : 440100200202299999
             * registrationNumber : 0508064478924
             * seatNumber : 01
             */

            private String id;
            private String examDetailId;
            private String name;
            private String no;
            private String idNumber;
            private String registrationNumber;
            private String seatNumber;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getExamDetailId() {
                return examDetailId;
            }

            public void setExamDetailId(String examDetailId) {
                this.examDetailId = examDetailId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getIdNumber() {
                return idNumber;
            }

            public void setIdNumber(String idNumber) {
                this.idNumber = idNumber;
            }

            public String getRegistrationNumber() {
                return registrationNumber;
            }

            public void setRegistrationNumber(String registrationNumber) {
                this.registrationNumber = registrationNumber;
            }

            public String getSeatNumber() {
                return seatNumber;
            }

            public void setSeatNumber(String seatNumber) {
                this.seatNumber = seatNumber;
            }
        }
    }

}
