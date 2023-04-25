package com.sunmnet.mediaroom.common.interfaces;

public interface IDataBindings {
    public <T> void binding(T t);
    public void onBindError(String message);
}
