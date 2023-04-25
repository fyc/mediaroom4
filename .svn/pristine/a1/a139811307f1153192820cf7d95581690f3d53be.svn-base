package com.sunmnet.mediaroom.brand.common.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.config.DeviceConfig;


public abstract class DeviceApp extends BaseApplication {

    private static DeviceApp deviceApp;
    protected DeviceInfo device;

    @Override
    protected void init() {
        deviceApp = this;
        super.init();
    }

    @Override
    protected void initDeviceInfo() {
        super.initDeviceInfo();
        device = DeviceConfig.getInstance().getDeviceInfo(this);
    }

    /**
     * 是否已经注册
     *
     * @return
     */
    public boolean isRegistered() {
        return (device != null && !TextUtils.isEmpty(device.getPlatformIp()));
    }

    public static DeviceApp getApp() {
        return deviceApp;
    }

    public DeviceInfo getDevice() {
        return device;
    }

    public String getPlIp() {
        return device == null ? null : device.getPlatformIp();
    }


    public String getClassroomCode() {
        return device == null ? null : device.getClassroomCode();
    }


    public String getClassroomName() {
        return device == null ? null : device.getClassroomName();
    }


    public int getPlPort() {
        return device == null ? null : device.getPlatformPort();
    }


    public String getCoreIp() {
        return device == null ? null : device.getCoreIp();
    }

    public int getCorePort() {
        return device == null ? null : device.getCorePort();
    }


    /**
     * @return http服务器地址
     */
    public String getPlServerAddr() {
        return device == null ? null : "http://" + getPlIp() + ":" + getPlPort();
    }

    /**
     * @return http服务器地址, 末尾带斜杠
     */
    public String getPlServerAddr_Slash() {
        return device == null ? null : "http://" + getPlIp() + ":" + getPlPort() + "/";
    }

    public void saveSharedPreferencesValue(String key, String value) {
        SharedPreferences preferences = getSharedPreferences("deviceAppData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedPreferencesValue(String key) {
        SharedPreferences preferences = getSharedPreferences("deviceAppData", Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

}
