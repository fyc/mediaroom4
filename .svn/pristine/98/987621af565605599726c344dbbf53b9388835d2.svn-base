package com.sunmnet.mediaroom.brand.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunmnet.mediaroom.common.tools.RunningLog;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        RunningLog.run("BootReceiver接收到广播，action：" + action);
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            RunningLog.run("设备开机完毕，启动应用");
            Intent bootStartIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());//new Intent(context, MainActivity.class);
            bootStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(bootStartIntent);
            return;
        }
        if (Intent.ACTION_REBOOT.equals(action)) {
            RunningLog.run("设备正在重启...");
            return;
        }
        if (Intent.ACTION_SHUTDOWN.equals(action)) {
            RunningLog.run("设备正在关机...");
        }

    }
}
