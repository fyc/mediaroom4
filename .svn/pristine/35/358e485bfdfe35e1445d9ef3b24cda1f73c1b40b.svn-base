package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.control.base.TextStyleControlProperty;
import com.sunmnet.mediaroom.brand.bean.event.RefreshAttendanceEvent;
import com.sunmnet.mediaroom.brand.bean.response.IntegerResponse;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 基础考勤组件
 */

public abstract class AbsAttendanceControl<P extends TextStyleControlProperty> extends PrefixSuffixTextControl<P> {

    protected Timer timer;
    protected boolean expired = false; // 是否超过签到时间段。false:在签到时间段里，true:已超过签到时间段。
    protected SingleTaskObserver<IntegerResponse> observer;

    protected Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!CourseHelper.getDefault().isLoaded()) {
                String ss = "没有课程表数据，无法获取考勤信息";
                RunningLog.run(ss);
                return;
            }
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), 0, "0", CourseConfig.PRE_START_TIME);
            if (courseSchedule == null)
                return;

            if (Math.abs(System.currentTimeMillis() - courseSchedule.getStartTime().getTime()) > TimeUnit.MINUTES.toMillis(10) &&
                Math.abs(System.currentTimeMillis() - courseSchedule.getEndTime().getTime()) > TimeUnit.MINUTES.toMillis(10)) {
                if (expired) {
                    return;
                }
                expired = true;
            }

            RunningLog.run("正在请求考勤信息");
            getRequestObservable(courseSchedule.getClassNo()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        }
    };

    // 主动刷新考勤数据runnable
    protected Runnable refreshAttendanceRunnable = new Runnable() {
        @Override
        public void run() {
            if (!CourseHelper.getDefault().isLoaded()) {
                String ss = "没有课程表数据，无法获取考勤信息";
                RunningLog.run(ss);
                return;
            }
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), 0, "0", CourseConfig.PRE_START_TIME);
            if (courseSchedule == null)
                return;

            RunningLog.run("正在请求考勤信息");
            getRequestObservable(courseSchedule.getClassNo()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        }
    };

    public AbsAttendanceControl(Context context) {
        super(context);
    }

    public AbsAttendanceControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public AbsAttendanceControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsAttendanceControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        observer = initResponseObserver();
    }

    @Override
    public void refreshControlData() {
        setText("0");
        expired = false;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onControlShow();
    }

    @Override
    public void onControlShow() {
        startTimer();
        registerEventBus();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onControlHide();
    }

    @Override
    public void onControlHide() {
        stopTimer();
        if (observer != null) {
            observer.dispose();
        }
        unregisterEventBus();
    }

    protected void startTimer() {
        if (timer != null)
            return;
        try {
            timer = new Timer();
            timer.schedule(new HandlerTimerTask(runnable), 0, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void stopTimer() {
        if (timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract Observable<IntegerResponse> getRequestObservable(String classNo);

    protected void setAttendanceNo(Integer num) {
        setText(TypeUtil.objToInt(num) + "");
    }

    protected SingleTaskObserver<IntegerResponse> initResponseObserver() {
        return new SingleTaskObserver<IntegerResponse>() {
            @Override
            public void onNext(IntegerResponse response) {
                if (response == null) {
                    RunningLog.run("考勤人数请求结果数据有误");
                    return;
                }
                RunningLog.run("考勤数据请求结果, success: " + response.isSuccess() + ", obj: " + response.getObj() + ", msg: " + response.getMsg());
                setAttendanceNo(response.getObj());
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    /**
     * 注册EventBus
     */
    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 注销EventBus
     */
    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 接收要刷新考勤的事件
     * 后面读卡器刷卡考勤方式也可以在这里接收, 然后去掉用定时器去获取考勤数据的代码
     * @param event
     */
    @Subscribe
    public void onRefreshAttendance(RefreshAttendanceEvent event) {
        RunningLog.run("收到刷新考勤数据事件");
        new Thread(refreshAttendanceRunnable).start();
    }
}
