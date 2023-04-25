package com.sunmnet.mediaroom.tabsp.record.impl.ava;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractRecord;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;
import com.sunmnet.mediaroom.util.MD5Util;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Response;

public class AvaRecord extends AbstractCirculateRecord {
    protected String loginKey = null;
    protected String path = null;
    protected String playUrl = null;

    public AvaRecord(RecordEntity entity) {
        super(entity);
        path = "http://" + this.bean.getHost() + "/cgi-bin/plat.cgi";
        //主码流 rtsp://10.60.141.139:554/stream/0?config.login=web
        //副码流 rtsp://10.60.141.139:554/stream/4?config.login=web
        this.playUrl = "rtsp://" + this.bean.getHost() + ":554/stream/4?config.login=web";
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                loginAbsolute();
            }
        });
    }

    private void loginAbsolute() {
        while (!login()) {
            RunningLog.run("[奥威亚录播]登录失败~~睡眠10秒。");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> T getRecordState() {
        IRecordStatus recordStatus = new Status(RecordStatus.IDLE);
        if (this.loginKey == null) {
            return (T) recordStatus;
        }
        RunningLog.run("[奥威亚录播]获取录播状态getRecordState");
        Map<String, String> params = new LinkedHashMap<>();
        params.put("action", "2");
        params.put("cmd", "1");
        params.put("key", loginKey == null ? "" : loginKey);
        params.put("p0", "144");
        String res = null;
        try {
            Response response = execute(params);
            res = new String(response.body().bytes());
            if (res.startsWith("|") || res.startsWith("-2")) {
                //无法获取数据或者返回-2开始的数据，都执行登录功能。
                RunningLog.run("查询时检查到需要重新登录!!");
                ThreadUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        loginAbsolute();
                    }
                });
                return null;
            }
            String[] caches = res.split("\\|");
            String value = caches[0];
            if ("0 -1".equals(value)) {
                recordStatus = new Status(RecordStatus.IDLE);
            } else {
                String[] temp = value.split(" ");
                int start = Integer.parseInt(temp[0]);
                int end = Integer.parseInt(temp[1]);
                if (start == 0 && end > 0) {
                    //录制中
                    //end为启动时间参数
                    recordStatus = new Status(RecordStatus.RECODING, null, " ");
                } else if (start > 0 && end > 0) {
                    //还是录制中  start为已录制时间，end为第二期启动时间
                    recordStatus = new Status(RecordStatus.RECODING, null, " ");
                } else if (start > 0 && end < 0) {
                    //暂停中
                    recordStatus = new Status(RecordStatus.PAUSE, null, " ");
                }
            }
        } catch (IOException networkException) {
            RunningLog.error(networkException);
            RecordResult loginResult = new RecordResult();
            loginResult.setSuccess(false);
            loginResult.setMessage("操作失败,设备离线");
            RunningLog.run("[奥威亚录播]获取录播状态失败 " + networkException.getMessage());
            this.recordStatus = new Status(RecordStatus.DISCONNECTED);
            EventBus.getDefault().post(loginResult);
        } catch (Exception e) {
            RunningLog.warn("[奥威亚录播]未支持的:" + res);
        }
        return (T) recordStatus;
    }

    IRecordPresentation presentation = null;

    private void onLogin() {
        refresh();
        if (presentation == null) {
            presentation = new AvaRecordPrensetation();
            EventBus.getDefault().postSticky(presentation);
        }
    }

    public boolean login() {
        RunningLog.run("[奥威亚录播]登录主机");
        boolean isLogin = false;
        String md5 = MD5Util.getMD5String(bean.getPassword());
        Map<String, String> params = new LinkedHashMap<>();
        params.put("action", "1");
        params.put("cmd", "25");
        params.put("username", bean.getUserName());
        params.put("passwd", md5);
        try {
            Response response = execute(params);
            String result = new String(response.body().bytes());
            RunningLog.run("接口调用回调:" + result);
            //if (this.avaRecordListener != null) this.avaRecordListener.deviceOperatingSuccess();
            if (result != null && !TextUtils.isEmpty(result)) {
                //1|UGNMczZ0NmZlNlVyRTBSVms0NTNvTlcyTnA2UDJqSGw|43|
                if (result.startsWith("-6")) {//需要等待完全唤醒

                } else {
                    String values[] = result.split("\\|");
                    String temp = null;
                    switch (values.length) {
                        case 2:
                        case 3:
                            temp = values[1];
                            loginKey = temp;
                            isLogin = true;
                            onLogin();
                            break;
                        case 4:
                        case 5:
                        case 6:
                            String state = values[3];
                            temp = values[1];
                            loginKey = temp;
                            if ("SLEEP".equals(state)) {
                                RunningLog.run("录播主机正在睡眠状态，等待唤醒");
                                Status recordStatus = new Status(RecordStatus.SLEEP);
                                EventBus.getDefault().postSticky(recordStatus);
                                /*if (avaRecordListener != null) {
                                    avaRecordListener.deviceOperatingSuccess();
                                    avaRecordListener.onSleep(recordStatus);
                                }*/
                                loginKey = values[1];
                            } else {
                                isLogin = true;
                                onLogin();
                                //登录后查询
                            }
                            break;
                        default:
                            RunningLog.run("登录key获取失败:" + result);
                            RecordResult loginResult = new RecordResult();
                            loginResult.setSuccess(false);
                            loginResult.setMessage("无法登录到录播主机");
                            this.recordStatus = new Status(RecordStatus.ERROR);
                            EventBus.getDefault().post(loginResult);
                            break;
                    }
                }
            } else {
                RecordResult loginResult = new RecordResult();
                loginResult.setSuccess(false);
                loginResult.setMessage("无法登录到录播主机");
                this.recordStatus = new Status(RecordStatus.ERROR);
                EventBus.getDefault().post(loginResult);
            }
        } catch (Exception e) {
            RunningLog.error(e);
            RecordResult loginResult = new RecordResult();
            loginResult.setSuccess(false);
            loginResult.setMessage("无法登录到录播主机");
            this.recordStatus = new Status(RecordStatus.DISCONNECTED);
            EventBus.getDefault().post(loginResult);
        }
        return isLogin;
    }

    protected String executeCommand(String action, String cmd, String key) {
        RunningLog.run("[奥威亚录播]执行命令 action=" + action + " ,cmd = " + cmd + " ,key=" + key);
        final Map<String, String> params = new LinkedHashMap<>();
        params.put("action", action);
        params.put("cmd", cmd);
        params.put("key", key);
        String res = null;
        try {
            Response response = execute(params);
            res = new String(response.body().bytes());
            RunningLog.run("接口调用回调:" + res);
        } catch (IOException networkException) {
            RunningLog.error(networkException);
            this.recordStatus = new Status(RecordStatus.DISCONNECTED);
            EventBus.getDefault().postSticky(recordStatus);
        }
        return res;
    }

    protected Response execute(Map<String, String> params) throws IOException {
        Response response = OkHttpUtil.get(path, params, (Map<String, String>) null);
        return response;
    }

    @Override
    public String getPlayUrl() {
        return this.playUrl;
    }

    @Override
    public boolean start(String uuid) {
        RunningLog.run("[奥威亚录播]start");
        boolean optResult = false;
        String result = executeCommand("1", "1001", loginKey);
        if (checkAuthorization(result)) {
            RunningLog.run("执行开始操作,返回值:" + result + ",成功重新登录,重新执行操作");
            optResult = start(uuid);
        } else if ("".equals(result) || "0".equals(result) || "0|".equals(result)) {

            optResult = true;
        } else optResult = false;
        postOptResult(optResult, "开始");
        return optResult;
    }

    private boolean checkAuthorization(String result) {
        if (result != null) {
            if (result.startsWith("-2")) {
                return login();
            } else return false;
        }
        return false;
    }

    @Override
    public boolean pause(String uuid) {
        RunningLog.run("[奥威亚录播]pause");
        String result = executeCommand("1", "1003", loginKey);
        boolean optResult = false;
        if (checkAuthorization(result)) {
            RunningLog.run("执行暂停操作,返回值:" + result + ",成功重新登录,重新执行操作");
            optResult = pause(uuid);
        } else if ("".equals(result) || "0".equals(result) || "0|".equals(result)) {
            optResult = true;
        } else optResult = false;
        postOptResult(optResult, "暂停");
        return optResult;
    }

    private void postOptResult(boolean optResult, String msg) {
        RecordResult result = new RecordResult();
        result.setMessage(msg + (optResult ? "操作成功" : "操作失败"));
        result.setSuccess(optResult);
        EventBus.getDefault().post(result);
    }

    @Override
    public boolean goon(String uuid) {
        RunningLog.run("[奥威亚录播]goon");
        boolean optResult = start(uuid);
        postOptResult(optResult, "继续");
        return optResult;
    }

    public boolean wakeup() {
        RunningLog.run("[奥威亚录播]准备唤醒录播主机");
        String action = "1";
        String cmd = "85";
        String result = executeCommand(action, cmd, loginKey);
        boolean optResult = false;
        if (checkAuthorization(result)) {
            RunningLog.run("执行唤醒操作,返回值:" + result + ",成功重新登录,重新执行操作");
            optResult = start(null);
        } else if ("".equals(result) || "0".equals(result) || "0|".equals(result)) {
            optResult = true;
        } else optResult = false;
        postOptResult(optResult, "唤醒");
        return optResult;
    }

    @Override
    public boolean stop(String uuid) {
        RunningLog.run("[奥威亚录播]stop");
        String result = executeCommand("1", "1002", loginKey);
        boolean optResult = false;
        if (checkAuthorization(result)) {
            RunningLog.run("执行停止操作,返回值:" + result + ",成功重新登录,重新执行操作");
            optResult = stop(uuid);
        } else if ("".equals(result) || "0".equals(result) || "0|".equals(result)) {
            optResult = true;
        } else optResult = false;
        postOptResult(optResult, "停止");
        return optResult;
    }

    @Override
    public void run() {
        int count = 0;
        do {
            count++;
            IRecordStatus recordStatus = getRecordState();
            if (recordStatus == null) {
                recordStatus = new Status(RecordStatus.ERROR);
            }
            this.recordStatus = recordStatus;
            EventBus.getDefault().postSticky(this.recordStatus);
            try {
                if (count <= 5) {
                    Thread.sleep(200);
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                RunningLog.error("录播中，线程异常！");
                RunningLog.error(e);
            }
        } while (this.recordStatus.getRecordStatus() == RecordStatus.RECODING || count < 5);
    }
}
