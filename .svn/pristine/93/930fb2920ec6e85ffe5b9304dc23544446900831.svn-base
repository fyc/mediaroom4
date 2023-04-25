package com.sunmnet.mediaroom.tabsp.record.impl.xiwoo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.util.JsonUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import okhttp3.Response;

/**
 * 希沃录播版本SV10
 */

public class XiwooRecord extends AbstractCirculateRecord {

    static final int HANDLER_REFRESH_TOKEN_CODE = 0x01;
    static final String HANDLER_REFRESH_TOKEN_TOKEN = "refresh_token";

    String connectPath;
    JsonParser parser = new JsonParser();
    String token = null;
    String playPath = null;
    long lastActive = 0;
    String aislePort; // 通道号
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_REFRESH_TOKEN_CODE:
                    refreshToken();
                    break;
            }
        }
    };

    @Override
    public String getPlayUrl() {
        return playPath;
    }

    public XiwooRecord(RecordEntity recordBean) {
        super(recordBean);
        connectPath = "http://" + recordBean.getHost() + ":" + recordBean.getPort();
        aislePort = recordBean.getParams().get("aislePort");
        if (aislePort != null) {
            playPath = "rtsp://" + recordBean.getHost() + ":554/avstream/channel=" + aislePort + "/stream=1.sdp";
        } else {
            RunningLog.run("希沃录播没有通道号，playPath为null");
        }
        new Thread(() -> {
            boolean isLogin = login();
            if (isLogin) {
                RunningLog.run("录播设备" + bean.getHost() + " 登录成功!!");
                IRecordStatus status = getRecordState();
                EventBus.getDefault().postSticky(status);
                //readState(status);
                activeChecker.refresh();
                // activeChecker.run();
                RunningLog.run("希沃，eventBus发送:XiwooPresentation");
                EventBus.getDefault().postSticky(new XiwooPresentation());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**这里重置状态。*/
                XiwooRecord.this.run();
            }
        }).start();
    }

    class ActiveChecker implements Runnable {
        long activeTime = System.currentTimeMillis();
        Object locker = new Object();

        @Override
        public void run() {
            long timetask = 15 * 60 * 1000;
            try {
                while (true) {
                    synchronized (this.locker) {
                        long now = System.currentTimeMillis();
                        if (now - activeTime >= timetask) {
                            break;
                        } else {
                            now = activeTime + timetask;
                        }
                        RunningLog.run("正在刷新会话时间!!");
                        this.locker.wait(now);
                    }
                }
            } catch (Exception e) {
                RunningLog.error(e);
                RunningLog.error("XiwoReord会话检查出现异常");
            } finally {
                token = null;
            }
            RunningLog.run("退出会话管理!!");
        }

        public void refresh() {
            activeTime = System.currentTimeMillis();
        }
    }

    ActiveChecker activeChecker = new ActiveChecker();

    private String get(String path, Map<String, String> params, Map<String, String> header) throws IOException {
        Response response = null;
        response = OkHttpUtil.get(path, params, header);
        String result = new String(response.body().bytes());
        return result;
    }

    private String post(String path, Map<String, String> params, Map<String, String> header) throws IOException {
        Response response = null;
        String mapToJson = mapToJson(params);
        System.out.println(path + "参数：" + mapToJson + " ,header:" + JsonUtils.objectToJson(header));
        response = OkHttpUtil.postJson(path, mapToJson, header);
        String result = new String(response.body().bytes());
        return result;
    }

    private String mapToJson(Map<String, String> params) {
        if (params != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            int size = 1;
            for (String key : params.keySet()) {
                builder.append("\"");
                builder.append(key);
                builder.append("\"");
                builder.append(":\"");
                builder.append(params.get(key));
                builder.append("\"");
                if (size < params.size()) {
                    builder.append(",");
                }
                size++;
            }
            builder.append("}");
            return builder.toString();
        }
        return "";

    }

    private boolean login() {
        String path = connectPath + "/sdk/LoginSystem";
        RunningLog.run("登录访问：" + path);
        Map<String, String> bodyParam = new HashMap<>();
        bodyParam.put("userName", bean.getUserName());
        bodyParam.put("password", bean.getPassword());
        try {
            String result = post(path, bodyParam, null);
            RunningLog.run("登录结果：" + JsonUtils.objectToJson(result));
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            int statusCode = jsonObject.get("result").getAsInt();
            if (statusCode <= 0) {
                RunningLog.error("录播登录失败!!!");
                return false;
            }
            String message = jsonObject.get("errMsg").getAsString();
            JsonObject resultData = jsonObject.getAsJsonObject("data");
            String token = resultData.get("token").getAsString();
            this.token = token;
            resetTokenCountTime();
            return true;
        } catch (IOException ioe) {
            onServerError();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void onServerError() {
        this.token = null;
        EventBus.getDefault().postSticky(new Status(RecordStatus.ERROR));
        //readState(new Status(RecordStatus.ERROR));

    }

    @Override
    public <T> T getRecordState() {
        if (this.token == null) {
            return (T) new Status(RecordStatus.IDLE);
        }
        String path = this.connectPath + "/sdk/GetRecordStatus";
        Map<String, String> param = new LinkedHashMap<>();
        param.put("channel", aislePort);
        param.put("token", this.token);
        try {
            String result = get(path, param, null);
            RunningLog.run("希沃录播，请求状态返回, result:" + result);
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            int statusCode = jsonObject.get("result").getAsInt();
            String message = jsonObject.get("errMsg").getAsString();
            RunningLog.run("希沃录播，请求状态返回, statusCode:" + statusCode);
            RunningLog.run("希沃录播，请求状态返回, message:" + message);
            RecordResult recordResult = new RecordResult();
            if (statusCode <= 0) {
                recordResult.setSuccess(false);
                recordResult.setMessage("获取录播状态失败");
                return (T) new Status(RecordStatus.IDLE);
            } else {
                recordResult.setSuccess(true);
                if (jsonObject.has("data") && jsonObject.get("data") != null) {
                    JsonObject resultData = jsonObject.getAsJsonObject("data");
                    int recordStatus = resultData.get("recordStatus").getAsInt();
                    RunningLog.run("希沃录播，状态值:" + recordStatus);
                    activeChecker.refresh();
                    switch (recordStatus) {
                        case 1:
                            return (T) new Status(RecordStatus.RECODING);
                        case 2:
                            return (T) new Status(RecordStatus.PAUSE);
                        case 0:
                            return (T) new Status(RecordStatus.IDLE);
                        default:
                            return (T) new Status(RecordStatus.IDLE);
                    }
                }
            }
            EventBus.getDefault().post(recordResult);
        } catch (IOException e) {
            RunningLog.error(e);
            return (T) new Status(RecordStatus.ERROR);
        } catch (Exception e) {
            RunningLog.error(e);
        }

        return (T) new Status(RecordStatus.IDLE);
    }

    private boolean action(String action) {
        String path = this.connectPath + "/sdk/";
        boolean actionResult = false;
        if (this.token == null) {
            RunningLog.run("正在执行" + action + ",当前未登录，执行登录！");
            actionResult = login();
            if (!actionResult) {
                RunningLog.error("执行" + action + "失败!!登录异常");
                return false;
            }
        }
        switch (action) {
            case "StartRecord":
                path += action;
                break;
            case "PauseRecord":
                path += action;
                break;
            case "StopRecord":
                path += action;
                break;
        }
        Map<String, String> params = new HashMap<>();
        try {
            params.put("token", this.token);
            Response response = OkHttpUtil.get(path, params, (Map<String, String>) null);
            String result = new String(response.body().bytes());
            RunningLog.run("路径:" + path + "   -->" + result);
            JsonObject jsonObject = parser.parse(result).getAsJsonObject();
            activeChecker.refresh();
            int statusCode = jsonObject.get("result").getAsInt();
            RecordResult recordResult = new RecordResult();
            if (statusCode > 0) {
                recordResult.setSuccess(true);
            } else if (statusCode == -50) {
                // token失效
                this.token = refreshTokenAndGetResult();
                response = OkHttpUtil.get(path, params, (Map<String, String>) null);
                result = new String(response.body().bytes());
                jsonObject = parser.parse(result).getAsJsonObject();
                statusCode = jsonObject.get("result").getAsInt();
                if (statusCode > 0) {
                    recordResult.setSuccess(true);
                } else {
                    recordResult.setSuccess(false);
                    recordResult.setMessage("录播请求失败");
                }
            } else {
                recordResult.setSuccess(false);
                recordResult.setMessage("录播请求失败");
            }
            EventBus.getDefault().post(recordResult);
            return statusCode > 0;
        } catch (Exception e) {
            RunningLog.error(e);
        }
        resetTokenCountTime();
        return actionResult;
    }

    @Override
    public boolean start(String uuid) {
        /*String path = this.connectPath + "/sdk/StartRecord";
        RunningLog.run("开始录播");
        Map<String, String> params = new HashMap<>();
        params.put("", this.token);
        return post(1);*/
        return action("StartRecord");
    }

    @Override
    public boolean pause(String uuid) {
        /*RunningLog.run("暂停录播");
        return post(2);*/
        return action("PauseRecord");
    }

    @Override
    public boolean goon(String uuid) {
        RunningLog.run("继续录播");
        return start(uuid);
    }

    @Override
    public boolean stop(String uuid) {
        /*RunningLog.run("结束录播");
        return post(3);*/
        return action("StopRecord");
    }

    @Override
    public void release() {
        super.release();
        mHandler.removeCallbacksAndMessages(null);
    }

    private boolean post(int action) {
        if (token != null) {
            String path = this.connectPath + "/sdk/v1/record/status";
            Map<String, String> header = new HashMap();
            Map<String, String> bodyParam = new HashMap<>();
            bodyParam.put("action", action + "");
            header.put("token", this.token);
            String result = null;
            try {
                result = post(path, bodyParam, header);
                RunningLog.run("操作结果：:" + result);
                JsonObject jsonObject = parser.parse(result).getAsJsonObject();
                int statusCode = jsonObject.get("statusCode").getAsInt();
                String message = jsonObject.get("message").getAsString();
                return statusCode == 200;
            } catch (IOException e) {
                RunningLog.error(e);
                onServerError();
                return false;
            } catch (Exception e) {
                RunningLog.error(e);
                return false;
            }

        } else {
            RunningLog.run("未登录");
            return false;
        }
    }

    /**
     * 重设token有效倒计时
     */
    private void resetTokenCountTime() {
        RunningLog.run("希沃录播，重置刷新token倒计时");

        mHandler.removeCallbacksAndMessages(HANDLER_REFRESH_TOKEN_TOKEN);
        Message message = Message.obtain();
        message.what = HANDLER_REFRESH_TOKEN_CODE;
        message.obj = HANDLER_REFRESH_TOKEN_TOKEN;
        mHandler.sendMessageDelayed(message, 840000);
    }

    /**
     * 刷新token
     */
    private void refreshToken() {
        new Thread(() -> {
            String path = connectPath + "/sdk/LoginSystem";
            RunningLog.run("希沃录播，刷新token：" + path);
            Map<String, String> bodyParam = new HashMap<>();
            bodyParam.put("userName", bean.getUserName());
            bodyParam.put("password", bean.getPassword());
            try {
                String result = post(path, bodyParam, null);
                RunningLog.run("希沃录播，刷新token结果：" + JsonUtils.objectToJson(result));
                JsonObject jsonObject = parser.parse(result).getAsJsonObject();
                int statusCode = jsonObject.get("result").getAsInt();
                if (statusCode <= 0) {
                    RunningLog.error("希沃录播，刷新token失败!!!");
                }
                String message = jsonObject.get("errMsg").getAsString();
                JsonObject resultData = jsonObject.getAsJsonObject("data");
                String token = resultData.get("token").getAsString();
                this.token = token;
                resetTokenCountTime();
            } catch (Exception ioe) {
            }
        }).start();
    }

    /**
     * 获取刷新token
     * 不能运行在UI线程
     * @return
     */
    private String refreshTokenAndGetResult() {
        Callable<String> refreshTokenWork = () -> {
            String path = connectPath + "/sdk/LoginSystem";
            RunningLog.run("希沃录播，刷新token：" + path);
            Map<String, String> bodyParam = new HashMap<>();
            bodyParam.put("userName", bean.getUserName());
            bodyParam.put("password", bean.getPassword());
            try {
                String result = post(path, bodyParam, null);
                RunningLog.run("希沃录播，刷新token结果：" + JsonUtils.objectToJson(result));
                JsonObject jsonObject = parser.parse(result).getAsJsonObject();
                int statusCode = jsonObject.get("result").getAsInt();
                if (statusCode <= 0) {
                    RunningLog.error("希沃录播，刷新token失败!!!");
                }
                String message = jsonObject.get("errMsg").getAsString();
                JsonObject resultData = jsonObject.getAsJsonObject("data");
                String token = resultData.get("token").getAsString();
                resetTokenCountTime();
                return token;
            } catch (Exception ioe) {
                RunningLog.run("希沃，刷新token出错");
                return null;
            }
        };

        FutureTask<String> refreshTokenTask = new FutureTask<>(refreshTokenWork);
        Thread thread = new Thread(refreshTokenTask);
        thread.start();
        try {
            return refreshTokenTask.get();
        } catch (ExecutionException | InterruptedException e) {
            RunningLog.run("希沃，获取刷新token出错");
            e.printStackTrace();
            return null;
        }
    }
}
