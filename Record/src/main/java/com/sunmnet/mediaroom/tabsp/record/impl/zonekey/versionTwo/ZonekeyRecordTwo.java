package com.sunmnet.mediaroom.tabsp.record.impl.zonekey.versionTwo;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create by WangJincheng on 2021-12-21
 * 中庆录播第二版
 */

public class ZonekeyRecordTwo extends AbstractRecord {

    TcpRecordTwo handler;

    IRecordPresentation presentation;

    String playUrl = null;

    RecordTcpListener listener = new RecordTcpListener() {

        @Override
        public void onReceiveMessage(String str) {
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

        @Override
        public void onConnected() {
            if (presentation == null) {
                presentation = new IjkPresentation();
                EventBus.getDefault().postSticky(presentation);
            }
        }

        @Override
        public void getPlayUrl(String json) {
            try {
                RunningLog.debug("中庆录播, playUrlJson:" + json);
                JSONObject jsonObject = new JSONObject(json);
                ZonekeyRecordTwo.this.playUrl = jsonObject.getString("teacher-full");
                RunningLog.debug("中庆录播, playUrl:" + ZonekeyRecordTwo.this.playUrl);
            } catch (JSONException e) {
                RunningLog.error(e.getMessage());
                e.printStackTrace();
            }
        }
    };

    public ZonekeyRecordTwo(RecordEntity entity) {
        super(entity);
        if (super.bean != null) {
            try {
                int port = Integer.parseInt(super.bean.getPort());
                this.handler = new TcpRecordTwo(super.bean.getHost(), port, this.listener);
                this.handler.start();
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.error(e.getMessage());
            }
        }
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
    }

    interface RecordTcpListener {
        void onReceiveMessage(String str);

        void onConnected();

        void getPlayUrl(String playUrl);
    }
}
