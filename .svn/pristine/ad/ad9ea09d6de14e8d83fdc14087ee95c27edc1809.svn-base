package com.sunmnet.mediaroom.tabsp.connector;

import android.content.Context;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.connector.service.ITransformer;
import com.sunmnet.mediaroom.tabsp.connector.version4.Topic;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class MQClient implements ITransformer {
    String clientID, userName = "admin", password = "admin";
    String HOST;
    MqttCallback callback;
    List<Topic> subscribs;
    Context context;
    private MqttAndroidClient mqttAndroidClient;
    IMqttActionListener mqttActionListener;
    private MqttConnectOptions mMqttConnectOptions;
    boolean isConnectSuccess = false;

    public MQClient(Context context, String clientID, String host, int port, MqttCallback callback) {
        this(context, clientID, "tcp://" + host + ":" + port, callback);
    }

    public MQClient(Context context, String clientID, String connectURI, MqttCallback callback) {
        this(context, clientID, connectURI, callback, null);
    }

    public MQClient(Context context, String clientID, String connectURI, MqttCallback callback, List<Topic> subscribe) {
        this.subscribs = new ArrayList<>();
        this.clientID = clientID;
        this.callback = callback;
        this.subscribs.addAll(subscribe);
        this.HOST = connectURI;
        this.context = context;
    }

    public MQClient(Context context, String clientID, String connectURI, MqttCallback callback, List<Topic> subscribe, String userName, String password) {
        this.subscribs = new ArrayList<>();
        this.clientID = clientID;
        this.callback = callback;
        this.subscribs.addAll(subscribe);
        this.HOST = connectURI;
        this.context = context;
        this.userName = userName;
        this.password = password;
    }

    public void addSubscribe(Topic topic) throws MqttException {
        if (!this.subscribs.contains(topic)) {
            this.subscribs.add(topic);
            if (mqttAndroidClient != null) mqttAndroidClient.subscribe(topic.topic, topic.qosLevel);
        }
    }


    public void setMqttActionListener(IMqttActionListener mqttActionListener) {
        this.mqttActionListener = mqttActionListener;
    }

    //MQTT是否连接成功的监听
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            isConnectSuccess = true;
            try {
                for (Topic topic : subscribs
                ) {
                    mqttAndroidClient.subscribe(topic.topic, topic.qosLevel);
                }
            } catch (MqttException e) {
                RunningLog.error(e);
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            isConnectSuccess = false;
            RunningLog.error("歪，连接出错了！！");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (MQClient.this) {
                        RunningLog.run("启动重连线程，5秒后执行重连");
                        try {
                            MQClient.this.wait(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    RunningLog.run("开始重连！！");
                    reconnect();
                }
            }).start();
        }
    };

    public void start() {
        String serverURI = HOST; //服务器地址（协议+地址+端口号）
        if (this.mqttAndroidClient == null || this.mMqttConnectOptions == null) {
            mqttAndroidClient = new MqttAndroidClient(context, serverURI, this.clientID);
            mqttAndroidClient.setCallback(this.callback); //设置订阅消息的回调
            mMqttConnectOptions = new MqttConnectOptions();
            mMqttConnectOptions.setCleanSession(true); //设置是否清除缓存
            mMqttConnectOptions.setConnectionTimeout(20); //设置超时时间，单位：秒
            mMqttConnectOptions.setKeepAliveInterval(20); //设置心跳包发送间隔，单位：秒
            mMqttConnectOptions.setUserName(this.clientID); //设置用户名
            mMqttConnectOptions.setPassword(password.toCharArray()); //设置密码
            mMqttConnectOptions.setAutomaticReconnect(true);
            mMqttConnectOptions.setCleanSession(true);
        }
        reconnect();
    }

    public void stop() {
        try {
            if (mqttAndroidClient != null)
                mqttAndroidClient.disconnect();
        } catch (MqttException e) {
            RunningLog.error(e);
            e.printStackTrace();
        }
    }

    public void reconnect() {
        if (!mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.connect(mMqttConnectOptions, null, iMqttActionListener);
            } catch (MqttException e) {
                RunningLog.error(e);
                e.printStackTrace();
            }
        }
    }

    public boolean publish(String message) {
        return sendMsg(message);
    }

    @Override
    public void release() {

    }

    @Override
    public boolean sendMsg(String message) {
        String controll = "/conference/controll";
        return sendMsg(controll,message);
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        boolean isPublish = false;
        MqttMessage msg = new MqttMessage(message.getBytes());
        try {
            IMqttDeliveryToken token = mqttAndroidClient.publish(topic, msg, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    RunningLog.run("发送成功!!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    try{
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        exception.printStackTrace(pw);
                        String msg = sw.toString();
                        pw.close();
                        RunningLog.error(msg);
                    }catch (Exception e){
                        RunningLog.error(e);
                    }

                }
            });
            isPublish = token.isComplete();
            RunningLog.run("topic=" + topic + "  --> " + message);
        } catch (MqttException e) {
            RunningLog.error(e);
        } finally {
            RunningLog.run("发送结果:" + isPublish);
            return isPublish;
        }
    }
}
