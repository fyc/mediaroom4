package com.sunmnet.mediaroom.brand.impl.device;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.broadcast.AlarmReceiver;
import com.sunmnet.mediaroom.brand.broadcast.BroadcastAction;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.brand.interfaces.device.IBrandDevice;
import com.sunmnet.mediaroom.common.tools.CmdUtil;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 其他未对接过的设备，按通常方式处理
 */
public class NormalDevice implements IBrandDevice {

    private boolean hasRoot;
    private SoftReference<Activity> registeredActivity;

    @Override
    public void init() {
        try {
            Process p = Runtime.getRuntime().exec("su");
            if (p != null) {
                p.destroy();
                hasRoot = true;
                ShellUtils.hasRoot = true;
            } else {
                hasRoot = false;
                ShellUtils.hasRoot = false;
            }

        } catch (Exception e) {
            hasRoot = false;
            ShellUtils.hasRoot = false;
        }
    }

    @Override
    public boolean checkFeature() {
        return true;
    }

    @Override
    public void openAdb() {
        CmdUtil.openAdb();
    }

    @Override
    public void closeAdb() {
        CmdUtil.closeAdb();
    }

    /**
     * 熄屏睡眠方式
     *
     * @param offTime
     * @param onTime
     */
    protected void sleepSwitch(long offTime, long onTime) {
        StringBuilder builder = new StringBuilder();
        builder.append("使用定时熄屏方式开关机，");
        Date now = new Date();
        //如果当前时间在熄屏时间之后，并且当前时间在亮屏之前，说明
        //当前时间内在熄屏时间内，设置3秒后熄屏
        Date off = new Date(offTime);
        Date on = new Date(onTime);
        if (now.after(off) && now.before(on)) {
            off.setTime(now.getTime() + TimeUnit.SECONDS.toMillis(3));
            builder.append("当前时间在熄屏时间之后，在亮屏之前，3秒后熄灭屏幕。");
        }
        if (now.before(off) && now.after(on)) {
            builder.append("，当前时间在亮屏时间之后，熄屏时间之前，10秒后设置亮屏。");
            on.setTime(now.getTime() + TimeUnit.SECONDS.toMillis(10));
        }
        builder.append("关机时间：").append(DateUtil.formatDate(off, DateUtil.DEFAULT_FORMAT));
        builder.append(", 开机时间：").append(DateUtil.formatDate(on, DateUtil.DEFAULT_FORMAT));
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
            intent.setAction(BroadcastAction.ACTION_BRAND_POWER_ACQUIRE);
        } else {
            intent = new Intent(BroadcastAction.ACTION_BRAND_POWER_ACQUIRE);
        }
        if (on.after(now)) {
            AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.POWER_LOCK_ACQUIRE, on);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent = new Intent(DeviceApp.getApp(), AlarmReceiver.class);
            intent.setAction(BroadcastAction.ACTION_BRAND_POWER_RELEASE);
        } else {
            intent = new Intent(BroadcastAction.ACTION_BRAND_POWER_RELEASE);
        }
        if (off.after(now)) {
            AlarmUtils.registerAlarm(DeviceApp.getApp(), intent, R.id.POWER_LOCK_RELEASE, off);
        }
    }

    protected void cancelSleepSwitch() {
        AlarmUtils.cancelAlarm(R.id.POWER_LOCK_ACQUIRE, DeviceApp.getApp());
        AlarmUtils.cancelAlarm(R.id.POWER_LOCK_RELEASE, DeviceApp.getApp());
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        sleepSwitch(offTime, onTime);
        return true;
    }

    @Override
    public boolean cancelTimingSwitch() {
        cancelSleepSwitch();
        return true;
    }

    @Override
    public boolean setSystemTime(long timeMillis) {
        int res = CmdUtil.setTime(DeviceApp.getApp(), timeMillis);
        return res == 0;
    }

    @Override
    public boolean installApk(String path) {
        return hasRoot() && AppUtil.slientInstall(path);
    }

    @Override
    public boolean hasRoot() {
        return hasRoot;
    }

    @Override
    public void registerSwipeCard(Activity activity) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter != null) {
            PendingIntent mPendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, activity.getClass()), PendingIntent.FLAG_UPDATE_CURRENT);
            mNfcAdapter.enableForegroundDispatch(activity, mPendingIntent, null, null);
            registeredActivity = new SoftReference<>(activity);
        }
    }

    @Override
    public void unregisterSwipeCard(Activity activity) {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(activity);
        if (mNfcAdapter != null) {
            if (registeredActivity != null && registeredActivity.get() != null) {
                mNfcAdapter.disableForegroundDispatch(registeredActivity.get());
                registeredActivity.clear();
                registeredActivity = null;
            } else {
                PendingIntent mPendingIntent = PendingIntent.getActivity(activity, 0, new Intent(activity, activity.getClass()), PendingIntent.FLAG_UPDATE_CURRENT);
                mNfcAdapter.enableForegroundDispatch(activity, mPendingIntent, null, null);
                mNfcAdapter.disableForegroundDispatch(activity);
            }
        }
    }

    @Override
    public void shutdown() {
        ShellUtils.execCommand("reboot -p");
    }

    @Override
    public void reboot() {
        ShellUtils.execCommand("reboot");
    }

}
