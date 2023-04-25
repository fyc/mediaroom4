package com.sunmnet.mediaroom.tabsp.controll.version3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.device.bean.LoginEvent;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.DialogInfo;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.AbstractActivityUiDispatcher;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.eventbus.events.LockEvent;
import com.sunmnet.mediaroom.tabsp.ui.DispatchFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Route(path = Constants.ROUTERPATH_ACTIVITY_VERSION3_CONTENT)
public class Version3ActivityDispatcher extends AbstractActivityUiDispatcher {
    Fragment contentFragment, detailFragment;
    ScheduledFuture reloadSchedule;
    public static class DetailChanger {
        public int detailState;
    }

    @Override
    public void dispatch(BaseActivity baseActivity) {
        super.dispatch(baseActivity);
        baseActivity.setContentView(getLayout());
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.title_fragment, addFragment(Dispatcher.FRAGMENT_TITLE));
        contentFragment = addFragment(Constants.ROUTERPATH_CONTROLL_V3_CONTENT);
        transaction.replace(R.id.content_fragment, contentFragment);
        transaction.show(contentFragment);
        transaction.commit();
    }

    private Fragment getDetailFragment(FragmentTransaction transaction) {
        if (detailFragment == null) {
            detailFragment = addFragment(Constants.ROUTERPATH_CONTROLL_V3_DETAIL);
        }
        return detailFragment;
    }

    @Subscribe
    public void onSwitch(DetailChanger changer) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if (changer.detailState == 1) {
            transaction.replace(R.id.content_fragment, getDetailFragment(transaction));
        } else {
            transaction.replace(R.id.content_fragment, contentFragment);
        }
        transaction.commit();
    }

    private Fragment addFragment(String tag) {
        Fragment fragment = new DispatchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Dispatcher.PAGE_KEY, tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void release() {
        RunningLog.run(this + "Version3ActivityDispatcher.release");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onLock(LockEvent lockEvent) {
        EventType type = lockEvent.getEventType();
        switch (type) {
            case EVENT_LOCK:
                RunningLog.run("ContentDispathcher 处理Event_Lock事件");
                backToLogin();
                break;
            case ACTIVITY_RELOAD:
                RunningLog.run("ContentDispathcher 处理Event_Lock事件");
                backToLogin();
                if (reloadSchedule == null || reloadSchedule.isDone()) {
                    reloadSchedule = ThreadUtils.schedule(new Runnable() {
                        @Override
                        public void run() {
                            Controller.getInstance().query(CommuTag.TABSP_GET_STATE, "");
                            reloadSchedule = null;
                        }
                    }, 10, TimeUnit.SECONDS);
                }
                break;
        }
    }

    private void backToLogin() {
        ARouter.getInstance().build(Constants.ROUTERPATH_ACTIVITY).withString(Dispatcher.PAGE_KEY, "").navigation();
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        }, 200);
        release();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onOnkeyDown(LoginEvent loginEvent) {
        switch (loginEvent.getEventType()) {
            case ON_CLASS:
                RunningLog.run("ContentDispathcher 处理ON_CLASS事件");
                if (TabspApplication.getInstance().getConfig().getClassOnCountdown() > 0) {
                    DialogInfo dialogInfo = new DialogInfo(DialogInfo.DialogType.DEVICE_OPENNING,
                            null, TabspApplication.getInstance().getConfig().getClassOnCountdown());
                    Event<DialogInfo, EventType> dialogEvent = new Event<>();
                    dialogEvent.setMessage(dialogInfo);
                    dialogEvent.setEventType(EventType.DIALOG_EVENT);
                    EventBus.getDefault().post(dialogEvent);
                }
                EventBus.getDefault().removeStickyEvent(loginEvent);
                break;
            case ON_CLASSOVER:
                RunningLog.run("ContentDispathcher 处理ON_CLASSOVER事件");
                backToLogin();
                break;
        }
    }

    @Override
    public void onReady() {
        RunningLog.run(this + "ContentDispatcher.onReady");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_version3_main_acitivity_layout;
    }
}
