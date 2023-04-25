package com.sunmnet.mediaroom.tabsp.record.impl.vanlon;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class VanlonRecord extends AbstractCirculateRecord {
    String path;
    String rid;
    String playUrl;

    public VanlonRecord(final RecordEntity entity) {
        super(entity);
        this.path = "http://" + entity.getHost() + "/recordEvent";
        this.rid = entity.getUserName();
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                String url = "http://" + bean.getHost() + "/getEquipment";
                Map<String, String> params = new HashMap<>();
                params.put("rid", rid);
                try {
                    Response response = OkHttpUtil.get(url, params, (Map<String, String>) null);
                    String res = response.body().string();
                    RunningLog.run("获取rtmp地址结果:" + res);
                    JsonObject jsonObject = parser.parse(res).getAsJsonObject();
                    int code = jsonObject.get("code").getAsInt();
                    if (code == 200) {
                        RunningLog.run("rtmp视频地址获取成功!");
                        JsonObject obj = jsonObject.get("data").getAsJsonObject();
                        String rtmpUrl = obj.get("extraneturl").getAsString();
                        playUrl = rtmpUrl;
                        //发送界面对象
                        EventBus.getDefault().postSticky(new IjkPresentation());
                    } else RunningLog.warn("无法获取rtmp地址");
                } catch (Exception e) {
                    RunningLog.error(e);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                VanlonRecord.this.run();
            }
        });
    }

    @Override
    public String getPlayUrl() {
        return playUrl;
    }

    JsonParser parser = new JsonParser();

    @Override
    public <T> T getRecordState() {
        String cmd = "state";
        String result = executeCommand(cmd);
        IRecordStatus recordStatus = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                JsonObject obj = parser.parse(result).getAsJsonObject();
                int code = obj.get("code").getAsInt();
                JsonObject object = obj.get("data").getAsJsonObject();
                boolean state = object.get("state").getAsBoolean();
                if (state) {
                    int length = object.get("timeLen").getAsInt();
                    recordStatus = new Status(RecordStatus.RECODING, null, length);
                } else recordStatus = new Status(RecordStatus.IDLE);
            } catch (Exception e) {
                RunningLog.error(e);

            }
        } else recordStatus = new Status(RecordStatus.IDLE);
        return (T) recordStatus;
    }

    @Override
    public boolean start(String uuid) {
        String cmd = "start";
        return executeCmd(cmd);
    }

    private String executeCommand(String cmd) {
        String result = null;
        Map<String, String> params = new HashMap<>();
        params.put("action", cmd);
        params.put("rid", this.rid);
        params.put("jobnumber", "10010");
        try {
            Response response = OkHttpUtil.get(this.path, params, (Map<String, String>) null);
            result = response.body().string();
            RunningLog.run("命令:" + cmd + "  执行结果:" + result);
        } catch (Exception e) {
            RunningLog.error(e);
        }
       /* try {
            Socket socket=new Socket("192.168.100.253",4321);
            do{
                OutputStream os=socket.getOutputStream();
                os.write(cmd.getBytes());
                os.flush();
                int length=-1;
                byte[] data=new byte[1024];
                InputStream inputStream=socket.getInputStream();
                while ((length=inputStream.read(data))!=-1){
                    byte[] truelyData=new byte[length];
                    System.arraycopy(data,0,truelyData,0,length);
                    result=new String(truelyData);
                    break;
                }
                socket.close();
            }while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return result;
    }

    private boolean executeCmd(String cmd) {
        boolean isSuccess = false;
        String res = executeCommand(cmd);
        if (res != null) {
            RunningLog.debug("command execute result=" + res);
            JsonObject obj = parser.parse(res).getAsJsonObject();
            int code = obj.get("code").getAsInt();
            JsonObject object = obj.get("data").getAsJsonObject();
            boolean optResult = object.get("success").getAsBoolean();
            isSuccess = optResult;
        }
        return isSuccess;
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
        String cmd = "stop";
        return executeCmd(cmd);
    }
}
