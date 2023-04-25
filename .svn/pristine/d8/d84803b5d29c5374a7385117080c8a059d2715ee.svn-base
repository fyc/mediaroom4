package com.sunmnet.mediaroom.brand.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunmnet.mediaroom.brand.PlayableManager;
import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

public class PowerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BroadcastAction.ACTION_BRAND_POWER_ACQUIRE.equals(action)){
            RunningLog.debug("开启屏幕:"+ DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT));
            //CommandExecution.execCommand("echo sunmnetlocker > /sys/power/wake_unlock",true);
            ShellUtils.execCommand("echo on >/sys/power/state", true);
            ThreadUtils.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ShellUtils.execCommand("reboot", true);
                }
            });
            PlayableManager.getInstance().reload();
        } else if (BroadcastAction.ACTION_BRAND_POWER_RELEASE.equals(action)) {
            RunningLog.debug("熄灭屏幕:" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT));
            ShellUtils.execCommand("echo sunmnetlocker > /sys/power/wake_lock", true);
            ShellUtils.execCommand("echo mem >/sys/power/state", true);
            AlarmUtils.cancelAlarm(R.id.START_PLAY_NOTIFICATION, DeviceApp.getApp());
            AlarmUtils.cancelAlarm(R.id.START_PLAY_PROGRAM, DeviceApp.getApp());
            PlayableManager.getInstance().stopPlay();
        }
    }
}
