package com.sunmnet.mediaroom.tabsp.record.impl.simple.services;

public interface SimpleRecordConnectListener {
    <T> void onMessage(T t);
    /**
     * 连接完成
     * */
    void onConnected();
    /***
     * 连接关闭
     */
    void onDisconnect();
    boolean isConnected();

    /**
     * 出现异常
     * @param exception
     */
    void onException(Exception exception);
}
