package com.sunmnet.mediaroom.brand.impl.device;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;

/**
 * 紫橙班牌
 */
public class InchDevice extends RockchipDevice {

    @Override
    public boolean checkFeature() {
        boolean exist = false;
        try {
            DeviceApp.getApp().getPackageManager().getPackageInfo("com.inch.inchupdate", 0);
            exist = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            //可以另外再参考这两个应用是否存在，尚未验证是紫橙独有
//            //com.lango.ota  com.lango.system.settings
//            ApplicationInfo applicationInfo = DeviceApp.getApp().getPackageManager().getApplicationInfo("com.lango.ota", 0);
//            Log.d("com.lango.ota", applicationInfo.toString());
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        return exist;
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        //紫橙班牌5.1版本可使用广播定时开关机
        //7.0版本只能使用休眠关机
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            sleepSwitch(offTime, onTime);
        } else {
            broadcastSwitch(offTime, onTime);
        }
        return true;
    }

    @Override
    public boolean cancelTimingSwitch() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            cancelSleepSwitch();
        } else {
            cancelBroadcastSwitch();
        }
        return true;
    }

    @Override
    public void shutdown() {
        //紫橙5.1班牌无法关机，只能重启
        super.shutdown();
    }
}
