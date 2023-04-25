package com.sunmnet.mediaroom.tabsp.controll.version3.bean;

import com.sunmnet.mediaroom.device.bean.DeviceType;

public class MenuType extends DeviceType {
    public MenuType(String deviceType, String deviceTypeName) {
        super(deviceType, deviceTypeName);
    }
    public MenuType(DeviceType deviceType, String deviceTypeName) {
        super(deviceType.getDeviceType(), deviceTypeName);
    }
}
