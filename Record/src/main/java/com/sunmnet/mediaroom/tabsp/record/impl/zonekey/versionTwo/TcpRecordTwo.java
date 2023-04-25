package com.sunmnet.mediaroom.tabsp.record.impl.zonekey.versionTwo;

import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpRecordTwo implements Runnable {
    String host;
    int port;
    boolean isConnected = false;
    ZonekeyRecordTwo.RecordTcpListener listener;
    // 工作线程
    Thread worker;

    public TcpRecordTwo(String host, int port, ZonekeyRecordTwo.RecordTcpListener listener) {
        this.host = host;
        this.port = port;
        this.listener = listener;
        getPlayUrl();
    }

    public void start() {
        if (worker == null) {
            worker = new Thread(this, "ZonekeyWork");
        }
        worker.start();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                doConnect();
                Thread.sleep(5000);
            }
        } catch (Exception connectException) {
            RunningLog.error(connectException);
            isConnected = false;
        } finally {
            isConnected = false;
        }
    }

    private void doConnect() throws IOException {
        isConnected = true;
        this.send("RecordCmd=QueryRAllInfo\n");
    }

    private void handleMessage(byte[] data) {
        String str = new String(data);
        RunningLog.commu("中庆主机", "智能面板", str);
        if (this.listener != null) this.listener.onReceiveMessage(str);
    }

    private boolean handle(String str) {
        if (str != null && str.indexOf("###ok") >= 0) {
            return true;
        } else return false;
    }

    private void send(final String str, final OutputStream os) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    os.write(str.getBytes());
                    os.flush();
                    RunningLog.run(str + "发送成功!!");
                } catch (IOException e) {
                    RunningLog.error(e);
                }

            }
        }).start();
    }

    public boolean send(String cmd) {
        try {
            Socket socket = new Socket(this.host, this.port);
            socket.getOutputStream().write(cmd.getBytes());
            socket.getOutputStream().flush();
            RunningLog.commu("智能面板", "中庆录播主机", cmd);
            byte[] data = new byte[10240];
            int length = -1;
            while ((length = socket.getInputStream().read(data)) != -1) {
                byte[] cache = new byte[length];
                System.arraycopy(data, 0, cache, 0, length);
                if (length >= 20) {
                    this.handleMessage(cache);
                    return true;
                } else {
                    String str = new String(cache);
                    return handle(str);
                }
            }
            return false;
        } catch (Exception e) {
            RunningLog.error(e);
            RunningLog.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取rtsp的url
     */
    private void getPlayUrl() {
        try {
            String cmd = "RecordCmd=QueryRtspUrls\n";
            Socket socket = new Socket(this.host, this.port);
            socket.getOutputStream().write(cmd.getBytes());
            socket.getOutputStream().flush();
            RunningLog.commu("智能面板", "中庆录播主机", cmd);
            byte[] data = new byte[10240];
            int length = -1;
            while ((length = socket.getInputStream().read(data)) != -1) {
                byte[] cache = new byte[length];
                System.arraycopy(data, 0, cache, 0, length);
                String res = new String(cache);
                int start = res.indexOf("{");
                res = res.substring(start);
                if (this.listener != null) {
                    this.listener.getPlayUrl(res);
                    this.listener.onConnected();
                }
            }
        } catch (Exception e) {
            RunningLog.error(e);
        }
    }
}
