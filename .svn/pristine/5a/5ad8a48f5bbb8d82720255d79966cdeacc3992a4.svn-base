package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.sunmnet.mediaroom.common.interfaces.Callback;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import java.io.File;

/**
 * 以命令行实现功能的封装工具类
 */
public class CommandUtil {

    public static boolean silentInstallApk(String path) {
        return silentInstallApk(path, true);
    }

    public static boolean silentInstallApk(String path, boolean deleteAfterInstall) {
        return silentInstallApk(path, null, false, deleteAfterInstall, null);
    }


    public static boolean silentInstallApk(String path, Context context, boolean startAfterInstall, boolean deleteAfterInstall, Callback callBack) {
        File file = new File(path);
        boolean result = false;

        if (file.exists()) {
            StringBuilder builder = new StringBuilder();
            builder.append(installApkCommand(file.getAbsolutePath()));
            if (deleteAfterInstall) {
                builder.append(" && ").append(removeFileCommand(file.getAbsolutePath()));
            }
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(builder.toString(), true);
            result = (commandResult.result == 0);
            if (!result) {
                RunningLog.error("apk安装失败:" + path);
                if (callBack != null) {
                    callBack.onFail();
                }
            } else if (startAfterInstall && context != null) {
                PackageInfo info = AppUtil.getPackageInfoByPath(path, context);
                String packageName = info.applicationInfo.packageName;
                callBack.onSuccess();
            }
        }
        return result;
    }


    private static String installApkCommand(String apkPath) {
        return "(pm install -r " + apkPath + ")";
    }

    private static String removeFileCommand(String apkPath) {
        return "(rm -f " + apkPath + ")";
    }

    private static String startAppCommand(String packageName, String activityName) {
        return "(am start -n " + packageName + "/" + activityName + ")";
    }
}
