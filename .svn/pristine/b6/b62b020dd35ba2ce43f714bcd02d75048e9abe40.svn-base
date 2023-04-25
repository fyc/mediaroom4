package com.sunmnet.mediaroom.device.bean;

import java.util.List;

public class ControlBean {

    /**
     * operateMethod : 1
     * operativesId : xxxxxxxx
     * operatePa : 1,1
     * operateDevices : [{"deviceType":"SOUND","control":"1","deviceCode":"","componentCode":""},{"deviceType":"AIRCONDITIONER","control":"1,1,25,1","deviceCode":"AIRCONDITIONER_001","componentCode":""},{"deviceType":"FRESHAIR","control":"1,1,1","deviceCode":"FRESHAIR_0002","componentCode":""}]
     */

    private int operateMethod;
    private String operativesId;
    private String operatePa;
    private List<OperateDevicesBean> operateDevices;

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

    public String getOperatePa() {
        return operatePa;
    }

    public void setOperatePa(String operatePa) {
        this.operatePa = operatePa;
    }

    public List<OperateDevicesBean> getOperateDevices() {
        return operateDevices;
    }

    public void setOperateDevices(List<OperateDevicesBean> operateDevices) {
        this.operateDevices = operateDevices;
    }

    public static class OperateDevicesBean {
        /**
         * deviceType : SOUND
         * control : 1
         * deviceCode :
         * componentCode :
         */

        private String deviceType;
        private String control;
        private String deviceCode;
        private String componentCode;

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getComponentCode() {
            return componentCode;
        }

        public void setComponentCode(String componentCode) {
            this.componentCode = componentCode;
        }
    }
}
