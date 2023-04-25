package com.sunmnet.mediaroom.tabsp;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.litesuits.common.utils.ShellUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.ui.DispatcherActivity;

import java.util.List;


public class BootBroadcast extends BroadcastReceiver {
    private static final String PACKAGENAME = "com.sunmnet.mediaroom.tabsp";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        RunningLog.run("BootBroadcast接收到action:" + action);
        if (Intent.ACTION_BOOT_COMPLETED.equals(action) || Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            checkForeground(context);
        }
    }

    private void checkForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        boolean isForeground = false;
        int pid = -1;
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        if (processes == null)
            return;
        for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
            if (processInfo.processName.equals(context.getPackageName())) {
                pid = processInfo.pid;
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    isForeground = true;
                }
            }
        }
        if (!isForeground) {
            RunningLog.run("启动程序");
            Intent intent1 = context.getPackageManager().getLaunchIntentForPackage(PACKAGENAME);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent1);
            RunningLog.run("MainActivity当前不在前台运行");
        }
    }
}