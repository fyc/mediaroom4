package com.sunmnet.mediaroom.device.bean;


import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;

public class Device extends AbstractDevice<BaseDeviceDto>  {

    public Device(DeviceType deviceType, String deviceName, String deviceCode) {
        super(deviceType, deviceName, deviceCode);
    }

    @Override
    public void setDeviceState(DeviceState deviceState) {
        super.setDeviceState(deviceState);
        notifyChange();
    }
}
