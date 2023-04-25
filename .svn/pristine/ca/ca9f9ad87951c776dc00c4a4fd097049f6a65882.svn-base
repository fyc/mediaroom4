package com.sunmnet.mediaroom.tabsp.record.impl;

import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecord;

import org.greenrobot.eventbus.EventBus;

public abstract class AbstractRecord implements IRecord {
    protected RecordEntity bean;
    protected IRecordStatus recordStatus = new Status(RecordStatus.DISCONNECTED);

    public AbstractRecord(RecordEntity entity) {
        this.bean = entity;
    }
    RecordResult lastError;
    protected void notifyStatusChanged() {
        EventBus.getDefault().postSticky(this.recordStatus);
    }

    @Override
    public void setRecordListener(RecordListener listener) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public <T> T getRecordState() {
        return null;
    }
    /**
     * 操作结果回调
     * */
    protected void setOperatingResult(RecordResult result) {
        lastError=result;
    }

    @Override
    public <T> T getLastError() {
        RecordResult result=this.lastError;
        this.lastError=null;
        return (T) result;
    }

    @Override
    public String getPlayUrl() {
        return null;
    }

    @Override
    public boolean start(String uuid) {
        return false;
    }

    @Override
    public boolean pause(String uuid) {
        return false;
    }

    @Override
    public boolean goon(String uuid) {
        return false;
    }

    @Override
    public boolean stop(String uuid) {
        return false;
    }

    @Override
    public void release() {

    }
}
