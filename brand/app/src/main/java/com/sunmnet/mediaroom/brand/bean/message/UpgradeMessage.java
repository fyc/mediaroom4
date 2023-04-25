package com.sunmnet.mediaroom.brand.bean.message;

public class UpgradeMessage {


    private String requestAction;
    private String md5;

    public String getRequestAction() {
        return requestAction;
    }

    public void setRequestAction(String requestAction) {
        this.requestAction = requestAction;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
