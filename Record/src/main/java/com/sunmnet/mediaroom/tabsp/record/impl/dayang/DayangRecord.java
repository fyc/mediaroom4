package com.sunmnet.mediaroom.tabsp.record.impl.dayang;

import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class DayangRecord extends AbstractCirculateRecord {
    public DayangRecord(RecordEntity entity) {
        super(entity);
        this.playUrl = "http://" + this.bean.getHost() + ":1554/sd_stream0";
        //this.playUrl = "rtsp://admin:hik12345@172.16.2.250:554/h264/ch1/main/av_stream";
        //EventBus.getDefault().register(this);
        //refresh();
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                /**初始化根据获取到的录播状态来确定是否连接正常*/
                DayangRecord.this.recordStatus = getRecordState();
                EventBus.getDefault().postSticky(DayangRecord.this.recordStatus);
                if (recordStatus.getRecordStatus()!=RecordStatus.DISCONNECTED||recordStatus.getRecordStatus()!=RecordStatus.ERROR){
                    EventBus.getDefault().post(new IjkPresentation());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**这里重置状态。*/
                DayangRecord.this.run();
            }
        });
    }

    String playUrl;

    @Override
    public String getPlayUrl() {
        return this.playUrl;
    }

    @Override
    public <T> T getRecordState() {
        IRecordStatus status = new Status(RecordStatus.DISCONNECTED);
        String url = "http://" + bean.getHost() + "/GetRecordTopic";
        StringBuilder builder = new StringBuilder();
        builder.append("路径:");
        builder.append(url);
        try {
            Response response = OkHttpUtil.get(url, null, (Map<String, String>) null);
            String result = new String(response.body().bytes());
            builder.append(",执行结果：");
            builder.append(result);
            if (result != null) {
                result = result.replace("<", "");
                result = result.replace("<", ">");
                result = result.replace("[", "");
                result = result.replace("]", "");
                result = result.replace("recordTopic", "");
                if (result.contains("startRecord")) {
                    status = new Status(RecordStatus.RECODING, " ");
                } else if (result.contains("stopRecord")) {
                    status = new Status(RecordStatus.IDLE);
                } else if (result.contains("continueRecord")) {
                    status = new Status(RecordStatus.RECODING, " ");
                } else if (result.contains("pauseRecord")) {
                    status = new Status(RecordStatus.PAUSE, " ");
                } else {
                    RunningLog.run("未知状态，默认空闲");
                }
            }

        } catch (IOException connectErr) {
            RecordResult result = new RecordResult();
            result.setSuccess(false);
            result.setMessage("网络连接异常!!");
            setOperatingResult(result);
        } catch (Exception e) {
            RecordResult result = new RecordResult();
            result.setSuccess(false);
            result.setMessage("状态查询失败!!");
            setOperatingResult(result);
            RunningLog.error(e);
        }
        RunningLog.run(builder.toString());
        return (T) status;
    }

    private boolean executeCommand(String cmd) {
        boolean isSuccess = false;
        String url = "http://" + bean.getHost() + "/SetRecordTopic";
        Map<String, String> params = new HashMap<>();
        params.put("userName", "");
        params.put("password", "");
        params.put("fingerId", "123456789");
        params.put("cmd", cmd);
        RecordResult optResult = new RecordResult();
        try {
            Response response = OkHttpUtil.get(url, params, (Map<String, String>) null);
            String result = new String(response.body().bytes());
            RunningLog.run("操作：" + cmd + "-->结果：" + result);
            if (result != null) {
                if (result.contains("OK")) {
                    RunningLog.run("命令执行成功!!");
                    optResult.setSuccess(true);
                    isSuccess = true;
                } else {
                    optResult.setSuccess(false);
                    optResult.setMessage("命令执行失败!!");
                    RunningLog.run("命令执行失败");
                }
            }
        } catch (IOException e) {
            optResult = new RecordResult();
            optResult.setSuccess(false);
            optResult.setMessage("网络连接异常!!");
            //setOperatingResult(result);
        } catch (Exception e) {
            optResult = new RecordResult();
            optResult.setSuccess(false);
            optResult.setMessage("命令执行失败!!");
            EventBus.getDefault().post(optResult);
            //setOperatingResult(result);
            RunningLog.error(e);
        }

        EventBus.getDefault().post(optResult);
        return isSuccess;
    }

    @Override
    public boolean start(String uuid) {
        return executeCommand("startRecord");
    }

    @Override
    public boolean pause(String uuid) {
        return executeCommand("pauseRecord");
    }

    @Override
    public boolean goon(String uuid) {
        return executeCommand("continueRecord");
    }

    @Override
    public boolean stop(String uuid) {
        return executeCommand("stopRecord");
    }

}
