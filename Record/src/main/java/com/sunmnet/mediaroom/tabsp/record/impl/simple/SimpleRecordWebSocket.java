package com.sunmnet.mediaroom.tabsp.record.impl.simple;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.impl.simple.services.SimpleRecordConnectListener;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class SimpleRecordWebSocket extends WebSocketClient {
    public SimpleRecordWebSocket(URI serverUri) {
        super(serverUri);
    }

    public SimpleRecordWebSocket(URI serverUri, SimpleRecordConnectListener listener) {
        super(serverUri);
        this.listener = listener;

    }

    /**
     * 异步线程连接
     */
    public void connectAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    SimpleRecordConnectListener listener;

    public void setConnectListener(SimpleRecordConnectListener listener) {
        this.listener = listener;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("已连接");
        if (listener != null) this.listener.onConnected();
    }

    JsonParser parser = new JsonParser();

    @Override
    public void onMessage(String message) {
        if (listener != null) {
            try {
                JsonObject object = parser.parse(message).getAsJsonObject();
                if (object != null && listener != null) {

                    if (object.get("data") != null)
                        listener.onMessage(object.get("data").getAsJsonObject());
                    if (object.get("result") != null) {
                        if (object.get("result").isJsonObject()) {
                            listener.onMessage(object.get("result").getAsJsonObject());
                        } else {
                            RunningLog.warn("数据无法转换成JsonObject，源数据--->" + object.toString());
                        }
                    }
                }
            } catch (Exception e) {
                RunningLog.error(e);
                listener.onException(e);
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        RunningLog.run("与录播主机连接终止！！");
        listener.onDisconnect();
    }

    @Override
    public void onError(Exception ex) {
        RunningLog.error(ex);
        RunningLog.error("连接异常出错,正在重连!!");
    }
}
