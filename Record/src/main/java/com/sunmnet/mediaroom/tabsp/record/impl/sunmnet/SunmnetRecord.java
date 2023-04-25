package com.sunmnet.mediaroom.tabsp.record.impl.sunmnet;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;
import com.sunmnet.mediaroom.tabsp.record.impl.sunmnet.services.RecordConnectListener;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;

import org.greenrobot.eventbus.EventBus;

import java.net.URI;

public class SunmnetRecord extends AbstractRecord implements RecordConnectListener {

    public SunmnetRecord(RecordEntity entity) {
        super(entity);
        init(entity.getHost(), entity.getPort(), entity.getUserName(), entity.getPassword());
    }

    RecordWebSocket webSocket;
    String user, pwd;
    protected boolean isRecording = false;
    String url;

    private void init(String host, String port, String admin, String pwd) {
        webSocket = new RecordWebSocket(URI.create("ws://" + host + ":" + port), this);
        webSocket.connectAsync();
        recordStatus = new Status(RecordStatus.IDLE);
        this.user = admin;
        this.pwd = pwd;
    }

    @Override
    public String getPlayUrl() {
        return this.url;
    }

    @Override
    public IRecordStatus getRecordState() {
        String command = "#;&3#;&xns#;&1#;&3#;&3#;&cmd#;&19#;&getGuideStationInfoForApp#;&";
        if (webSocket.isOpen()) webSocket.send(command);
        return null;
    }

    @Override
    public boolean start(String uuid) {
        String title = null;
        title = DateUtil.getNowDateString("yyyy-MM-dd HH:mm");
        title = title.replace("-", "");
        title = title.replace(":", "");
        title = title.replace(" ", "");
        // title="201909291032";
        String command = "#;&3#;&xns#;&1#;&3#;&3#;&cmd#;&11#;&startRecord#;&5#;&title#;&12#;&" + title + "#;&9#;&speakerId#;&0#;&#;&11#;&speakerName#;&4#;&null#;&8#;&courseId#;&0#;&#;&10#;&courseName#;&0#;&#;&11#;&courseBrief#;&0#;&#;&\n";
        this.webSocket.send(command);
        return true;
    }

    @Override
    public boolean pause(String uuid) {
        String command = "#;&3#;&xns#;&1#;&3#;&3#;&cmd#;&11#;&pauseRecord#;&";
        if (webSocket.isClosed()) return false;
        else {
            webSocket.send(command);
            return true;
        }
    }

    @Override
    public boolean goon(String uuid) {
        String command = "#;&3#;&xns#;&1#;&3#;&3#;&cmd#;&14#;&continueRecord#;&";
        if (webSocket.isClosed()) return false;
        else {
            webSocket.send(command);
            return true;
        }
    }

    @Override
    public boolean stop(String uuid) {
        String command = "#;&3#;&xns#;&1#;&3#;&3#;&cmd#;&10#;&stopRecord#;&";
        if (webSocket.isClosed()) return false;
        else {
            webSocket.send(command);
            return true;
        }
    }

    IRecordPresentation presentation;

    @Override
    public void onConnected() {
        if (presentation == null) {
            presentation = new IjkPresentation();
            EventBus.getDefault().postSticky(presentation);
        }
        //登录之后马上查询
        getRecordState();
    }

    @Override
    public void onDisconnect() {
        RunningLog.run("录播控制掉线，重新连接");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                webSocket.reconnect();
                RunningLog.run("与录播服务器的连接正在尝试重连!!");
            }
        }).start();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public <T> void onMessage(T t) {
        JsonObject record = (JsonObject) t;
        System.out.println(record);
        if (record.get("recordStatus") != null) {
            int recordStatus = record.get("recordStatus").getAsInt();
            IRecordStatus state = null;
            String durationTime = null;
            if (record.get("recordInfo") != null) {
                durationTime = record.get("recordInfo").getAsJsonObject().get("durationTime").getAsString();
            } else if (record.get("durationTime") != null) {
                durationTime = record.get("durationTime").getAsString();
            }
            if (record.get("httpFlvUrl") != null) {
                String url = record.get("httpFlvUrl").getAsString();
                RunningLog.run("流媒体播放地址：" + url);
                if (!TextUtils.isEmpty(url) && !url.equals(this.url)) {
                    this.url = url;
                }
            } else if (record.get("RtmpUrl") != null) {
                String url = record.get("RtmpUrl").getAsString();
                RunningLog.run("流媒体播放地址：" + url);
                if (!TextUtils.isEmpty(url) && !url.equals(this.url)) {
                    this.url = url;
                }
            } else if (record.get("url") != null) {
                String url = record.get("url").getAsString();
                RunningLog.run("流媒体播放地址：" + url);
                if (!TextUtils.isEmpty(url) && !url.equals(this.url)) {
                    this.url = url;
                }
            }
            switch (recordStatus) {
                case 0:
                    this.recordStatus = new Status(RecordStatus.IDLE);
                    //this.listener.onRecordIdle(state);
                    break;
                case 1:
                    this.recordStatus = new Status(RecordStatus.PAUSE, "", durationFormat(durationTime));
                    // this.listener.onRecordPaused(state);
                    break;
                case 2:
                    this.recordStatus = new Status(RecordStatus.RECODING, "", durationFormat(durationTime));
                    //this.listener.onRecording(state);
                    break;
            }
            notifyStatusChanged();
        }
    }

    String durationFormat(String duration) {
        Integer totalSeconds = 0;
        try {
            totalSeconds = Integer.parseInt(duration);
        } catch (Exception e) {
            totalSeconds = null;
        }
        if (totalSeconds == null || totalSeconds < 1) {
            return "00:01";
        }
        //将秒格式化成HH:mm:ss
        //这里应该用Duration更合理，但它不能格式化成字符串
        //而使用LocalTime，在时间超过24小时后格式化也会有问题（！）
        int hours = totalSeconds / 3600;

        int rem = totalSeconds % 3600;
        int minutes = rem / 60;
        int seconds = rem % 60;
        String res = null;
        if (hours <= 0) {
            res = String.format("%02d:%02d", minutes, seconds);
        } else res = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return res;
    }
}
