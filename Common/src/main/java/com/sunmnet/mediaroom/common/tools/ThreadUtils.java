package com.sunmnet.mediaroom.common.tools;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
    private final static Handler mHandler = new Handler(Looper.getMainLooper());
    private final static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    private final static ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(0);

    public static void execute(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public static <V> Future<V> execute(Callable<V> callable) {
        return threadPool.submit(callable);
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static void runOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != mHandler.getLooper().getThread()) {
            mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    public static void runOnUiThread(Runnable runnable, long delay) {
        mHandler.postDelayed(runnable, delay);
    }

    public static void runOnSubThread(Runnable runnable) {
        if (Thread.currentThread() == mHandler.getLooper().getThread()) {
            execute(runnable);
        } else {
            runnable.run();
        }
    }

    public static ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
        return scheduledThreadPool.schedule(command, delay, unit);
    }

    public static <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return scheduledThreadPool.schedule(callable, delay, unit);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return scheduledThreadPool.scheduleAtFixedRate(command, initialDelay, period, unit);
    }
}
