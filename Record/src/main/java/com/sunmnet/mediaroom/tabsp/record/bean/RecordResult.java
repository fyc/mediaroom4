package com.sunmnet.mediaroom.tabsp.record.bean;

import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecord;

public class RecordResult {
    /**
     * 是否操作成功
     * */
    boolean success;

    public IRecord.RecordStatus getAction() {
        return action;
    }

    public void setAction(IRecord.RecordStatus action) {
        this.action = action;
    }

    IRecord.RecordStatus action;
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 提示
     * */
    String message;
}
