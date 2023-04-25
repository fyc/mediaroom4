package com.sunmnet.mediaroom.brand.interfaces.device;

import android.app.Activity;

/**
 * 班牌设备类型，用于适配设备
 */
public interface IBrandDevice {

    void init();

    boolean checkFeature();

    void openAdb();

    void closeAdb();

    /**
     *
     * @param offTime 关机时间毫秒时间戳
     * @param onTime 开机时间毫秒时间戳
     * @return
     */
    boolean setTimingSwitch(long offTime, long onTime);

    boolean cancelTimingSwitch();

    boolean setSystemTime(long timeMillis);

    boolean installApk(String path);

    boolean hasRoot();

    void registerSwipeCard(Activity activity);

    void unregisterSwipeCard(Activity activity);

    void shutdown();

    void reboot();

}
