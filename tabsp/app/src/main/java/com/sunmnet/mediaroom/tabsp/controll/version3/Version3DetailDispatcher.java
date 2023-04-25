package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.MenuEntity;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.version3.bean.MenuType;
import com.sunmnet.mediaroom.tabsp.ui.DispatchFragment;
import com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher.AbstractFragmentDispatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

@Route(path = Constants.ROUTERPATH_CONTROLL_V3_DETAIL)
public class Version3DetailDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    static final String MENU_KEY = "MENU";
    static final String DEVICE_TYPE_LIST = "DEVICE_TYPE_LIST";

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_detail_layout;
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e != null && e instanceof BaseActivity) {
            this.activity = (BaseActivity) e;
            check();
        }
    }

    List<Fragment> showingFragment = new ArrayList<>();

    private void onFragmentVisibilyChanged(boolean shown, Fragment fragment) {
        if (shown) {
            if (!showingFragment.contains(fragment)) {
                showingFragment.add(fragment);
            }
        } else {
            showingFragment.remove(fragment);
        }
    }

    private void check() {
        FragmentManager manager = this.activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(MENU_KEY);
        if (fragment == null) {
            fragment = addFragment(Constants.ROUTERPATH_CONTROLL_V3_MENU);
            transaction.add(R.id.menu_fragment, fragment);
        } else transaction.show(fragment);
        onFragmentVisibilyChanged(true, fragment);
        fragment = manager.findFragmentByTag(DEVICE_TYPE_LIST);
        if (fragment == null) {
            fragment = addFragment(Constants.ROUTERPATH_CONTROLL_V3_TYPE_LIST);
            transaction.add(R.id.detiveTypes, fragment);
        } else transaction.show(fragment);
        onFragmentVisibilyChanged(true, fragment);
        transaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onMenuChange(MenuEntity entity) {
        FragmentManager manager = this.activity.getSupportFragmentManager();
        String link = entity.getLink();
        if (manager.findFragmentByTag(link) == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = addFragment(link);
            transaction.replace(R.id.deviceDetails, fragment, link);
            onFragmentVisibilyChanged(true, fragment);
            transaction.commit();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 4)
    public void onMenuChange(MenuType entity) {
        EventBus.getDefault().removeStickyEvent(entity);
        FragmentManager manager = this.activity.getSupportFragmentManager();
        String link = entity.getDeviceType();
        if (manager.findFragmentByTag(link) == null) {
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment = addFragment(link);
            transaction.replace(R.id.deviceDetails, fragment, link);
            onFragmentVisibilyChanged(true, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onReady() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void release() {
        super.release();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private Fragment addFragment(String tag) {
        Fragment fragment = new DispatchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Dispatcher.PAGE_KEY, tag);
        fragment.setArguments(bundle);
        return fragment;
    }
}
