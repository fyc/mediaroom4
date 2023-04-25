package com.sunmnet.mediaroom.brand.bean.response;

import com.sunmnet.mediaroom.common.bean.Result;

import java.util.List;

public class BorrowInfoResponse extends Result<List<BorrowInfoResponse.Result>> {

    public static class Result {
        //@ApiModelProperty(value = "编号")
        private String id;

        //@ApiModelProperty(value = "审批人")
        private String approverName;

        //@ApiModelProperty(value = "申请人")
        private String applicantName;

        //@ApiModelProperty(value = "教室编号")
        private String classroomCode;

        //@ApiModelProperty(value = "借用类型")
        private Integer appointmentType;

        //@ApiModelProperty(value = "原由")
        private String cause;

        //@ApiModelProperty(value = "借用日期")
        private String borrowDate;

        //@ApiModelProperty(value = "开始时间")
        private String startTime;

        //@ApiModelProperty(value = "结束时间")
        private String endTime;

        //@ApiModelProperty(value = "状态 1:未审批 2:审批中 3:审批通过 4:审批拒绝 5:取消")
        private Integer status;

        //@ApiModelProperty(value = "借用类型文本")
        private String appointmentTypeText;

        //@ApiModelProperty(value = "状态文本")
        private String statusText;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApproverName() {
            return approverName;
        }

        public void setApproverName(String approverName) {
            this.approverName = approverName;
        }

        public String getApplicantName() {
            return applicantName;
        }

        public void setApplicantName(String applicantName) {
            this.applicantName = applicantName;
        }

        public String getClassroomCode() {
            return classroomCode;
        }

        public void setClassroomCode(String classroomCode) {
            this.classroomCode = classroomCode;
        }

        public Integer getAppointmentType() {
            return appointmentType;
        }

        public void setAppointmentType(Integer appointmentType) {
            this.appointmentType = appointmentType;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getBorrowDate() {
            return borrowDate;
        }

        public void setBorrowDate(String borrowDate) {
            this.borrowDate = borrowDate;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getAppointmentTypeText() {
            return appointmentTypeText;
        }

        public void setAppointmentTypeText(String appointmentTypeText) {
            this.appointmentTypeText = appointmentTypeText;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
        }
    }
}
