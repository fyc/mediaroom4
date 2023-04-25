package com.sunmnet.mediaroom.common.bean;

import java.io.Serializable;

public class Result<T> implements Serializable {

    protected Boolean success;
    protected Integer errorCode;
    protected String msg;
    protected T obj;

    public Result(boolean success, int errorCode, String msg, T obj) {
        this.success = success;
        this.errorCode = errorCode;
        this.msg = msg;
        this.obj = obj;
    }

    public Result() {
    }

    public boolean isSuccess() {
        return success == null ? false : success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public static Result success() {
        return new Result(true, 0, null, null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
