package com.sunmnet.mediaroom.tabsp.impl;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.impl.DefaultDownloadListener;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.VerifySign;
import com.sunmnet.mediaroom.tabsp.BuildConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class TabspInstaller extends DefaultDownloadListener {
    private static final String TAG = "智能面板软件升级";
    int downloadProgress = 0;

    @Override
    public void onDownloading(int progress) {
        if (this.downloadProgress == 0 && progress > 0) {
            ToastUtil.show(BaseApplication.getInstance(), "文件下载中.....", false);
            RunningLog.run("文件下载中.....");
        }
        downloadProgress += progress;
    }

    @Override
    public void onDownloadFailed(Exception e) {
        RunningLog.error(e);
        ToastUtil.show(BaseApplication.getInstance(), "文件下载失败！！停止更新！！", false);
        onDownloadFailed("文件下载失败！！停止更新！！");
    }

    @Override
    public void onDownloadFailed(String msg) {
        downloadProgress = 0;
        Event<String, EventType> event = new Event<>();
        event.setEventType(EventType.UPGRADE_FAILED);
        event.setMessage(msg);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onDownloadSuccess(File file) {
        downloadProgress = 0;
        ToastUtil.show(BaseApplication.getInstance(), "文件下载完成", false);
        RunningLog.run("文件下载完成");
        VerifySign sign = new VerifySign(BaseApplication.getInstance(), file);
        Event<String, EventType> event = new Event<>();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // TODO
            RunningLog.run("当前系统版本为：" + Build.VERSION.SDK_INT + "   调用另外一个安装！！");
            ToastUtil.show(BaseApplication.getInstance(), "开始安装", false);
            boolean res = silenceInstallApk(file.getAbsolutePath());
            if (res) {
                event.setEventType(EventType.UPGRADE_SUCCESS);
                event.setMessage("升级成功！！");
                ToastUtil.show(BaseApplication.getInstance(), "升级成功！！", false);
            } else {
                event.setEventType(EventType.UPGRADE_FAILED);
                event.setMessage("升级失败！！系统版本为" + Build.VERSION.SDK_INT);
                ToastUtil.show(BaseApplication.getInstance(), "升级失败！！系统版本为" + Build.VERSION.SDK_INT, false);

            }
            EventBus.getDefault().post(event);
        } else {
            RunningLog.run("系统版本低于5.1....使用shell脚本进行静默安装");
            if (sign.check()) {
                installSystemApk(BaseApplication.getInstance(), file, "com.sunmnet.mediaroom.tabsp",
                        "com.sunmnet.mediaroom.tabsp.ui.DispathcerActivity");
            } else {
                ToastUtil.show(BaseApplication.getInstance(), "文件签名异常，请从正规渠道获取安装文件!!", false);
                event.setEventType(EventType.UPGRADE_FAILED);
                event.setMessage("文件签名异常，请从正规渠道获取安装文件!!");
                EventBus.getDefault().post(event);
            }
        }
        file.delete();
    }

    public static boolean silenceInstallApk(String apkPath) {
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        try {
//            process = new ProcessBuilder("pm", "install", "-i", "com.xh.hwcontroller", "-r", apkPath).start();
            process = new ProcessBuilder("pm", "install", "-i", BuildConfig.APPLICATION_ID, "-r", apkPath).start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        //如果含有“success”单词则认为安装成功
        return successMsg.toString().equalsIgnoreCase("success");
    }

    public ShellUtils.CommandResult installSilent(Context context, String filePath, String pmParams) {
        ShellUtils.CommandResult commandResult = null;

        File file = new File(filePath);
        if (filePath == null || filePath.length() == 0 || file == null || !file.exists() || !file.isFile()) {
            commandResult = new ShellUtils.CommandResult();
            commandResult.result = 127;
            commandResult.successMsg = "";
            commandResult.errorMsg = filePath + " 不是合法的apk文件！";
        }
        /**
         * if context is system app, don't need root permission, but should add <uses-permission
         * android:name="android.permission.INSTALL_PACKAGES" /> in mainfest
         **/
        StringBuilder command = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ")
                .append(pmParams == null ? "" : pmParams)
                .append(" ")
                .append(filePath.replace(" ", "\\ "))
                .append("");
        commandResult = ShellUtils.execCommand(command.toString(),
                ShellUtils.checkRootPermission());
        return commandResult;
    }

    public static void installSystemApk(Context context, File file, String packageName, String fulleEntryActivityName) {
        String fileName = file.getName();
        String apkPath = file.getAbsolutePath();
        PackageInfo info = getPackageInfoByPath(apkPath, context);
        String command = installApk(file.getAbsolutePath()) + "&&" + afterInstall(apkPath, packageName, fulleEntryActivityName);
        //command+="&&(am start com.sunmnet.mediaroom.tabsp/.ui.DispatcherActivity)";
        RunningLog.run("执行升级命令:" + command);
        ToastUtil.show(BaseApplication.getInstance(), "开始安装", false);
        ShellUtils.CommandResult result = ShellUtils.execCommand(command);
        if (result.result != 0) {
            ToastUtil.show(BaseApplication.getInstance(), "升级失败！！" + result.errorMsg, false);
            Event<String, EventType> event = new Event<>();
            event.setEventType(EventType.UPGRADE_FAILED);
            event.setMessage("升级失败！！" + result.errorMsg);
            EventBus.getDefault().post(event);
        }
    }

    public static PackageInfo getPackageInfoByPath(String filePath, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        return info;
    }

    private static String installApk(String apkPath) {
        return "(pm install -r " + apkPath + ")";
    }

    private static String afterInstall(String apkPath, String packageName, String activityName) {
        String remveTempPath = "(rm -f " + apkPath + ")";
        remveTempPath += "&&(am start -n " + packageName + "/" + activityName + ")";
        return remveTempPath;
    }
}
