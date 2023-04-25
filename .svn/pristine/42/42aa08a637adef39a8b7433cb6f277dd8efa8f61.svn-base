package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.interfaces.Callback;

/**
 * Created by dengzl_pc on 2018/3/1.
 */

public abstract class AbstractCallback<T> implements Callback<T> {
    protected abstract void onCallbackSuccess(T t);
    @Override
    public void onSuccess(T callbackMsg) {
        this.onCallbackSuccess(callbackMsg);
    }
    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onFail(T callbackMsg) {

    }


}
