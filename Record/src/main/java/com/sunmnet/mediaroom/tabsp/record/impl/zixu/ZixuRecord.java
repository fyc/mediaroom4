package com.sunmnet.mediaroom.tabsp.record.impl.zixu;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractCirculateRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class ZixuRecord extends AbstractCirculateRecord {
    String path=null;
    String playPath=null;
    public ZixuRecord(RecordEntity recordBean){
        super(recordBean);
        this.path="http://"+recordBean.getHost()+"/sdk.html";
        this.playPath="rtsp://"+recordBean.getUserName()+"@"+recordBean.getPassword()+"/av0";
        //EventBus.getDefault().register(this);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                /**初始化根据获取到的录播状态来确定是否连接正常*/
                ZixuRecord.this.recordStatus = getRecordState();
                EventBus.getDefault().postSticky(ZixuRecord.this.recordStatus);
                if (recordStatus.getRecordStatus() != RecordStatus.DISCONNECTED || recordStatus.getRecordStatus() != RecordStatus.ERROR) {
                    EventBus.getDefault().post(new IjkPresentation());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /**这里重置状态。*/
                ZixuRecord.this.run();
            }
        });
    }


    @Override
    public String getPlayUrl() {
        return this.playPath;
    }

    @Override
    public <T> T getRecordState() {
        String cmd="#;&3#;&cmd#;&14#;&GetServerState#;&";
        IRecordStatus status = new Status(RecordStatus.DISCONNECTED);
        try{
            String result=executeCommand(cmd);
            RunningLog.run("getRecordState 获取到结果:"+result);
            if (!TextUtils.isEmpty(result)){
                String pattern = "&(([a-zA-Z]{1,}#;)|([a-zA-Z_]{1,}_[a-zA-Z_]{1,})#;)";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                // 现在创建 matcher 对象
                Matcher m = r.matcher(result);
                int count=2;
                while (m.find()&&count>0){
                    result=m.group(0);
                    count--;
                }
                if ("&stop#;".equals(result)){
                    status=new Status(RecordStatus.IDLE);
                }else if ("&pause#;".equals(result)){
                    status=new Status(RecordStatus.PAUSE,null," ");
                }else if ("&record_live#;".equals(result)){
                    status=new Status(RecordStatus.RECODING,null," ");
                }else if ("&record#;".equals(result)){
                    status=new Status(RecordStatus.RECODING,null," ");
                }else {
                    status=new Status(RecordStatus.IDLE);
                }
            }else status=new Status(RecordStatus.IDLE);
        }catch (Exception e){
            RunningLog.error(e);
        }
        return (T) status;
    }
    private String executeCommand(String cmd)  {
        cmd="type=iecmd"+cmd;
        String jsonStr="{\"data\":\""+cmd+"\"}";
        String result=null;
        try {
            //Response response=OkHttpUtil.post(this.path,params, headers);
            Response response= OkHttpUtil.postJson(this.path,jsonStr);
            result=response.body().string();
            RunningLog.run(cmd+" 请求返回值："+result);
        } catch (IOException e) {
            RunningLog.error(e);
        }
        return result;
        /*String result=null;
        try {
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
        }
        return result;*/
    }
    private boolean exectuteOpt(String cmd){
        boolean isSuccess=false;
        String result=executeCommand(cmd);
        if (result!=null){
            try{
                String pattern = "&\\d#;";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                // 现在创建 matcher 对象
                Matcher m = r.matcher(result);
                String res=result;
                while(m.find()){
                    res=m.group(0);
                }

                res=res.replace("&","");
                res=res.replace("#;","");
                isSuccess="1".equals(res);
                RunningLog.run(cmd+"处理结果:"+res);
            }catch (Exception e){
                RunningLog.error(e);
            }
        }
        return isSuccess;
    }
    @Override
    public boolean start(String uuid) {
        String cmd="#;&3#;&cmd#;&15#;&StartLiveRecord#;&";
        return exectuteOpt(cmd);
    }

    @Override
    public boolean pause(String uuid) {
        String cmd="#;&3#;&cmd#;&11#;&PauseRecord#;&";
        return exectuteOpt(cmd);
    }

    @Override
    public boolean goon(String uuid) {
        return start(uuid);
    }

    @Override
    public boolean stop(String uuid) {
        String cmd="#;&3#;&cmd#;&10#;&StopRecord#;&";
        return exectuteOpt(cmd);
    }

}
