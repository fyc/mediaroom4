package com.sunmnet.mediaroom.tabsp.connector.version4;

import android.content.Context;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.connector.MQClient;
import com.sunmnet.mediaroom.tabsp.connector.service.ITransformer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

public class Version4Transformer implements ITransformer, MqttCallback {
    MQClient client;
    private static final String DEFAULT_CONTROLL_TOPIC = "/device/controll";
    private static final String DEFAULT_STATT_TOPIC = "/device/state";

    public Version4Transformer(Context context, String controllHost, int port) {
        EventBus.getDefault().register(this);
        String connectURI = "tcp://" + controllHost + ":" + port;
        Topic topic = new Topic(DEFAULT_STATT_TOPIC, 1);
        this.client = new MQClient(context, "clientID", connectURI, this, Arrays.asList(new Topic[]{topic}));
    }

    public Version4Transformer(Context context, String controllHost, int port, Topic topic) {
        this(context, controllHost, port, Arrays.asList(new Topic[]{topic}));
    }

    public Version4Transformer(Context context, String controllHost, int port, List<Topic> topics) {
        EventBus.getDefault().register(this);
        String connectURI = "tcp://" + controllHost + ":" + port;
        this.client = new MQClient(context, "clientID", connectURI, this, topics);
        this.client.start();
    }

    @Override
    public void release() {

    }

    @Override
    public boolean sendMsg(String message) {
        return sendMsg(DEFAULT_CONTROLL_TOPIC, message);
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        return this.client.sendMsg(topic, message);
    }

    @Override
    public void connectionLost(Throwable cause) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        cause.printStackTrace(pw);
        String msg = sw.toString();
        pw.close();
        RunningLog.error(msg);

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //处理对应标题
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        RunningLog.error("Version4Sender.mqcallnback.deliveryComplete");
    }
}
