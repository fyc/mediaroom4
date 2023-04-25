package com.sunmnet.mediaroom.device.bean;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.RunningLog;

public class Dimmer extends Device {
    public Dimmer(DeviceType deviceType, String deviceName, String deviceCode) {
        super(deviceType, deviceName, deviceCode);
    }

    int stateValue = 0;

    public int getStateValue() {
        return stateValue;
    }

    @Override
    public boolean isOpened() {
        return false;
        //return stateValue > 5 || (stateValue == 0 || stateValue == 3 || stateValue == 4);
    }

    @Override
    public boolean isClosed() {
        return stateValue == 5 || stateValue == 0 || stateValue == 2;
    }

    @Override
    public boolean isProcessing() {
        return stateValue == 2 || stateValue == 3 || this.state == DeviceState.OPENNING || this.state == DeviceState.CLOSING;
    }

    public void setState(String state, String stateValue) {
        this.state = DeviceState.ERROR; //DeviceState.fromString(state);
        if (!TextUtils.isEmpty(stateValue)) {
            try {
                this.stateValue = Integer.parseInt(stateValue);
            } catch (Exception e) {
                RunningLog.error(e);
            }

        }

    }
}
