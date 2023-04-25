package com.sunmnet.mediaroom.device.listeners;


public interface DeviceStateNotifier {
    public String getListenerKey();
    public  void callUpdate();
    public void callUpdate(String code);
}
