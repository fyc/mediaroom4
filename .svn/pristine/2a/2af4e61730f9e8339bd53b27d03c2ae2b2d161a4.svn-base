package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sunmnet.mediaroom.brand.bean.config.TimeSwitchConfig;
import com.sunmnet.mediaroom.brand.data.sharepref.BrandConfig;
import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TimeSwitchHelper {

    private TimeSwitchConfig config;

    public TimeSwitchHelper(Context context) {
        config = BrandConfig.getInstance().getTimeSwitchConfig(context);
    }

    public TimeSwitchHelper(TimeSwitchConfig config) {
        this.config = config;
    }

    private Date getDate(@Nullable Date baseDate, @NonNull String timeStr, @Nullable List<String> weeks) {
        if (baseDate == null)
            baseDate = new Date();
        String nowDateStr = DateUtil.formatDate(baseDate, "yyyy-MM-dd");
        Date result = DateUtil.parseDateStr(nowDateStr + " " + timeStr, "yyyy-MM-dd HH:mm");
        String weekNoResult = DateUtil.getWeekNo(result) + "";
        while (result.before(baseDate) || (weeks != null && !weeks.isEmpty() && !weeks.contains(weekNoResult))) {
            //如果时间早于基准时间，时间加一天
            //或者星期不在周期时间里，时间也要加一天
            result.setTime(result.getTime() + TimeUnit.DAYS.toMillis(1));
        }
        return result;
    }

    private Date getOnTime(@Nullable Date baseDate) {
        Date onTime = null;
        if (config != null) {
            try {
                //开机时间不受周期影响
                onTime = this.getDate(baseDate, config.getOnTime(), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return onTime;
    }


    private Date getOffTime(@Nullable Date baseDate) {
        Date offTime = null;
        if (config != null) {
            try {
                //关机时间需要受周期控制
                offTime = this.getDate(baseDate, config.getOffTime(), Arrays.asList(config.getPeriodWeeks().split(",")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return offTime;
    }

    /**
     * 设置定时开关机
     */
    public void setTimeSwitchConfig() {
        if (config == null || config.getState() == 0) {
            RunningLog.debug("没有设置定时开关机，取消定时开关机任务");
            BrandDevice.getInstance().cancelTimingSwitch();
            return;
        }
        Date now = new Date();
        Date off = getOffTime(now);
        Date on = getOnTime(now);
        if (on != null && off != null) {
            //如果开机时间在关机时间前，再以关机时间为标准获取开机时间
            if (on.before(off))
                on = getOnTime(new Date(off.getTime()));
            BrandDevice.getInstance().setTimingSwitch(off.getTime(), on.getTime());
        } else {
            RunningLog.debug("没有设置定时开关机时间，跳过设置定时开机任务");
        }
    }

}
