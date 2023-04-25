package com.sunmnet.mediaroom.device.bean;

public class OtherSetting {
    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }

    String volumn;

    public String getVgaMode() {
        return vgaMode;
    }

    public void setVgaMode(String vgaMode) {
        this.vgaMode = vgaMode;
    }

    String vgaMode;

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    String syncTime;
    int settingType = -1;

    public int getEventType() {
        return settingType;
    }

    public void setEventType(int eventType) {
        this.settingType = eventType;
    }
}
