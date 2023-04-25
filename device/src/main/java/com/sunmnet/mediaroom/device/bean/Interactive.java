package com.sunmnet.mediaroom.device.bean;

import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;
import com.sunmnet.mediaroom.devicebean.new2.components.param.VideoSourceParam;


public class Interactive extends AbstractDevice<BaseDeviceDto> {
    public Interactive(DeviceType deviceType, String deviceName, String deviceCode, VideoSourceParam values) {
        super(deviceType, deviceName, deviceCode);
        this.videoParams = values;
    }

    public Interactive(DeviceType deviceType, String deviceName, String deviceCode) {
        super(deviceType, deviceName, deviceCode);
    }

    public VideoSourceParam getVideoParams() {
        return videoParams;
    }

    public void setVideoParams(VideoSourceParam videoParams) {
        this.videoParams = videoParams;
    }

    VideoSourceParam videoParams;

    public static class InteractiveMode {
        String mode;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }

    InteractiveMode mode = new InteractiveMode();

    public InteractiveMode getMode() {
        return mode;
    }

    public void setMode(InteractiveMode mode) {
        this.mode = mode;
    }
}
