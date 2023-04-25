package com.sunmnet.mediaroom.tabsp.controll;

import android.content.Context;
import android.support.annotation.CallSuper;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.service.UIDispatcher;

import java.util.Map;

public abstract class AbstractActivityUiDispatcher implements UIDispatcher<BaseActivity>, IProvider {
    protected BaseActivity activity;
    long prevClickTime;
    int clickCount;

    protected void openEditMode() {
        long now = System.currentTimeMillis();
        if (now - prevClickTime < 300) {
            clickCount += 1;
        } else {
            clickCount = 1;
        }
        prevClickTime = now;
        if (clickCount >= 7) {
            clickCount = 0;
            RunningLog.run("打开设置界面");
            ARouter.getInstance().build(Constants.ROUTERPATH_ACTIVITY).withString(Dispatcher.PAGE_KEY, Constants.ROUTERPATH_SETTING).navigation();
        }
    }

    @Override
    public void dispatch(String type) {

    }

    @Override
    public void release() {

    }

    @Override
    public void dispatch(String type, String parent) {

    }

    @CallSuper
    @Override
    public void dispatch(BaseActivity baseActivity) {
        this.activity = baseActivity;
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void dispatch(String type, BaseActivity baseActivity) {

    }

    @Override
    public <E> void dispatch(BaseActivity baseActivity, E e) {

    }

    @Override
    public void invisible() {
    }

    @Override
    public void onReady() {
    }

    @Override
    public void setDataMap(Map map) {

    }
}
