package com.sunmnet.mediaroom.tabsp.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Create by WangJincheng on 2021-08-16
 * 获取二维码登录链接请求结果
 */

public class QrLoginLinkResult {

    /**
     * success : true
     * msg : 操作成功
     * obj : http://172.16.24.52/backstage/sys-svr/api/terminalQrLogin?deviceCode=werewdrew&classroomCode=wetwerrt&target=3
     * errorCode : null
     */

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("obj")
    private String obj;
    @JsonProperty("errorCode")
    private int errorCode;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
