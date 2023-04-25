package com.sunmnet.mediaroom.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.sunmnet.mediaroom.common.enums.SystemEventType;
import com.sunmnet.mediaroom.common.events.SystemEvent;
import com.sunmnet.mediaroom.common.interfaces.UIInterface;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SoftKeyBoardListener;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class BaseActivity extends AppCompatActivity implements UIInterface, SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
    protected Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SoftKeyBoardListener.setListener(this, this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(this.broadcastReceiver, filter);
    }
    private ScheduledFuture schedule;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {

                switch (action) {
                    case Intent.ACTION_SCREEN_OFF:
                        RunningLog.run("屏幕关闭");
                        SystemEvent<Boolean> systemEvent = new SystemEvent<>(SystemEventType.SCREEN_EVENT);
                        systemEvent.setMessage(false);
                        EventBus.getDefault().post(systemEvent);
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        RunningLog.run("屏幕开启");
                        if (schedule == null || schedule.isDone()) {
                            schedule = ThreadUtils.schedule(new Runnable() {
                                @Override
                                public void run() {
                                    SystemEvent systemEvent = new SystemEvent<>(SystemEventType.SCREEN_EVENT);
                                    systemEvent.setMessage(true);
                                    EventBus.getDefault().post(systemEvent);
                                    schedule = null;
                                }
                            }, 2, TimeUnit.SECONDS);
                        }
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.broadcastReceiver);
    }

    /**
     * 全屏，隐藏状态栏以及自带导航栏
     */
    protected void hideSystemUI() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    public void keyBoardShow(int height) {

    }

    @Override
    public void keyBoardHide(int height) {
        this.hideSystemUI();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.hideSystemUI();
    }

    @Override
    public void toastMessage(String message) {
        this.toastMessage(message, Toast.LENGTH_SHORT);
    }

    @Override
    public void toastMessage(final String message, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(getApplicationContext(), message, duration).show();
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toastMessage(message, duration);
                }
            });
        }
    }
}
