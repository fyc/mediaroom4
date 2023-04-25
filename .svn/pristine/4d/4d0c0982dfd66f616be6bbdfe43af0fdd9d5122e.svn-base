package com.sunmnet.mediaroom.tabsp.record.impl.Inxedu;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Response;

public class InxeduRecord extends AbstractCirculateRecord {
    String urlPath;
    JsonParser parser = new JsonParser();
    String sessionId;
    String task;
    String rtmpUrl;

    @Override
    public String getPlayUrl() {
        return this.rtmpUrl;
    }

    public InxeduRecord(RecordEntity entity) {
        super(entity);
        this.urlPath = "http://" + bean.getHost() + "/web/activeProtocol.action";
        this.rtmpUrl = "rtsp://" + bean.getHost() + "/1";
        new Thread(new Runnable() {
            @Override
            public void run() {
                RunningLog.run("开始登录!!");
                boolean isLogin = login();
                if (isLogin) {
                    RunningLog.run("录播设备" + bean.getHost() + " 登录成功!!");
                    IRecordStatus status = getRecordState();
                    EventBus.getDefault().postSticky(status);
                    //readState(status);
                    String params = "{\"method\": \"getRTMPProfiles\"}";
                    try {
                        JsonObject paramResult = formateJson(post(urlPath, params));
                        JsonArray rtmpProfiles = paramResult.getAsJsonArray("rtmpProfiles");
                        if (rtmpProfiles.size() > 0) {
                            JsonObject first = rtmpProfiles.get(0).getAsJsonObject();
                            String pushUrl = first.get("pushUrl").getAsString();
                            if (pushUrl != null) {
                                rtmpUrl = pushUrl;
                            }
                        }
                    } catch (Exception e) {
                        RunningLog.error(e);
                    }
                } else RunningLog.run("登录失败!!");
                //check();
            }
        }).start();
    }

    private void check() {
        String params = "{\"method\": \"getRTMPProfiles\"}";
        JsonObject paramResult = formateJson(post(this.urlPath, params));
        if (paramResult != null) {
            JsonArray rtmps = paramResult.getAsJsonArray("rtmpProfiles");
            JsonObject rtmp = rtmps.get(0).getAsJsonObject();
            if (rtmp != null) {
                this.rtmpUrl = rtmp.get("url").getAsString();
                this.rtmpUrl = "rtsp://" + bean.getHost() + "/2";
            }
        }
    }

    private String post(String urlPath, String params) {
        RunningLog.run("请求地址：" + urlPath + "   请求参数：" + params);
        Response response = null;
        String result = null;
        try {
            response = OkHttpUtil.postJson(urlPath, params, null);
            result = new String(response.body().bytes());
            RunningLog.run("请求结果:" + result);
        } catch (IOException e) {
            RunningLog.error(e);
            RunningLog.warn("请求失败！！");
        }
        return result;
    }

    private boolean login() {
        String params = "{\"method\": \"login\",\"params\": {\"userName\":\"" + bean.getUserName() + "\",\"password\": \"" + bean.getPassword() + "\"}}";
        JsonObject paramResult = formateJson(post(this.urlPath, params));
        if (paramResult != null) {
            this.sessionId = paramResult.get("sessionID").getAsString();
            return true;
        }
        return false;
    }

    private JsonObject formateJson(String result) {
        try {
            JsonObject object = parser.parse(result).getAsJsonObject();
            JsonObject jsonResult = object.getAsJsonObject("result");
            JsonObject paramResult = jsonResult.getAsJsonObject("params");
            int retCode = paramResult.get("retCode").getAsInt();
            if (retCode == 0) {
                return paramResult;
            } else return null;
        } catch (Exception e) {
            RunningLog.error(e);
        }
        return null;
    }

    @Override
    public <T> T getRecordState() {
        String params = "{\"method\": \"getRecordTaskProfiles\"}";
        try {
            JsonObject paramResult = formateJson(post(this.urlPath, params));
            if (paramResult != null) {
                JsonArray jsonArray = paramResult.getAsJsonArray("recordTaskProfiles");
                JsonObject object = jsonArray.get(0).getAsJsonObject();
                int state = object.get("status").getAsInt();
                this.task = object.get("token").getAsString();
                switch (state) {
                    case 1:
                        return (T) new Status(RecordStatus.RECODING);
                    case 2:
                        return (T) new Status(RecordStatus.PAUSE);
                    case 3:
                        return (T) new Status(RecordStatus.IDLE);
                    default:
                        return (T) new Status(RecordStatus.IDLE);
                }

            }
        } catch (Exception e) {
            RunningLog.error(e);
        }
        return (T) new Status(RecordStatus.IDLE);
    }

    @Override
    public boolean start(String uuid) {
        if (task != null) {
            String params = "{\"method\": \"startRecordTaskProfile\",\"params\": {\"token\":\"" + this.task + "\"}}";
            JsonObject paramResult = formateJson(post(this.urlPath, params));
            return paramResult != null;
        }
        return false;
    }

    @Override
    public boolean pause(String uuid) {
        if (task != null) {
            String params = "{\"method\": \"pauseRecordTask\",\"params\": {\"token\":\"" + this.task + "\"}}";
            JsonObject paramResult = formateJson(post(this.urlPath, params));
            return paramResult != null;
        }
        return false;
    }

    @Override
    public boolean goon(String uuid) {
        if (task != null) {
            String params = "{\"method\": \"resumeRecordTask\",\"params\": {\"token\":\"" + this.task + "\"}}";
            JsonObject paramResult = formateJson(post(this.urlPath, params));
            return paramResult != null;
        }
        return false;
    }

    @Override
    public boolean stop(String uuid) {
        if (task != null) {
            String params = "{\"method\": \"stopRecordTask\",\"params\": {\"token\":\"" + this.task + "\"}}";
            JsonObject paramResult = formateJson(post(this.urlPath, params));
            return paramResult != null;
        }
        return false;
    }
}
