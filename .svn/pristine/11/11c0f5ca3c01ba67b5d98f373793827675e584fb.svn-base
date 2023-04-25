package com.sunmnet.mediaroom.tabsp.bean;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Create by WangJinCheng on 2021/4/23
 * 请求扫码登录二维码结果
 */

public class LoginQrCodeRequestResult {

    @JsonProperty("success")
    private boolean success;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("obj")
    private ObjDTO obj;
    @JsonProperty("errorCode")
    private Object errorCode;

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

    public ObjDTO getObj() {
        return obj;
    }

    public void setObj(ObjDTO obj) {
        this.obj = obj;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public static class ObjDTO {
        @JsonProperty("uuid")
        private String uuid;
        @JsonProperty("type")
        private String type;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
