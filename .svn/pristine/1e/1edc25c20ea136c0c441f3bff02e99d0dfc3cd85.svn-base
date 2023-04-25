package com.sunmnet.mediaroom.tabsp.record.impl.hbe;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;
import com.sunmnet.mediaroom.tabsp.record.impl.hbe.bean.HBEStatusBean;
import com.sunmnet.mediaroom.util.JsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.XML;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HbeRecord extends AbstractCirculateRecord {

    public HbeRecord(RecordEntity entity) {
        super(entity);
        // EventBus.getDefault().register(this);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                /**初始化根据获取到的录播状态来确定是否连接正常*/
                HbeRecord.this.recordStatus = getRecordState();
                RunningLog.run("翰博尔，获取状态:" + HbeRecord.this.recordStatus.getRecordStatus());
                EventBus.getDefault().postSticky(HbeRecord.this.recordStatus);
                if (recordStatus.getRecordStatus() != RecordStatus.DISCONNECTED || recordStatus.getRecordStatus() != RecordStatus.ERROR) {
                    RunningLog.run("翰博尔，eventBus发送:IjkPresentation");
                    EventBus.getDefault().postSticky(new IjkPresentation());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**这里重置状态。*/
                HbeRecord.this.run();
            }
        });
    }

    private String createRequestPath(String req, String router, String params) {
        String res = req + "://";
        try {
            res += bean.getHost();
            res += "/";
            res += router;
            res += "?";
            res += (params == null ? "" : params);
        } catch (Exception e) {
            RunningLog.error(e);
        }
        return res;
    }

    private IRecordStatus readHbeStatusByBean(HBEStatusBean.InfoBean infoBean) {
        IRecordStatus status = new Status(RecordStatus.ERROR);
        if (infoBean != null) {
            HBEStatusBean.InfoBean.RecordStatusBean recordStatusBean = infoBean.getRecordStatus();
            if (recordStatusBean != null) {
                HBEStatusBean.InfoBean.RecordTimeBean timeBean = infoBean.getRecordTime();
                String time = timeBean != null ? timeBean.getValue() : "录制时间有误";
                switch (recordStatusBean.getValue()) {
                    case 0://IDLE
                        status = new Status(RecordStatus.IDLE);
                        break;
                    case 1://录制中
                        status = new Status(RecordStatus.RECODING, "", time);
                        break;
                    case 2://IDLE
                        status = new Status(RecordStatus.PAUSE, "", time);
                        break;
                    case 3://IDLE
                        break;
                }
            }
        }
        return status;
    }

    @Override
    public IRecordStatus getRecordState() {
        String path = createRequest("GetMainIndexMessage.do");
        String result = requestForStatus(path);
        IRecordStatus status = new Status(RecordStatus.DISCONNECTED);
        try {
            String jsonStr = XML.toJSONObject(result).toString();
            RunningLog.run("获取到状态:" + jsonStr);
            HBEStatusBean statusBean = (HBEStatusBean) JsonUtils.jsonToBean(jsonStr, HBEStatusBean.class);
            status = readHbeStatusByBean(statusBean.getInfo());
        } catch (JSONException e) {
            RunningLog.error(e);
        } catch (Exception e) {
            RunningLog.warn(e);
        }
        return status;
    }

    private String request(String url) {
        String result = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        RecordResult recordResult = new RecordResult();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                result = body;
                RunningLog.run("翰博尔，请求结果:" + result);
                if ("0".equals(result)) {
                    recordResult.setSuccess(true);
                } else {
                    recordResult.setMessage("控制失败!!");
                    recordResult.setSuccess(false);
                }
                //RunningLog.run("完成请求,url:"+url+",结果:"+body);
            } else {
                RunningLog.error("url:" + url + "请求失败!!");
                recordResult.setSuccess(false);
                recordResult.setMessage("url:" + url + "请求失败!!!");
            }

        } catch (IOException e) {
            RunningLog.error("url:" + url + "请求失败!!!");
            RunningLog.error(e);
            recordResult.setSuccess(false);
            recordResult.setMessage("url:" + url + "请求失败!!!");
        }
        EventBus.getDefault().post(recordResult);
        //result=testRest();
        return result;
    }

    /**
     * 请求录播状态
     * @param url
     * @return
     */
    private String requestForStatus(String url) {
        String result = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        RecordResult recordResult = new RecordResult();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                result = body;
                recordResult.setSuccess(true);
                //RunningLog.run("完成请求,url:"+url+",结果:"+body);
            } else {
                RunningLog.error("url:" + url + "请求失败!!");
                recordResult.setSuccess(false);
                recordResult.setMessage("url:" + url + "请求失败!!!");
            }

        } catch (IOException e) {
            RunningLog.error("url:" + url + "请求失败!!!");
            RunningLog.error(e);
            recordResult.setSuccess(false);
            recordResult.setMessage("url:" + url + "请求失败!!!");
        }
        if (!recordResult.isSuccess()) {
            EventBus.getDefault().post(recordResult);
        }
        //result=testRest();
        return result;
    }

    private String createRequest(String router) {
        String params = "";
        Random random = new Random();
        params += random.nextInt();
        params += "=%d";
        String path = createRequestPath("http", router, params);
        return path;
    }

    @Override
    public boolean start(String uuid) {
        String path = createRequest("StartRecord.do");
        path = request(path);
        return "0".equals(path);
    }

    @Override
    public boolean pause(String uuid) {
        String path = createRequest("PauseRecord.do");
        path = request(path);
        return "0".equals(path);
    }

    @Override
    public boolean goon(String uuid) {
        String path = createRequest("StartRecord.do");
        path = request(path);
        return "0".equals(path);
    }

    @Override
    public boolean stop(String uuid) {
        String path = createRequest("StopRecord.do");
        path = request(path);
        return "0".equals(path);
    }

    @Override
    public String getPlayUrl() {
        return "rtsp://" + bean.getHost() + ":" + bean.getPort() + "/ch0_1";
    }
}
