package com.sunmnet.mediaroom.tabsp.connector.service;
/**
 * 消息传送器
 * */
public interface ITransformer {
    public void release();
    /**
     * 发送消息
     * */
    public boolean sendMsg(String message);
    /**
     * 发送带主题的消息，3.0下可表示为协议头，4.0下表示为topic
     * */
    public boolean sendMsg(String topic, String message);

}
