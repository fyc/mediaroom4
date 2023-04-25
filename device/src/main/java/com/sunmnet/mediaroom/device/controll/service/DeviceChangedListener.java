package com.sunmnet.mediaroom.device.controll.service;
/**
 * 设备状态更新监听
 * */
public interface DeviceChangedListener {
    /**
     * 设备状态更新
     * @param deviceCode 设备编号
     * @[param deviceTypeCode 设备类型
     * */
    public void onDeviceChaned(String deviceTypeCode,String deviceCode);
}
