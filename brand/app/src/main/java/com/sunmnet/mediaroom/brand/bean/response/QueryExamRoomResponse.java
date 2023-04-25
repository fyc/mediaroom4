package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.util.List;

/**
 * 考场查询结果
 */
public class QueryExamRoomResponse extends Result<List<QueryExamRoomResponse.Result>> {

    public static class Result {

        /**
         * examCourse : string
         * examDate : string
         * examRoom : string
         * examTimeEnd : string
         * examTimeStart : string
         * idNumber : string
         * name : string
         * no : string
         * registrationNumber : string
         * seatNumber : string
         */

        private String examCourse;
        private String examDate;
        private String examRoom;
        private String examTimeEnd;
        private String examTimeStart;
        private String idNumber;
        private String name;
        private String no;
        private String registrationNumber;
        private String seatNumber;

        public String getExamCourse() {
            return examCourse;
        }

        public void setExamCourse(String examCourse) {
            this.examCourse = examCourse;
        }

        public String getExamDate() {
            return examDate;
        }

        public void setExamDate(String examDate) {
            this.examDate = examDate;
        }

        public String getExamRoom() {
            return examRoom;
        }

        public void setExamRoom(String examRoom) {
            this.examRoom = examRoom;
        }

        public String getExamTimeEnd() {
            return examTimeEnd;
        }

        public void setExamTimeEnd(String examTimeEnd) {
            this.examTimeEnd = examTimeEnd;
        }

        public String getExamTimeStart() {
            return examTimeStart;
        }

        public void setExamTimeStart(String examTimeStart) {
            this.examTimeStart = examTimeStart;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
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
