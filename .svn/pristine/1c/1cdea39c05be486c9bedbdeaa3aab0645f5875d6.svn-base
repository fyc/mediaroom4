package com.sunmnet.mediaroom.common.bean;

/**
 * 从kafka转发的消息
 */
public class KafkaMessage extends SocketMessage {

    //教室编号
    protected String classroomCode;
    //目标类型枚举
    protected int target;
    //目标IP 1：核心服务 2：班牌 3：面板
    protected String targetIp;

    public KafkaMessage() {
    }

    public KafkaMessage(String uuid, String classroomCode, int target, String targetIp, String key, String msg) {
        this.uuid = uuid;
        this.classroomCode = classroomCode;
        this.target = target;
        this.targetIp = targetIp;
        this.key = key;
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    @Override
    public String toString() {
        return "KafkaMessage{" +
                "classroomCode='" + classroomCode + '\'' +
                ", target=" + target +
                ", targetIp='" + targetIp + '\'' +
                ", uuid='" + uuid + '\'' +
                ", key='" + key + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
