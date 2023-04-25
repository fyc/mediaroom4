package com.sunmnet.mediaroom.brand.bean.event;

/**
 * Create by WangJincheng on 2021-08-02
 * 考勤事件
 */

public class AttendanceEvent {
    // 考勤反馈消息
    private String msg;

    public AttendanceEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
