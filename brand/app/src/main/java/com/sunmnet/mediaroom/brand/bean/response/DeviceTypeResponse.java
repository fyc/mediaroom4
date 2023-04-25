package com.sunmnet.mediaroom.brand.bean.response;

import java.util.List;

public class DeviceTypeResponse {

    private boolean state;
    private List<Datas> datas;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<Datas> getDatas() {
        return datas;
    }

    public void setDatas(List<Datas> datas) {
        this.datas = datas;
    }

    public static class Datas {
        private String deviceTypeCode;
        private String controlType;
        private String deviceTypeName;

        public String getDeviceTypeCode() {
            return deviceTypeCode;
        }

        public void setDeviceTypeCode(String deviceTypeCode) {
            this.deviceTypeCode = deviceTypeCode;
        }

        public String getControlType() {
            return controlType;
        }

        public void setControlType(String controlType) {
            this.controlType = controlType;
        }

        public String getDeviceTypeName() {
            return deviceTypeName;
        }

        public void setDeviceTypeName(String deviceTypeName) {
            this.deviceTypeName = deviceTypeName;
        }

        @Override
        public String toString() {
            return deviceTypeName;
        }
    }
}
