package com.sunmnet.mediaroom.tabsp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sunmnet.mediaroom.common.BaseFragment;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.service.UIDispatcher;
/***
 * Fragment 界面
 * */
public class DispatchFragment extends BaseFragment {
    /**
     * 界面分发器
     * */
    UIDispatcher uiDispatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(Dispatcher.PAGE_KEY) != null) {
            String routerKey = bundle.getString(Dispatcher.PAGE_KEY);
            String routerPath= TabspApplication.getInstance().getDispatcher().getPageByType(routerKey);
            RunningLog.run("DispatchFragment-->" + routerPath);
            this.uiDispatcher = (UIDispatcher) ARouter.getInstance().build(routerPath).navigation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (uiDispatcher != null) uiDispatcher.onReady();
    }
    @Override
    public void onPause() {
        super.onPause();
        RunningLog.run(this.uiDispatcher+"-->onPause()");
        if (uiDispatcher != null) uiDispatcher.release();
    }
   /* @Override
    public void onStop() {
        super.onStop();
        RunningLog.run(this.uiDispatcher+"-->onStop()");
        if (uiDispatcher != null) uiDispatcher.release();
    }
/*
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (uiDispatcher != null) uiDispatcher.release();
    }*/

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (uiDispatcher != null) uiDispatcher.invisible();
        } else {
            if (uiDispatcher != null) uiDispatcher.onReady();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = null;
        if (uiDispatcher == null) {
            contentView = inflater.inflate(R.layout.tabsp_unknow_layout, container, false);
        } else {
            contentView = inflater.inflate(uiDispatcher.getLayout(), container, false);
            RunningLog.run(uiDispatcher+"--->正在处理！！");
            uiDispatcher.dispatch(contentView, getActivity());
        }
        return contentView;
    }
}
