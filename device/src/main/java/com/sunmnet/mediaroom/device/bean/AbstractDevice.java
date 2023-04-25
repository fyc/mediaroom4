package com.sunmnet.mediaroom.device.bean;

import android.databinding.BaseObservable;
import android.view.View;


public abstract class AbstractDevice<T> extends BaseObservable implements IDevice<T> {
    public int getVisibility() {
        if (isProcessing()) return View.VISIBLE;
        else return View.GONE;
    }

    protected DeviceState state = DeviceState.ERROR;
    protected DeviceType deviceType;
    protected T property;
    String deviceName;
    String deviceCode;

    public AbstractDevice(DeviceType deviceType, String deviceName, String deviceCode) {
        this.deviceType = deviceType;
        this.deviceName = deviceName;
        this.deviceCode = deviceCode;
    }

    @Override
    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public AbstractDevice() {

    }

    @Override
    public boolean isOpened() {
        switch (this.state) {
            case OPENED:
            case OPENNONET:
            case CLOSING:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isClosed() {
        switch (this.state) {
            case CLOSED:
            case ERROR:
            case OPENNING:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isProcessing() {
        switch (this.state) {
            case CLOSING:
            case OPENNING:
                return true;
            default:
                return false;
        }
    }
    public void setState(String state){}
    @Override
    public String getDeviceName() {
        return this.deviceName;
    }

    @Override
    public String getDeviceCode() {
        return this.deviceCode;
    }

    @Override
    public String getDeviceTypeCode() {
        return deviceType.getDeviceType();
    }

    @Override
    public String getDeviceTypeCodeName() {
        return deviceType.getDeviceTypeName();
    }

    @Override
    public int getDrawable() {
        return deviceType.getDeviceDrawableByState(this.state);
    }

    @Override
    public DeviceState getDeviceState() {
        return this.state;
    }

    @Override
    public void setDeviceState(DeviceState deviceState) {
        this.state = deviceState;
    }

    @Override
    public void setProperty(T t) {
        this.property = t;
    }

    @Override
    public T getProperty() {
        return property;
    }
}
