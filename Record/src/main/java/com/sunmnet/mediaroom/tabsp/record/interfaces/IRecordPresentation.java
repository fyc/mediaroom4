package com.sunmnet.mediaroom.tabsp.record.interfaces;

import android.view.View;

/**
 * 录播展示接口
 * */
public interface IRecordPresentation {
    public void init(View view);
    public boolean isInit();
    public void release();

    /**
     * 出现异常
     * @param exception
     */
    default void onException(Exception exception) {

    }
}
