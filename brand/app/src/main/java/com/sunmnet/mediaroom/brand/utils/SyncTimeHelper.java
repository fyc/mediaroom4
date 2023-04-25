package com.sunmnet.mediaroom.brand.utils;

import android.os.SystemClock;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.brand.impl.device.HikDmbDevice;
import com.sunmnet.mediaroom.common.tools.CmdUtil;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import java.util.Date;

public class SyncTimeHelper {

    /**
     * 同步服务器时间
     * @param dateStr format yyyy-MM-dd HH:mm:ss;
     */
    public boolean syncTime(String dateStr) {
        ShellUtils.CommandResult result = ShellUtils.execCommand("getprop persist.sys.timezone", false);
        RunningLog.debug("当前时区-persist.sys.timezone：" + result.successMsg);
//        if (!StringUtils.isEmpty(result.successMsg)) {
            String format = "yyyy-MM-dd HH:mm:ss";
            Date date = DateUtil.parseDateStr(dateStr, format);
            //AlarmUtils.cancelAllAlarms(DeviceApp.getApp());
        int res = updateTime(date);
        if (res == 0) {
            RunningLog.debug("时间修改成功");
            return true;
        } else {
            RunningLog.debug("时间修改失败，结果代码：" + res);
            return false;
        }
//        }
    }

    public boolean syncTime(long timeStamp) {
        ShellUtils.CommandResult result = ShellUtils.execCommand("getprop persist.sys.timezone", false);
        RunningLog.debug("当前时区-persist.sys.timezone：" + result.successMsg);
        Date date = new Date(timeStamp);
        int res = updateTime(date);
        if (res == 0) {
            RunningLog.debug("时间修改成功");
            return true;
        } else {
            RunningLog.debug("时间修改失败，结果代码：" + res);
            return false;
        }
    }

    /**
     * 修改系统时间
     */
    public int updateTime(Date date) {
        ShellUtils.CommandResult dateresult= ShellUtils.execCommand("date \"+%Y-%m-%d %H:%M:%S\"", false);
        RunningLog.debug("时间设置前-硬件时间：" + dateresult.successMsg);

        RunningLog.debug("时间设置前-软件时间：" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT));

        RunningLog.debug("设置时间戳数据：" + date.getTime());
//        String command = "date -set " + date.getTime();
//        CommandExecution.CommandResult result = CommandExecution.execCommand(command, true);
        int result = 0;
        if (AppUtil.isSystemApp(DeviceApp.getApp())) {
            RunningLog.debug("当前应用为系统应用，直接修改系统时间");
            boolean success = SystemClock.setCurrentTimeMillis(date.getTime());
            if (!success) {
                result = 1;
            }
        } else {
            if (BrandDevice.getInstance().getType() instanceof HikDmbDevice) {
                result = CmdUtil.setTimeScript(DeviceApp.getApp(), date.getTime());
            } else {
                result = CmdUtil.setTime(DeviceApp.getApp(), date.getTime());
            }
        }

        ShellUtils.CommandResult dateresult2= ShellUtils.execCommand("date \"+%Y-%m-%d %H:%M:%S\"", false);
        RunningLog.debug("时间设置后-硬件时间：" + dateresult2.successMsg);

        RunningLog.debug("时间设置后-软件时间：" +  DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT));

        return result;
    }
}
