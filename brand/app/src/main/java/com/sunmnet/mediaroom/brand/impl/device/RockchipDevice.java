package com.sunmnet.mediaroom.brand.impl.device;

import android.content.Intent;
import android.os.Build;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.util.JsonUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Rockchip芯片主板系统的设备
 */
public class RockchipDevice extends NormalDevice {

    static final String POWER_ACTION = "android.56iq.intent.action.setpoweronoff";

    @Override
    public boolean checkFeature() {
        String phoneBrand = Build.BRAND;
        String manufacturer = Build.MANUFACTURER;
        if (phoneBrand != null &&
                (phoneBrand.equalsIgnoreCase("rockchip") || phoneBrand.equalsIgnoreCase("softwinners"))) {
            return true;
        } else if (manufacturer != null && manufacturer.equalsIgnoreCase("rockchip")) {
            return true;
        }
        return false;
    }

    /**
     * 广播方式
     *
     * @param offTime
     * @param onTime
     */
    protected void broadcastSwitch(long offTime, long onTime) {
        Date off = new Date(offTime);
        Date on = new Date(onTime);
        StringBuilder builder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(offTime);
        int[] shutTimeArr = new int[]{
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        };
        calendar.setTimeInMillis(onTime);
        int[] onTimeArr = new int[]{
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        };
        builder.append("设置定时开关机，关机参数:");
        builder.append(JsonUtils.objectToJson(shutTimeArr));
        builder.append(",开机参数:").append(JsonUtils.objectToJson(onTimeArr));
        builder.append("关机时间：").append(DateUtil.formatDate(off, DateUtil.DEFAULT_FORMAT));
        builder.append(", 开机时间：").append(DateUtil.formatDate(on, DateUtil.DEFAULT_FORMAT));
        Intent intent = new Intent();
        intent.putExtra("timeoff", shutTimeArr);
        intent.putExtra("timeon", onTimeArr);
        intent.putExtra("enable", true);
        intent.setAction(POWER_ACTION);
        DeviceApp.getApp().sendBroadcast(intent);
    }

    protected void cancelBroadcastSwitch() {
        Intent intent = new Intent();
        intent.putExtra("enable", false);
        intent.setAction(POWER_ACTION);
        DeviceApp.getApp().sendBroadcast(intent);
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        broadcastSwitch(offTime, onTime);
        return true;
    }

    @Override
    public boolean cancelTimingSwitch() {
        cancelBroadcastSwitch();
        return true;
    }
}
