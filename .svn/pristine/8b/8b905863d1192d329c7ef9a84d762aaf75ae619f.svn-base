package com.sunmnet.mediaroom.tabsp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunmnet.mediaroom.common.tools.RunningLog;

public class UpgradeReceiver extends BroadcastReceiver {
    private static final String PACKAGENAME = "com.sunmnet.mediaroom.tabsp";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        RunningLog.run("更新后接受到动作:" + action);
        if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
            RunningLog.run("启动程序");
            Intent intent1 = context.getPackageManager().getLaunchIntentForPackage(PACKAGENAME);
            context.startActivity(intent1);
        }
    }
}
