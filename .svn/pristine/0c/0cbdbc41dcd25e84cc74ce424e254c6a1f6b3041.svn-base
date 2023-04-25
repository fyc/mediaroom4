package com.sunmnet.mediaroom.tabsp.record.impl.zonekey.versionOne;

import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpRecord implements Runnable {
    String host;
    int port;
    Socket socket;
    boolean running = false;
    boolean isConnected = false;
    ZonekeyRecord.RecordTcpListener listener;
    public TcpRecord(String host, int port, ZonekeyRecord.RecordTcpListener listener) {
        this.host = host;
        this.port = port;
        this.listener=listener;
    }

    public void start() {
        if (!running) {
            running = true;
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (!isConnected) {
                    doConnect();
                }
                read();
                Thread.sleep(5000);
            } catch (IOException connectException) {
                RunningLog.error(connectException);
                isConnected = false;
                socket = null;
            }
            catch (Exception e){
                RunningLog.error(e);
            }finally {
                isConnected=false;
                socket=null;
            }
        }
    }

    private void doConnect() throws IOException {
        this.socket = new Socket(host, port);
        isConnected = true;
        this.send("RecordCmd=QueryRAllInfo\n");
    }

    private void read() throws IOException {
        if (isConnected && socket != null) {
            byte[] data = new byte[10240];
            int length = -1;
            while ((length = socket.getInputStream().read(data)) != -1) {
                byte[] cache = new byte[length];
                System.arraycopy(data, 0, cache, 0, length);
                handleMessage(cache);
            }
        } else RunningLog.warn("未连接到中庆主机，不读取数据");
    }

    private void handleMessage(byte[] data) {
        String str = new String(data);
        RunningLog.commu("中庆主机", "智能面板", str);
        if (this.listener!=null) this.listener.onReceivMessage(str);
    }
    private boolean handle(String str){
        if (str!=null&&str.indexOf("###ok")>=0){
            return true;
        }else return false;
    }
    private void send(final String str, final OutputStream os) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    os.write(str.getBytes());
                    os.flush();
                    RunningLog.run(str+"发送成功!!");
                } catch (IOException e) {
                   RunningLog.error(e);
                }

            }
        }).start();
    }
    public boolean send(String cmd) {
        try{
            Socket socket=new Socket(this.host,this.port);
            socket.getOutputStream().write(cmd.getBytes());
            socket.getOutputStream().flush();
            /*send(cmd,socket.getOutputStream());
            Thread.sleep(500);*/
            RunningLog.commu("智能面板","中庆录播主机",cmd);
            byte[] data = new byte[10240];
            int length = -1;
            while ((length=socket.getInputStream().read(data))!=-1){
                byte[] cache = new byte[length];
                System.arraycopy(data, 0, cache, 0, length);
                if (length>=20){
                    this.handleMessage(cache);
                    return true;
                }else {
                    String str=new String(cache);
                    return handle(str);
                }
            }
            return false;
            /*while ((length = socket.getInputStream().read(data)) != -1) {
                byte[] cache = new byte[length];
                System.arraycopy(data, 0, cache, 0, length);
                String str=new String(cache);

                break;
            }*/
        }catch (Exception e){
            RunningLog.error(e);
            return false;
        }
        /*try {
            if (socket != null && isConnected) {
                socket.getOutputStream().write(cmd.getBytes());
                socket.getOutputStream().flush();
                RunningLog.commu("智能面板","中庆录播主机",cmd);
            }else RunningLog.warn("与主机断开连接，无法发送命令"+cmd);
        } catch (Exception e) {
            RunningLog.error(e);
            try {
                socket.close();
            } catch (Exception ee) {
                RunningLog.error(ee);
            }
            isConnected = false;
            socket = null;
        }*/
    }
}
