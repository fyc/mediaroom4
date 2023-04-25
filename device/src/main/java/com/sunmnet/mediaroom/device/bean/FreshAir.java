package com.sunmnet.mediaroom.device.bean;

import com.sunmnet.mediaroom.util.bean.BaseDeviceDto;

public class FreshAir extends AbstractDevice<BaseDeviceDto> {
    public FreshAir(DeviceType deviceType, String deviceName, String deviceCode) {
        super(deviceType, deviceName, deviceCode);
    }

    public FreshAir(DeviceType deviceType, String deviceName) {
        super(deviceType, deviceName, null);
    }

    public static class Mode {
        String mode;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }

    Mode mode = new Mode();

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
    public void setMode(String state){

    }
}
