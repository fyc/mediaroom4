package com.sunmnet.mediaroom.brand.bean.response;

import java.util.List;

public class FailureTypeResponse {

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
        private String id;
        private String faultCode;
        private String faultClassification;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFaultCode() {
            return faultCode;
        }

        public void setFaultCode(String faultCode) {
            this.faultCode = faultCode;
        }

        public String getFaultClassification() {
            return faultClassification;
        }

        public void setFaultClassification(String faultClassification) {
            this.faultClassification = faultClassification;
        }

        @Override
        public String toString() {
            return faultClassification;
        }
    }
}
