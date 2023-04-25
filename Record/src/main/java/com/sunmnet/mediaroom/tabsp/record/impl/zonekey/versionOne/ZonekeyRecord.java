package com.sunmnet.mediaroom.tabsp.record.impl.zonekey.versionOne;

import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;

import org.greenrobot.eventbus.EventBus;

public class ZonekeyRecord extends AbstractRecord {
    public ZonekeyRecord(RecordEntity entity) {
        super(entity);
        if (bean != null) {
            this.playUrl = "rtsp://" + bean.getHost() + ":" + bean.getPort() + "/" + bean.getUserName() + "_film";
            this.handler = new TcpRecord(bean.getHost(), 1230, listener);
            //this.handler.start();
        }
    }

    TcpRecord handler;

    public interface RecordTcpListener {
        public void onReceivMessage(String str);

        public void onConnected();
    }

    RecordTcpListener listener = new RecordTcpListener() {
        @Override
        public void onReceivMessage(String str) {
            String regx = "recordStatus=";
            int index = str.lastIndexOf(regx);
            str = str.substring(index, str.length());
            int endIndex = str.indexOf("&");
            index = regx.length();
            String result = str.substring(index, endIndex);
            if ("RecordStopped".equals(result)) {
                readState(new Status(RecordStatus.IDLE));
            } else if ("Recording".equals(result)) {
                readState(new Status(RecordStatus.RECODING, null, " "));
            } else if ("RecordPaused".equals(result)) {
                readState(new Status(RecordStatus.PAUSE, null, " "));
            }
        }

        IRecordPresentation presentation;

        @Override
        public void onConnected() {
            if (presentation == null) {
                presentation = new IjkPresentation();
                EventBus.getDefault().postSticky(presentation);
            }
        }
    };

    String playUrl = null;

    @Override
    public String getPlayUrl() {
        return this.playUrl;
    }

    @Override
    public <T> T getRecordState() {
        return null;
    }

    @Override
    public boolean start(String uuid) {
        String cmd = "RecordCmd=StartRecord";
        return executeCommand(cmd);
    }

    @Override
    public boolean pause(String uuid) {
        String cmd = "RecordCmd=PauseRecord";
        return executeCommand(cmd);
    }

    @Override
    public boolean goon(String uuid) {
        String cmd = "RecordCmd=ResumeRecord";
        return executeCommand(cmd);
    }

    @Override
    public boolean stop(String uuid) {
        String cmd = "RecordCmd=StopRecord";
        return executeCommand(cmd);
    }

    private boolean executeCommand(String cmd) {
        try {
            cmd += "\n";
            return this.handler.send(cmd);
        } catch (Exception e) {
            return false;
        }
    }

    protected void readState(IRecordStatus status) {
        if (status == null) return;
        this.recordStatus = status;
        EventBus.getDefault().postSticky(this.recordStatus);
        /*if (status==null) return;
        if (this.recordStatus != null && this.recordStatus.getRecordStatus() != status.getRecordStatus()) {
            onStateChanged(status);
        }
        if (listener != null) {
            switch (status.getRecordStatus()) {
                case RECODING:
                    this.listener.onRecording(status);
                    //启动线程读取
                    onStateRead(true);
                    break;
                case IDLE:
                    onStateRead(false);
                    this.listener.onRecordIdle(status);
                    break;
                case PAUSE:
                    onStateRead(false);
                    this.listener.onRecordPaused(status);
                    break;
                case SAVED:
                    onStateRead(false);
                    this.listener.onRecordSaved(status);
                    status.setRecordStatus(RecordStatus.IDLE);
                    break;
            }
        }
        this.recordStatus = status;*/
    }

    protected void onStateChanged(IRecordStatus status) {
        switch (this.recordStatus.getRecordStatus()) {
            case PAUSE:
            case RECODING:
                switch (status.getRecordStatus()) {
                    case IDLE:
                        status.setRecordStatus(RecordStatus.SAVED);
                        break;
                }
                break;
        }
    }
}
