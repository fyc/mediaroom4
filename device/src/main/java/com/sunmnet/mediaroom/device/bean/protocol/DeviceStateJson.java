package com.sunmnet.mediaroom.device.bean.protocol;

public class DeviceStateJson {

    /**
     * deviceType : PROJECTOR
     * deviceCode : PROJECTOR_0002
     * deviceName : 投影仪1
     * state : 1
     * stateValue :
     * airConditionerState :
     * freshairState :
     * temperature :
     * humidity :
     * co2 :
     * pm25 :
     * tvoc :
     */

    private String deviceType;
    private String deviceCode;
    private String deviceName;
    private String state;
    private String stateValue;
    private String airConditionerState;
    private String freshairState;
    private String temperature;
    private String humidity;
    private String co2;
    private String pm25;
    private String tvoc;

    String aqi;
    String hourAqi;
    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getHourAqi() {
        return hourAqi;
    }

    public void setHourAqi(String hourAqi) {
        this.hourAqi = hourAqi;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }

    public String getAirConditionerState() {
        return airConditionerState;
    }

    public void setAirConditionerState(String airConditionerState) {
        this.airConditionerState = airConditionerState;
    }

    public String getFreshairState() {
        return freshairState;
    }

    public void setFreshairState(String freshairState) {
        this.freshairState = freshairState;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getTvoc() {
        return tvoc;
    }

    public void setTvoc(String tvoc) {
        this.tvoc = tvoc;
    }
}
