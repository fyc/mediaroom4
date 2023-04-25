package com.sunmnet.mediaroom.device.bean;

import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.devicebean.new2.BaseDeviceDto;

public class Airconditioner extends AbstractDevice<BaseDeviceDto> {
    public static class Setting {
        String tempeture;
        String mode;
        DeviceState deviceState = DeviceState.CLOSED;

        public String getTempeture() {
            return tempeture;
        }

        public void setTempeture(String tempeture) {
            this.tempeture = tempeture;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public DeviceState getDeviceState() {
            return deviceState;
        }

        public void setDeviceState(DeviceState deviceState) {
            this.deviceState = deviceState;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(this.getDeviceState().toString());
            builder.append(",");
            builder.append(mode);
            builder.append(",");
            builder.append(tempeture);
            return builder.toString();
        }
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    Setting setting;

    public Airconditioner(DeviceType deviceType, String deviceName, String deviceCode) {
        super(deviceType, deviceName, deviceCode);
    }

    public Airconditioner(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public void setSetting(String state) {
        if (this.setting == null) this.setting = new Setting();
        //Setting setting = new Setting();
        if (state.indexOf(',') >= 0) {
            String[] split = state.split(",");
            //0.2.25
            try {
                setting.setDeviceState(DeviceBuilder.getDeviceStateByState(split[0]));
                setting.setMode(split[1]);
                setting.setTempeture(split[2]);
            } catch (Exception e) {
                setting.setDeviceState(DeviceState.CLOSED);
                setting.setMode("1");
                setting.setTempeture("25");
            }
        } else {
            switch (state) {
                case "0":
                case "2":
                case "5":
                    setting.setTempeture("25");
                    setting.setMode("3");
                    setting.setDeviceState(DeviceState.CLOSED);
                    break;
                case "1":
                case "3":
                case "4":
                    setting.setTempeture("25");
                    setting.setMode("3");
                    setting.setDeviceState(DeviceState.OPENED);
                    break;

            }
        }
        //this.setSetting(setting);
    }

    public void setState(String state) {
        this.setSetting(state);
    }

    @Override
    public void setDeviceState(DeviceState deviceState) {
        super.setDeviceState(deviceState);
    }

    @Override
    public void setProperty(BaseDeviceDto deviceDto) {
        super.setProperty(deviceDto);
        //String state = deviceDto.getDeviceState();
       /* Setting state=new Setting();
        setting.set*/
        setSetting(DeviceState.ERROR.value);
    }

    public DeviceState getDeviceState() {
        DeviceState state = DeviceState.CUSTOM;
        if (this.property != null) {
            DeviceState cacheState = DeviceBuilder.getDeviceStateByState(DeviceState.ERROR.getStateValue());
            if (cacheState != null) state = cacheState;
        }
        return state;
    }

    @Override
    public String getDeviceName() {
        if (this.property != null) return this.property.getDeviceName();
        return null;
    }

    public String getControllString() {
        StringBuilder builder = new StringBuilder();
        builder.append(setting.deviceState.getStateValue());
        builder.append(",");
        builder.append(setting.mode);
        builder.append(",");
        builder.append(setting.tempeture);
        return builder.toString();
    }
}
