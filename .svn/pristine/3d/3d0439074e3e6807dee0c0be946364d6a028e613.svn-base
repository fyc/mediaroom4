package com.sunmnet.mediaroom.device.controll.service;

import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.devicebean.new2.device.ai.SuDto;

import java.util.List;
import java.util.Map;

/**
 * 设备操作接口
 */
public interface DeviceService<T, E extends DeviceType> extends IRelease {
    public List<T> getDevices();

    public List<T> getDevicesByDeviceType(E tag);

    public void setDeviceSettings(String name, Map<String, String> params);

    public void open(T t);

    public void open(E deviceTypeCode);

    public void close(E deviceTypeCode);

    public void close(T t);

    public void reverse(T t);

    public void setState(T t, String state);

    public void setState(T t, DeviceState state);

    void setSu(SuDto su);

    SuDto getSu();
}
