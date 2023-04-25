package com.sunmnet.mediaroom.tabsp.interfaces;

public interface DialogListener {
    public <T> void onConfirmed(T t);
    public void onCancel();
    public void onTick(int count);
}
