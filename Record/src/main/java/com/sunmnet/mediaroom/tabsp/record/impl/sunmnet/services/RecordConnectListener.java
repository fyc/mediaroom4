package com.sunmnet.mediaroom.tabsp.record.impl.sunmnet.services;

public interface RecordConnectListener {
    public <T> void onMessage(T t);
    /**
     * 连接完成
     * */
    public void onConnected();
    /***
     * 连接关闭
     */
    public void onDisconnect();
    public boolean isConnected();
}
