package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

public class AttendQrCodeResponse extends Result<AttendQrCodeResponse.Result> {

    @Override
    public String toString() {
        return "AttendQrCodeResponse{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }

    public static class Result {

        /**
         * uuid : ae6706acc03c4b89984482de7496339a
         * type : attendanceSign
         * classroomCode : S1-0203
         * timestamp : 1616143769
         * verifyCode : 187e86bb257d6c5221f6d56c92494843
         */

        private String uuid;
        private String type;
        private String classroomCode;
        private int timestamp;
        private String verifyCode;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getClassroomCode() {
            return classroomCode;
        }

        public void setClassroomCode(String classroomCode) {
            this.classroomCode = classroomCode;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "uuid='" + uuid + '\'' +
                    ", type='" + type + '\'' +
                    ", classroomCode='" + classroomCode + '\'' +
                    ", timestamp=" + timestamp +
                    ", verifyCode='" + verifyCode + '\'' +
                    '}';
        }
    }

}
