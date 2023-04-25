package com.sunmnet.mediaroom.tabsp.connector.service;

public interface IServerReceiver<T> {
    public void onReceiv(String msg);

    public void onReceiv(byte[] msg);

    public void onReceiv(String msg, T t);

    public void onReceiv(byte[] msg, T t);
}
