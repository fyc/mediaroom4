package com.sunmnet.mediaroom.tabsp.connector.version3;

import android.content.Context;
import android.content.Intent;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.connector.ConnectorTag;
import com.sunmnet.mediaroom.tabsp.connector.service.ITransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 匹配3.0通信协议的客户端
 */
public class Version3Transformer implements ITransformer, Runnable {
    ITransformer sender;
    ArrayBlockingQueue<String> sendQue = null;
    boolean isRunning = false;

    public Version3Transformer(Context context, String serverHost, int port) {
        EventBus.getDefault().register(this);
        Intent intent = new Intent(context, ServiceForVersion3.class);
        String connect = serverHost + ":" + port;
        intent.putExtra(ConnectorTag.CONNECT_LINK, connect);
        intent.putExtra(ConnectorTag.CONNECT_CLASS, "com.sunmnet.mediaroom.device.version3.Connector");
        context.startService(intent);
        sendQue = new ArrayBlockingQueue<>(20);
        isRunning = true;
        ThreadUtils.execute(this);
    }

    @Subscribe
    public void onConnect(ITransformer sender) {
        this.sender = sender;
    }

    @Override
    public void release() {
        this.isRunning = false;
        send("");
        EventBus.getDefault().unregister(this);
    }

    private void send(String msg) {
        try {
            this.sendQue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
    }

    @Override
    public boolean sendMsg(String message) {
        this.send(message);
        return true;
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        this.send(topic + message);
        return true;
    }

    @Override
    public void run() {
        String sendMsg = null;
        while (isRunning) {
            try {
                sendMsg = this.sendQue.take();
                if (sendMsg != null && !sendMsg.equals("") && this.sender != null)
                    this.sender.sendMsg(sendMsg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        RunningLog.run("发送线程结束!!");
    }
}
