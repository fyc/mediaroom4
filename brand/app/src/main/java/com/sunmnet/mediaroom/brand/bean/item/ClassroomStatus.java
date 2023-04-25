package com.sunmnet.mediaroom.brand.bean.item;

public class ClassroomStatus {
    String classroomName;
    String content;
    //（0-上课、1-预约、2-借用，-1 空闲）
    int status;

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
