package com.sunmnet.mediaroom.tabsp.controll.version3.adapters;

import com.sunmnet.mediaroom.device.bean.DeviceType;

public class DeviceMenu {
    public DeviceMenu(DeviceType deviceType, String deviceTypeName) {
        this.deviceType = deviceType;
        this.deviceTypeName = deviceTypeName;
    }

    public DeviceMenu() {
    }

    public DeviceType deviceType;
    public String deviceTypeName;
}
