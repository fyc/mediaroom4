package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.sunmnet.mediaroom.tabsp.controll.service.UIDispatcher;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public abstract class AbstractFragmentDispatcher implements UIDispatcher<View>, IProvider {

    protected AtomicInteger lifeCycleState = new AtomicInteger(0);
    public static int RELEASE = 0;
    public static int INVISIBLE = 1;
    public static int READY = 2;
    protected Map<String, Object> dataMap;


    @Override
    public void dispatch(View view) {
        lifeCycleState.compareAndSet(RELEASE, INVISIBLE);
    }

    @Override
    public void dispatch(String type, String parent) {
        lifeCycleState.compareAndSet(RELEASE, INVISIBLE);
    }

    @Override
    public void dispatch(String type) {
        lifeCycleState.compareAndSet(RELEASE, INVISIBLE);
    }

    @Override
    public void invisible() {
        lifeCycleState.getAndSet(INVISIBLE);
    }

    @Override
    public void onReady() {
        lifeCycleState.getAndSet(READY);
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void release() {
        lifeCycleState.getAndSet(RELEASE);
    }

    @Override
    public void dispatch(String type, View view) {

    }

    @Override
    public <E> void dispatch(View view, E e) {

    }

    @Override
    public void setDataMap(Map<String, Object> map) {
        dataMap = map;
    }

    @Override
    public int getLayout() {
        return 0;
    }

}
