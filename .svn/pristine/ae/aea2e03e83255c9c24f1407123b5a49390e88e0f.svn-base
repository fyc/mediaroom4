package com.sunmnet.mediaroom.tabsp.record.impl.reach;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractRecord;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;

import org.greenrobot.eventbus.EventBus;

import java.net.Socket;
import java.net.SocketAddress;

public class ReachRecord extends AbstractRecord implements ReachEventListener {
    ReachClient client=null;
    Sender sender=null;
    ReachServer server;
    //RecordListener listener;
    String recordPath=null;
    public String getRecordPlayPath(){
        return this.recordPath;
    }
    public ReachRecord(RecordEntity entity) {
        super(entity);
        this.sender=new Sender();
        this.server=new ReachServer(this,30001);
        sender.start();
        this.server.start();
    }
    IRecordPresentation presentation;
    @Override
    public void onClientConnected(Socket socket) {
        if(client!=null){
            RunningLog.run("本地尚存录播主机连接，执行关闭");
            closeClient(client);
        }
        if (presentation==null){
            presentation=new IjkPresentation();
            EventBus.getDefault().postSticky(presentation);
        }
        RunningLog.run("录播主机连接到智能面板");
        client=new ReachClient(socket,this);
        client.start();
        String path="rtsp://";
        SocketAddress address= socket.getRemoteSocketAddress();
        boolean isExit=false;
        if (address!=null){
            String ip=address.toString();
            ip=ip.replace("/","");
            String[] ips=ip.split(":");
            if (ips.length==2){
                path+=ips[0];
                isExit=true;
            }else{
                RunningLog.error("获取到的IP地址不正确");
            }
        }
        if (!isExit){
            path+=socket.getInetAddress().getHostName();
        }
        path+=":554/stream0/low";
        RunningLog.run("录播服务器连接成功！！播放路径:"+path);
        this.recordPath=path;
    }
    private void closeClient(ReachClient client){
        try{
            client.stop();
            RunningLog.run("关闭录播主机旧连接..");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onClientHeatbeat(ReachClient client) {
        String response="FF07070955";
        sendCommand(response);
    }

    @Override
    public void onClientLogin(ReachClient socket) {
        String response="FF07070855";
        byte[] data=Utils.hexStringToByteArray(response);
        sender.send(client.getOutputStream(),data);
    }
    @Override
    public void onRecording() {
        Status recordingRecordStatus=new Status(RecordStatus.RECODING,null,"录制中");
        EventBus.getDefault().postSticky(recordingRecordStatus);
        /*if (recordStatus.getRecordStatus()!=recordingRecordStatus.getRecordStatus()&&this.listener!=null){
            recordStatus=recordingRecordStatus;
            this.listener.onRecording(recordStatus);
        }*/
    }

    @Override
    public void onRecordPausing() {
        Status cache=new Status(RecordStatus.PAUSE,null,"录播暂停");
        EventBus.getDefault().postSticky(cache);
    }

    @Override
    public void onRecordFinished() {
        Status cache=new Status(RecordStatus.IDLE,null,"空闲");
        EventBus.getDefault().postSticky(cache);
    }
    @Override
    public void setRecordListener(RecordListener listener) {
        /*this.listener=listener;
        if (listener==null) return;
        try{
            if (this.recordStatus!=null){
                if (this.recordStatus.getRecordStatus()==RecordStatus.RECODING) listener.onRecording(recordStatus);
                else if (this.recordStatus.getRecordStatus()==RecordStatus.PAUSE) listener.onRecordPaused(recordStatus);
                else if (this.recordStatus.getRecordStatus()==RecordStatus.IDLE)listener.onRecordIdle(recordStatus);
                else if (this.recordStatus.getRecordStatus()==RecordStatus.SAVED)
                    listener.onRecordSaved(recordStatus);
                else this.listener.onRecordIdle(new Status(RecordStatus.IDLE,null,"空闲"));

            }else{
                this.recordStatus=new Status(RecordStatus.IDLE,null,"空闲");
            } this.listener.onRecordIdle(this.recordStatus);
            //this.listener.onRecordIdle(this.recordStatus);
        }catch (Exception e){
            RunningLog.error("尝试在设置recordListener的时候初始化一个空闲状态失败！！");
            RunningLog.error(e);
        }*/
    }

    @Override
    public String getPlayUrl() {
        return this.recordPath;
    }
    @Override
    public boolean start(String uuid) {
        String order="FF07070555";
        sendCommand(order);
        return true;
    }

    @Override
    public boolean pause(String uuid) {
        String order="FF07070655";
        sendCommand(order);
        return true;
    }

    @Override
    public boolean goon(String uuid) {
        return this.start(uuid);
    }

    @Override
    public boolean stop(String uuid) {
        String order="FF07070755";
        sendCommand(order);
        return true;
    }

    private void sendCommand(String command){
        try{
            byte[] data= Utils.hexStringToByteArray(command);
            if (sender!=null)sender.send(client.getOutputStream(),data);
        }catch (Exception e){
            RunningLog.warn(e);
        }
    }
}
