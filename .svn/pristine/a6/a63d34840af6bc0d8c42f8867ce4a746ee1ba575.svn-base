package com.sunmnet.mediaroom.common.bean;

public class KafkaMessageResponse extends KafkaMessage {

    //状态码 0:正常 1：异常
    private int status;
    //错误信息
    private String errorMsg;

    public KafkaMessageResponse() {
    }

    public KafkaMessageResponse(String classroomCode, int target, String targetIp, String key, String msg, int status, String errorMsg) {
        this.classroomCode = classroomCode;
        this.target = target;
        this.targetIp = targetIp;
        this.key = key;
        this.msg = msg;
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public KafkaMessageResponse(KafkaMessage message) {
        this.classroomCode = message.getClassroomCode();
        this.target = message.getTarget();
        this.targetIp = message.getTargetIp();
        this.key = message.getKey();
    }

    public KafkaMessageResponse(KafkaMessage message, String msg, int status, String errorMsg) {
        this(message);
        this.msg = msg;
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "KafkaMessageResponse{" +
                "status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", classroomCode='" + classroomCode + '\'' +
                ", target=" + target +
                ", targetIp='" + targetIp + '\'' +
                ", uuid='" + uuid + '\'' +
                ", key='" + key + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
