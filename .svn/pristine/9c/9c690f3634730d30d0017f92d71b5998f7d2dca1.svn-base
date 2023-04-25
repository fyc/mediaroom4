package com.sunmnet.mediaroom.brand.control.base;

import android.os.Handler;
import android.os.Looper;

import java.util.TimerTask;

/**
 * 可对UI线程进行操作的TimerTask
 */
public class HandlerTimerTask extends TimerTask {

    private final static Handler handler = new Handler(Looper.myLooper());
    private Runnable runnable;

    public HandlerTimerTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            if (runnable != null) {
                handler.post(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
