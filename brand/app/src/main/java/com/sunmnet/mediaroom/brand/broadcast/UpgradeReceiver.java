package com.sunmnet.mediaroom.brand.broadcast;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.UpgradeInfo;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.data.sharepref.UpgradePref;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.List;

public class UpgradeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        if (Intent.ACTION_PACKAGE_REPLACED.equals(action)
                && data != null && TextUtils.equals(data.getSchemeSpecificPart(), DeviceApp.getApp().getPackageName())) {
            RunningLog.debug("程序更新成功");
            UpgradeInfo info = UpgradePref.getInstance().getUpgradeInfo(context);
            if (info != null) {
                info.setUpgradeSuccess(true);
                UpgradePref.getInstance().saveUpgradeInfo(context, info);
            }

            boolean isRunning = false;
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runningTaskInfoList = activityManager.getRunningTasks(Integer.MAX_VALUE);
            for (ActivityManager.RunningTaskInfo taskInfo : runningTaskInfoList) {
                if (taskInfo.topActivity.getPackageName().equals(context.getPackageName())) {
                    isRunning = true;
                    break;
                }
            }
            if (!isRunning) {
                Intent bootStartIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());//new Intent(context, MainActivity.class);
                bootStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(bootStartIntent);
            }


        }
    }

}
