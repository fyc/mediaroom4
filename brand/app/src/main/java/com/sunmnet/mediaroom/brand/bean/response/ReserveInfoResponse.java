package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.util.List;

public class ReserveInfoResponse extends Result<List<ReserveInfoResponse.Result>> {

    public static class Result {

        private String id;

        private String nameCn;

        private String code;

        private String departmentName;

        private String gradeName;

        private String classroomCode;

        //(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
        private String startTime;

        //(pattern="yyyy-MM-dd HH:mm", timezone="GMT+8")
        private String endTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getClassroomCode() {
            return classroomCode;
        }

        public void setClassroomCode(String classroomCode) {
            this.classroomCode = classroomCode;
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
    }
}
