package com.sunmnet.mediaroom.common.tools;


import android.content.Context;

import com.sunmnet.mediaroom.common.cmd.SetTime;
import com.sunmnet.mediaroom.util.FileUtils;

import java.io.File;
import java.util.Date;

public class CmdUtil {

    public static boolean HASROOT = true;

    public static int setTime(Context context, String timeStr, String format) {
        Date date = DateUtil.parseDateStr(timeStr, format);
        if (date != null)
            return setTime(context, date.getTime());
        else
            RunningLog.debug("无效的时间格式：" + timeStr + " ， " + format);
        return -1;
    }

    public static int setTime(Context context, long timeMillis) {
        String apkPath = context.getPackageCodePath();
        String cmd = "CLASSPATH=" + apkPath +
                " app_process /system/bin " + SetTime.class.getName() +
                " " + timeMillis;
        RunningLog.debug("execute cmdline:" + cmd);
        return ShellUtils.execCommand(cmd, HASROOT).result;
    }

    public static int setTimeScript(Context context, long timeMillis) {
        String apkPath = context.getPackageCodePath();
        String cmd = "CLASSPATH=" + apkPath +
                " app_process /system/bin " + SetTime.class.getName() +
                " " + timeMillis;
        RunningLog.debug("execute cmdline script:" + cmd);
        File file = new File(context.getCacheDir(), "settime.sh");
        FileUtils.writeFile(file.getAbsolutePath(), cmd);
        String[] cmds = new String[4];
        cmds[0] = "mv " + file.getAbsolutePath() + " /system/bin/settime.sh";
        cmds[1] = "chmod 744 /system/bin/settime.sh";
        cmds[2] = "/system/bin/settime.sh";
        cmds[3] = "rm /system/bin/settime.sh";
        return ShellUtils.execCommand(cmds, HASROOT).result;
    }

    public static void closeAdb() {
        try {
            ShellUtils.CommandResult result = ShellUtils.execCommand("stop adbd", HASROOT);
            if (result != null && result.result == 0) {
                RunningLog.info("远程调试关闭成功:" + result.successMsg);
            } else {
                RunningLog.info("远程调试关闭失败:" + (result != null ? result.errorMsg : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openAdb() {
        try {
            String[] cmdArr = {"setprop service.adb.tcp.port 5555", "stop adbd", "start adbd"};
            ShellUtils.CommandResult result = ShellUtils.execCommand(cmdArr, HASROOT);
            if (result != null && result.result == 0) {
                RunningLog.info("远程调试开启成功:" + result.successMsg);
            } else {
                RunningLog.info("远程调试开启失败:" + (result != null ? result.successMsg : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAdbOpen() {
        ShellUtils.CommandResult result = ShellUtils.execCommand("getprop | grep init.svc.adbd", HASROOT);
        if (result != null && result.successMsg != null) {
            if (result.successMsg.contains("running")) {
                return true;
            }
        }
        return false;
    }
}
