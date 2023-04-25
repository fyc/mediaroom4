package com.sunmnet.mediaroom.device.bean.protocol;

import java.util.List;

/**
 * 串口控制
 */
public class SerialControl {

    /**
     * operateMethod : 1
     * operativesId : xxxxxxxx
     * devices : [{"deviceType":"INTERACTIVE","deviceCode":"","controlCmd":""}]
     */

    private int operateMethod;
    private String operativesId;
    private List<DevicesBean> devices;

    public int getOperateMethod() {
        return operateMethod;
    }

    public void setOperateMethod(int operateMethod) {
        this.operateMethod = operateMethod;
    }

    public String getOperativesId() {
        return operativesId;
    }

    public void setOperativesId(String operativesId) {
        this.operativesId = operativesId;
    }

    public List<DevicesBean> getDevices() {
        return devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public static class DevicesBean {
        /**
         * deviceType : INTERACTIVE
         * deviceCode :
         * controlCmd :
         */

        private String deviceType;
        private String deviceCode;
        private String controlCmd;

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

        public String getControlCmd() {
            return controlCmd;
        }

        public void setControlCmd(String controlCmd) {
            this.controlCmd = controlCmd;
        }
    }
}
