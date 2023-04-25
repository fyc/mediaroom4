package com.sunmnet.mediaroom.tabsp.record.impl.reach;


import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by dengzl on 2019/7/30.
 */
public class ReachClient extends AbstractExecutor implements IReceiver {
    public ReachClient(Socket socket, ReachEventListener listener) {
        this.socket = socket;
        this.listener = listener;
    }
    public ReachClient(Socket socket){
        this.socket=socket;
    }
    public String getAddress(){
        return socket.getLocalAddress().getHostAddress();
    }
    OutputStream outputStream;
    InputStream inputStream;

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public void stop() {
        super.stop();
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RunningLog.run("锐取客户端关闭!");
    }

    Socket socket;
    ReachEventListener listener;

    @Override
    protected boolean work() throws IOException {
        int length = -1;
        inputStream = this.socket.getInputStream();
        outputStream = this.socket.getOutputStream();
        byte[] datas = new byte[5];
        System.out.println("等待接收数据!!");
        while ((length = inputStream.read(datas)) != -1) {
            byte[] truelyData = new byte[length];
            System.arraycopy(datas, 0, truelyData, 0, length);
            this.onReceiv(truelyData);
        }
        RunningLog.run("锐取录播读取线程结束任务！");
        return false;
    }

    @Override
    public void onReceiv(String msg) {
       if (msg.equals("FF01060855")) {
            //登录
            //RunningLog.run("锐取登录");
            this.listener.onClientLogin(this);
        } else if (msg.equals("FF01060955")) {
            //心跳
           //RunningLog.run("锐取心跳");
            this.listener.onClientHeatbeat(this);
        }
        else if (msg.equals("FF01060555")) {
            //开始录播
          // RunningLog.run("锐取开始录播");
            listener.onRecording();
        }
        else if (msg.equals("FF01060655")) {
            //暂停录播
          // RunningLog.run("锐取暂停");
            listener.onRecordPausing();
        }
        else if (msg.equals("FF01060755")) {
            //结束录播
          // RunningLog.run("锐取录播完成");
            listener.onRecordFinished();
        }
    }

    @Override
    public void onReceiv(byte[] data) {
        this.onReceiv(Utils.bytesToHexString(data).toUpperCase());
    }
}
