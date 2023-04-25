package com.sunmnet.mediaroom.tabsp.record.impl.hik;

import com.sunmnet.mediaroom.tabsp.record.impl.AbstractRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.bean.InteractiveBean;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.services.InteractiveService;

import java.util.List;

public class HikInteractionRecord extends AbstractRecord implements InteractiveService<InteractiveBean> {
    HikRecord record;
    public HikInteractionRecord(HikRecord record) {
        super(null);
        this.record = record;
    }

    @Override
    public void setRecordListener(RecordListener listener) {
        this.record.setRecordListener(listener);
    }

    @Override
    public <T> T getRecordState() {
        return (T) this.record.getRecordState();
    }

    @Override
    public String getPlayUrl() {
        return this.record.getPlayUrl();
    }

    @Override
    public boolean start(String uuid) {
        return this.record.start(uuid);
    }

    @Override
    public boolean pause(String uuid) {
        return this.record.start(uuid);
    }

    @Override
    public boolean goon(String uuid) {
        return this.record.start(uuid);
    }

    @Override
    public boolean stop(String uuid) {
        return this.record.start(uuid);
    }

    @Override
    public void release() {
        this.record.release();
    }

    @Override
    public List<InteractiveBean> getSettingParners() {
        return null;
    }

    @Override
    public List<InteractiveBean> getPartificants() {
        return null;
    }

    @Override
    public boolean invite(List<InteractiveBean> partificants) {
        return false;
    }

    @Override
    public boolean intrrupt() {
        return false;
    }

    @Override
    public boolean quit() {
        return false;
    }
}
