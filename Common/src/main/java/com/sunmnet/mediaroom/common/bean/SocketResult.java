package com.sunmnet.mediaroom.common.bean;

import java.io.Serializable;

public class SocketResult<T> implements Serializable {

    private boolean success;
    private boolean response;
    private T msg;
    private String errorMsg;

    public SocketResult() {
    }

    public SocketResult(boolean success, boolean response, T msg, String errorMsg) {
        this.success = success;
        this.response = response;
        this.msg = msg;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static <T> SocketResult<T> success(T msg) {
        return new SocketResult<T>(true, true, msg, null);
    }

    public static SocketResult success() {
        return new SocketResult(true, false, null, null);
    }

    public static SocketResult fail() {
        return new SocketResult(false, false, null, null);
    }

    public static <T> SocketResult<T> fail(T msg, String errorMsg) {
        return new SocketResult<>(false, true, msg, errorMsg);
    }

}
