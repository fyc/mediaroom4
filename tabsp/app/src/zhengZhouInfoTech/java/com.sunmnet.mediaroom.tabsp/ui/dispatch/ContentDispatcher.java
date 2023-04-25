package com.sunmnet.mediaroom.tabsp.ui.dispatch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.BaseDialogFragment;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.enums.SystemEventType;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.events.SystemEvent;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
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
import com.sunmnet.mediaroom.tabsp.ui.RateDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页布局
 */
@Route(path = Constants.ROUTERPATH_ACTIVITY_CONTENT)
public class ContentDispatcher extends AbstractActivityUiDispatcher {
    static final String TAG="RATE";
    @BindView(R.id.coverLayout)
    View coverLayout;
    @BindView(R.id.contentPart)
    View contentLayout;

    ScheduledFuture reloadSchedule;

    @Override
    public void dispatch(BaseActivity baseActivity) {
        super.dispatch(baseActivity);
        View view = LayoutInflater.from(baseActivity).inflate(getLayout(), null, false);
        baseActivity.setContentView(view);
        ButterKnife.bind(this, view);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.title_fragment, addFragment(Dispatcher.FRAGMENT_TITLE));
        transaction.add(R.id.menu_fragment, addFragment(Dispatcher.FRAGMENT_MENU));
        transaction.commit();
    }
    private void onScreenProtection(boolean onOrOff) {
        if (coverLayout != null) {
            if (onOrOff) {
                RunningLog.run("屏幕已唤醒，开始接收触摸事件！！");
                coverLayout.setVisibility(View.GONE);
                coverLayout.setClickable(false);
                coverLayout.setOnTouchListener(null);
            } else {
                RunningLog.run("屏幕关闭，丢弃触摸事件！！");
                coverLayout.setVisibility(View.VISIBLE);
                coverLayout.setClickable(true);
                coverLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        RunningLog.run("屏幕睡眠期间拒绝触摸事件下发!!");
                        return true;
                    }
                });
                coverLayout.bringToFront();
            }

        } else {
            RunningLog.run("没有触摸保护层，不处理屏幕事件！");
        }
    }

    private Fragment addFragment(String tag) {
        Fragment fragment = new DispatchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Dispatcher.PAGE_KEY, tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.tabsp_content_layout;
    }

    @Override
    public void release() {
        RunningLog.run(this + "ContentDispatcher.release");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    /**
     * 处理核心返回的状态
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
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
                    }, 5, TimeUnit.SECONDS);
                }
                break;
        }
    }
    /**
     * 返回到登录界面(锁定界面)
     * */
    private void backToLogin() {
        ARouter.getInstance().build(Constants.ROUTERPATH_ACTIVITY).withString(Dispatcher.PAGE_KEY, "").navigation();
        coverLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        },200);
        release();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onOnkeyDown(LoginEvent loginEvent) {
        switch (loginEvent.getEventType()) {
            case ON_CLASS:
                if (TabspApplication.getInstance().getConfig().getClassOnCountdown() > 0) {
                    RunningLog.run("ContentDispathcher 显示设备开启中弹框");
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
                if (TabspApplication.getInstance().getConfig().isRate()){
                    openRate();
                }else{
                    backToLogin();
                }
                break;
        }
    }
    private void openRate() {
        //打开设备评价
        Date date = new Date();
        CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(date, 0, "0");
        if (courseSchedule == null) {
            RunningLog.run("【设备评价】不在上课期间，查找5分钟前的上课信息");
            date.setTime(date.getTime() - TimeUnit.MINUTES.toMillis(5));
            courseSchedule = CourseHelper.getDefault().getCourseSchedule(date, 0, "0");
        }
        if (courseSchedule == null) {
            RunningLog.run("【设备评价】获取不到当前及5分钟前的上课信息，不打开设备评价窗口");
            backToLogin();
            return;
        } else if (TextUtils.isEmpty(courseSchedule.getCourseCode())) {
            RunningLog.run("【设备评价】无课程安排，不打开设备评价窗口");
            backToLogin();
            return;
        }
        BaseDialogFragment rateDialog = RateDialogFragment.newInstance(courseSchedule);
        rateDialog.setOnCancelListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                backToLogin();
            }
        });
        rateDialog.show(this.activity.getSupportFragmentManager(),TAG);

    }
    /**
     * 系统熄屏事件接收处理
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScreenEvent(SystemEvent<Boolean> screenEvent) {
        if (screenEvent.getEventType() == SystemEventType.SCREEN_EVENT) {
            Boolean value = screenEvent.getMessage();
            if (value != null) {
                onScreenProtection(value.booleanValue());
            }
        }
    }

    @Override
    public void onReady() {
        RunningLog.run(this + "ContentDispatcher.onReady");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
}
