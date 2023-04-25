package com.sunmnet.mediaroom.tabsp.ui.dispatch.fragmentDispatcher;

import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.R;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.record.RecordMaker;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractPlayer;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecord;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPlayer;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
/**
 * 录播界面
 * */
@Route(path = Constants.ROUTERPATH_CONTROLL_DEVICE_RECORD)
public class RecordDispatcher extends AbstractFragmentDispatcher {
    BaseActivity activity;
    IRecord record;
    IRecordPlayer player;
    IRecordPresentation recordPresentation;
    View contentView;

    @Override
    public int getLayout() {
        return R.layout.record_default_container;
    }

    @Subscribe(sticky = true)
    public void onPrensentationLoad(IRecordPresentation presentation) {
        RunningLog.run("收到IRecordPresentation");
        if (this.recordPresentation != presentation && this.recordPresentation != null) {
            this.recordPresentation.release();
        }
        this.recordPresentation = presentation;
        ((AbstractPlayer) player).setPlayerParam(record.getPlayUrl());
        if (contentView != null) {
            RunningLog.run("收到IRecordPresentation后初始化界面");
            presentation.init(contentView);
        }
    }

    @Override
    public void dispatch(View view) {
        try {
            record = RecordMaker.getRecord();
            player = RecordMaker.getRecordPlayer();
        } catch (Exception e) {
            RunningLog.error(e);
        }
        this.contentView = view;
        RunningLog.run("RecordDispatcher.onDispatch....");
        RunningLog.run("RecordDispatcher.onDispatch, record:" + record);
        RunningLog.run("RecordDispatcher.onDispatch, player:" + player);
    }

    @Override
    public void onReady() {
        RunningLog.run("RecordDispatcher.onReady....");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (player != null) {
            player.start();
        }
    }

    @Override
    public <E> void dispatch(View view, E e) {
        if (e instanceof BaseActivity) this.activity = (BaseActivity) e;
        this.dispatch(view);
    }

    @Override
    public void invisible() {
        RunningLog.run("RecordDispatcher.invisible()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (player != null){
            player.pause();
        }
    }

    @Override
    public void release() {
        RunningLog.run("RecordDispatcher.release()");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (recordPresentation != null){
            recordPresentation.release();
        }
        if (player != null) {
            player.release();
        }
    }
}
