package com.sunmnet.mediaroom.device.bean;

/**
 * Create by WangJincheng on 2021/2/23
 * 面板注册信息
 * 暂时用此类，后面班牌有共同属性才合并到RegisterInfo类
 */

public class TabspRegisterInfo extends RegisterInfo {
    // 设备编号
    String deviceCode;

    public String getDeviceCode() {
        return this.deviceCode;
    }

    public void setDeviceCode(String _deviceCode) {
        this.deviceCode = _deviceCode;
    }

    @Override
    public String toString() {
        return "TabspRegisterInfo{" +
                "deviceCode='" + deviceCode + '\'' +
                ", platformIp='" + platformIp + '\'' +
                ", platformPort='" + platformPort + '\'' +
                ", coreIp='" + coreIp + '\'' +
                ", corePort='" + corePort + '\'' +
                ", classroomCode='" + classroomCode + '\'' +
                ", classroomName='" + classroomName + '\'' +
                '}';
    }
}
