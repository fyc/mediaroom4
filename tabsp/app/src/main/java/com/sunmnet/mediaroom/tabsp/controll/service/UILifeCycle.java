package com.sunmnet.mediaroom.tabsp.controll.service;
/**
 * 界面生命周期
 * */
public interface UILifeCycle {
    /**
     * 界面不可视时的回调
     * */
    public void invisible();
    /**
     * 界面可见
     * */
    public void onReady();
    /**
     * 资源释放
     * */
    public void release();
}
