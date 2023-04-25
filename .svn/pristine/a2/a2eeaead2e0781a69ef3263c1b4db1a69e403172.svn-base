package com.sunmnet.mediaroom.common.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.interfaces.Callback;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.util.JsonUtils;

/**
 * Created by dengzl_pc on 2017/8/7.
 */

public class AppUtil {
    public static final String LOG_TAG = "AppUtil";
    /**
     * 本地文件保存路径
     */
    public static String SD_FOLDER = "";

    public static boolean copyAssetsToDst(Context context, String srcPath, String dstPath) {
        try {
            String fileNames[] = context.getAssets().list(srcPath);
            if (fileNames.length > 0) {
                File file = new File(Environment.getExternalStorageDirectory(), dstPath);
                if (!file.exists()) file.mkdirs();
                for (String fileName : fileNames) {
                    if (!srcPath.equals("")) { // assets 文件夹下的目录
                        copyAssetsToDst(context, srcPath + File.separator + fileName, dstPath + File.separator + fileName);
                    } else { // assets 文件夹
                        copyAssetsToDst(context, fileName, dstPath + File.separator + fileName);
                    }
                }
            } else {
                File outFile = new File(Environment.getExternalStorageDirectory(), dstPath);
                InputStream is = context.getAssets().open(srcPath);
                FileOutputStream fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                is.close();
                fos.close();
            }
            return true;
        } catch (Exception e) {
            RunningLog.error("asset文件" + srcPath + " 复制到:" + dstPath + "，失败！！");
            RunningLog.error(e);
            return false;
        }
    }

    /**
     * 执行截图
     *
     * @param root     要保存的截图目录
     * @param fileName 要保存的截图名称
     * @param callBack 截图完成后的回调对象
     */
    public static void capture(String root, String fileName, final Callback callBack) {
        File file = new File(root);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(root, fileName);
        if (file.exists()) file.delete();
        final String snapShotPath = file.getAbsolutePath();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ShellUtils.CommandResult result = ShellUtils.execCommand("screencap -p " + snapShotPath, ShellUtils.hasRoot);
                if (result != null && result.result == 0) {
                    try {
                        Thread.sleep(1000);
                        callBack.onSuccess(snapShotPath);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else callBack.onFail(snapShotPath);
            }
        });
        thread.start();
    }

    public static void capture(DeviceInfo device, Callback callBack) {
        String rootPath;
        String fileName;
        if (device == null) {
            RunningLog.error("设备数据未正常初始化....");
            rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
            fileName = "";
        } else {
            rootPath = device.getFolderPath() + File.separator;
            fileName = device.getClassroomCode() + "_";
        }
        fileName += DateUtil.getNowDateString("yyyyMMdd_HHmmss_");
        fileName += ".png";
        capture(rootPath, fileName, callBack);
    }

    public static String getAppInfo(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return pkName + "   " + versionName + "  " + versionCode;
        } catch (Exception e) {
        }
        return null;
    }

    public static PackageInfo getPackageInfoByName(String packageName, Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(
                    packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();

        }
        return packageInfo;
    }

    public static PackageInfo getPackageInfoByPath(String filePath, Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        return info;
    }

    public static String makeInstallCommand(String apkPath, String appName, String oldSysPath, String packageName, String activityName, boolean restartSystem) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        buffer.append("(export LD_LIBRARY_PATH=/vendor/lib:/system/lib)");
        /*buffer.append("&&");
        buffer.append("(pm uninstall "+packageName+")");*/
        buffer.append("&&");
        buffer.append("(mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system)");
        buffer.append("&&");
        String str = "cat " + apkPath + " > /system/app/" + appName;
        String removeStr = "((rm -f " + oldSysPath + " )&&(" + str + "))";
        buffer.append("(" + removeStr + "||(" + str + "))");
        // buffer.append("("+str+")");
        buffer.append("&&");
        buffer.append("(pm install -r " + apkPath + ")");
        buffer.append("&&");
        buffer.append("(rm -f " + apkPath + ")");
        buffer.append("&&");
        if (restartSystem) {
            buffer.append("(reboot)");
        } else {
            buffer.append("(am start -n " + packageName + "/" + activityName + ")");
        }
        buffer.append(")");
        buffer.append("||(");
        buffer.append("(pm install -r " + apkPath + ")");
        buffer.append("&&");
        if (restartSystem) {
            buffer.append("(rm -f " + apkPath + ")");
            buffer.append("&&");
            buffer.append("(reboot)");
        } else {
            buffer.append("(am start -n " + packageName + "/" + activityName + ")");
            buffer.append("&&");
            buffer.append("(rm -f " + apkPath + ")");
        }
        /**设备执行完成后都重启*/
        buffer.append(")");
        return buffer.toString();
    }

    public static PackageInfo getPackageInfoByContext(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void installSystemApk(File file, String oldSystemApkTag, final Callback callBack) {
        String fileName = file.getName();
        String apkPath = file.getAbsolutePath();
        String command = copyToSystemApp(oldSystemApkTag, fileName, apkPath);
        ShellUtils.CommandResult result = ShellUtils.execCommand(command, ShellUtils.hasRoot);
        if (result.result == 0) {
            RunningLog.debug("复制到系统App成功!!");
        } else
            RunningLog.warn("命令:" + command + "======>执行有误");
        command = installApk(apkPath) + "&&(rm -f " + apkPath + ")";
        result = ShellUtils.execCommand(command, ShellUtils.hasRoot);
        if (result.result == 0) {
            RunningLog.debug("程序更新成功!!");
        } else {
            RunningLog.error("更新失败:" + JsonUtils.objectToJson(result));
            callBack.onFail();
        }
    }

    public static void installSystemApk(Context context, File file, String oldSystemApkTag, String packageName, String fulleEntryActivityName, final Callback callBack) {
        String fileName = file.getName();
        String apkPath = file.getAbsolutePath();
        PackageInfo info = getPackageInfoByPath(apkPath, context);
        /*String command=copyToSystemApp(oldSystemApkTag,fileName,apkPath);
        CommandExecution.CommandResult result=CommandExecution.execCommand(command,true);
        if(result.result==0){
            RunningLog.debug("复制到系统App成功!!");
            String systemPath="/system/app/"+file.getName();
            command="(chmod 666 "+systemPath+")&&";
            command+=installApk(systemPath)+"&&"+afterInstall(apkPath,packageName,fulleEntryActivityName);
        }else{
            RunningLog.warn("命令:"+command+"======>执行有误");
            command=installApk(file.getAbsolutePath())+"&&"+afterInstall(apkPath,packageName,fulleEntryActivityName);
        }*/
        String command = installApk(file.getAbsolutePath()) + "&&" + afterInstall(apkPath, packageName, fulleEntryActivityName);
        RunningLog.run("执行升级命令:" + command);
        ShellUtils.CommandResult result = ShellUtils.execCommand(command, ShellUtils.hasRoot);
        if (result.result != 0) {
            RunningLog.error("更新失败:" + JsonUtils.objectToJson(result));
            if (callBack != null) {
                callBack.onFail(info.versionName);
            }
        }

        /*String fileName=file.getName();
        String apkPath=file.getAbsolutePath();
        String command=removeSystemApp(oldSystemApkTag);
        CommandExecution.CommandResult result=CommandExecution.execCommand(command,true);
        RunningLog.run(command+"-->\n执行结果："+JsonUtils.objectToJson(result));
        String systemApk = "/system/app/" + fileName;
        command=exportLibrary();
        result=CommandExecution.execCommand(command,true);
        RunningLog.run(command+"-->\n执行结果："+JsonUtils.objectToJson(result));
        command="cat " + apkPath + " > " + systemApk ;
        result=CommandExecution.execCommand(command,true);
        RunningLog.run(command+"-->\n执行结果："+JsonUtils.objectToJson(result));
        if(result.result==0){

            command="(chmod 666 "+systemApk+")&&(pm install -r "+systemApk+")";
        }else{
            command="pm install -r "+file.getAbsolutePath();
        }
        result=CommandExecution.execCommand(command,true);
        RunningLog.run(command+"-->\n执行结果："+JsonUtils.objectToJson(result));
        command=afterInstall(apkPath,packageName,fulleEntryActivityName);
        result=CommandExecution.execCommand(command,true);
        RunningLog.run(command+"-->\n执行结果："+JsonUtils.objectToJson(result));
        if(file!=null&&file.exists())
        {
            String fileName=file.getName();
            String apkPath=file.getAbsolutePath();
            PackageInfo info=getPackageInfoByPath(apkPath,context);
            String command=installApk(file.getAbsolutePath());
            command+="&&"+copyToSystemApp(oldSystemApkTag,fileName,apkPath);
            command+="&&"+afterInstall(apkPath,packageName,fulleEntryActivityName);
            StringBuilder builder=new StringBuilder();
            builder.append("命令-->");
            builder.append(command);
            RunningLog.run("升级中:"+builder.toString());
            CommandExecution.CommandResult result=CommandExecution.execCommand(command,true);
            builder.setLength(0);
            builder.append("升级结果:");
            builder.append((result.successMsg==null||result.successMsg.equals(""))
                    ?"失败-->"+result.errorMsg
            :"成功-->"+result.successMsg);
            RunningLog.run(builder.toString());
        }*/
    }

    private static String removeSystemApp(String appNameTag) {
        StringBuffer buffer = new StringBuffer();
        String systemApp = "/system/app/";
        ShellUtils.CommandResult result = ShellUtils.execCommand("ls " + systemApp, ShellUtils.hasRoot);
        if (result.result == 0) {
            result = ShellUtils.execCommand("ls " + systemApp + "*" + appNameTag + "*.apk", ShellUtils.hasRoot);
            if (result.result == 0) {
                String[] res = result.successMsg.split("\\n");
                StringBuffer buf = new StringBuffer();
                if (res.length > 0) {
                    for (int i = 0; i < res.length; i++) {
                        String msg = res[i];
                        if (buf.length() > 0) {
                            buf.append("&&");
                        }
                        buf.append("(");
                        buf.append("rm -f ");
                        buf.append(msg);
                        buf.append(")");
                    }
                    buffer.append("&&");
                    buffer.append(buf);
                    return buffer.toString();
                } else return "&&([ 1 -eq 1 ])";
            } else return "&&([ 1 -eq 1 ])";
        } else return null;
    }

    private static String exportLibrary() {
        return "(export LD_LIBRARY_PATH=/vendor/lib:/system/lib)&&(mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system)";
    }

    private static String installBySystemApp(String apkName) {
        return "pm install -r /system/app/" + apkName;
    }

    private static String copyToSystemApp(String deviceType, String apkName, String apkPath) {
        String systemAppPath = removeSystemApp(deviceType);
        if (systemAppPath != null) {
            //没有/system/app/文件夹/
            ///加载权限
            String systemAppOp = "(export LD_LIBRARY_PATH=/vendor/lib:/system/lib)&&(mount -o remount,rw -t yaffs2 /dev/block/mtdblock3 /system)";
            //删除旧的/system/app/MR_BRAND_0.0.0.0000.apk
            systemAppOp += systemAppPath;
            //复制apk到/system/app/下
            String systemApk = "/system/app/" + apkName;
            systemAppOp += "&&(cat " + apkPath + " > " + systemApk + ")";
            return systemAppOp;
        } else return "";
    }

    private static String installApk(String apkPath) {
        return "(pm install -r " + apkPath + ")";
    }

    private static String afterInstall(String apkPath, String packageName, String activityName) {
        String remveTempPath = "(rm -f " + apkPath + ")";
        remveTempPath += "&&(am start -n " + packageName + "/" + activityName + ")";
        return remveTempPath;
    }

    /**
     * 系统应用安装
     *
     * @param apkPath       新apk的保存路径
     * @param context       上下文
     * @param needToRestart 安装完毕后是否需要重启设备
     */
    public static boolean systemSlientIntstall(String apkPath, Context context, boolean needToRestart) {
        boolean result = false;
        PackageInfo info = getPackageInfoByPath(apkPath, context);
        if (info != null) {
            String packageName = info.packageName;
            String activityName = info.activities[0].name;
            info = getPackageInfoByContext(context);
            String oldVersion = info.versionName;
            File file = new File(apkPath);
            String oldPath = "/system/app/" + file.getName();
            String apkName = "/system/app/" + oldVersion + ".apk";
            String command = makeInstallCommand(apkPath, apkName, oldPath, packageName, activityName, needToRestart);
            ShellUtils.CommandResult execution = ShellUtils.execCommand(command, ShellUtils.hasRoot);
            result = (execution.result == 0) ? true : false;
        }
        return result;
    }

    /**
     * 循环assets文件夹下指定的目录，执行安装apk文件
     *
     * @param pathInAsset assets下的目录
     */
    public static void addNessesarySoftInAssetsDirectory(String pathInAsset, Context context) {
        //只安装apk文件
        //对比apk是否已经安装
        //如果未安装，则执行后台安装
    }

    public static void addNessarySoftInAssets(String path, Context context) throws IOException {
        addNessarySoftInAssets(path, context, new Callback<String>() {
            @Override
            public void onSuccess(String callbackMsg) {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFail(String callbackMsg) {

            }
        });
    }

    public static boolean isPortOccupy(int port) {
        boolean occupy = false;
        try {
            ShellUtils.CommandResult result = ShellUtils.execCommand("netstat -ano | grep " + port, ShellUtils.hasRoot);
            if (result != null && !result.successMsg.equals("") && result.successMsg.length() > 1) {
                //命令执行成功并且命令的返回值有数据，说明端口已经启动起来了。
                occupy = true;
            }
        } catch (Exception e) {
            RunningLog.error(e);
        }


        return occupy;
    }

    /**
     * 循环assets文件夹下指定的目录，执行安装apk文件
     *
     * @param path 增加的软件在assets文件夹下的路径
     */
    public static void addNessarySoftInAssets(final String path, final Context context, final Callback callBack) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences preferences = getSharedPreferences("installation", context);
                    boolean isInstall = preferences.getBoolean(path, false);
                    if (isInstall == false) {
                        //执行安装
                        String tempFilePath = SD_FOLDER + "/temp/";
                        File file = new File(tempFilePath);
                        if (!file.exists()) file.mkdirs();
                        String[] temp = path.split("/");
                        String tempFileName = temp[temp.length - 1];
                        tempFilePath += tempFileName;
                        copyFileFromAssetToLocalPath(path, context, tempFilePath);
                        file = new File(tempFilePath);
                        if (file.exists()) {
                            PackageInfo info = getPackageInfoByPath(tempFilePath, context);
                            PackageInfo exists = getPackageInfoByName(info.packageName, context);
                            if (exists == null) {
                                isInstall = slientInstall(tempFilePath);
                                if (isInstall == false) {
                                    Log.w(LOG_TAG, "未能完成外部软件安装！！！");
                                    callBack.onFail("未能完成外部软件安装！！！");
                                } else {
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean(path, isInstall);
                                    editor.commit();
                                    callBack.onSuccess();
                                }
                            } else {
                                String msg = "软件-->" + path + " 已经安装完成，无需再次安装...";
                                Log.w(LOG_TAG, msg);
                                callBack.onSuccess(msg);
                            }
                        }
                    } else callBack.onSuccess();
                } catch (Exception e) {
                    Log.e(LOG_TAG, "软件-->" + path + " 已经安装完成，无需再次安装...");
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static SharedPreferences getSharedPreferences(String name, Context context) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean slientInstall(String path) {
        File file = new File(path);
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        System.out.println(file.getPath());
        if (file.exists()) {
            System.out.println(file.getPath() + "==");
            try {
                process = Runtime.getRuntime().exec("su");
                out = process.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(out);
                dataOutputStream.writeBytes("chmod 777 " + file.getPath()
                        + "\n"); // 获取文件所有权限
                dataOutputStream
                        .writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
                                + file.getPath()); // 进行静默安装命令
                // 提交命令
                dataOutputStream.flush();
                // 关闭流操作
                dataOutputStream.close();
                out.close();
                int value = process.waitFor();

                // 代表成功
                if (value == 0) {
                    Log.e("hao", "安装成功！");
                    file.delete();
                    result = true;
                } else if (value == 1) { // 失败
                    Log.e("hao", "安装失败！");
                    result = false;
                } else { // 未知情况
                    Log.e("hao", "未知情况！");
                    result = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!result) {
                Log.e("hao", "root权限获取失败，将进行普通安装");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                //startActivity(intent);
                result = true;
            }
        }

        return result;
    }

    public static String getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void copyFileFromAssetToLocalPath(InputStream is, String localPath, Context context) {
        FileOutputStream fos = null;
        try {
            File file = new File(localPath);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            else if (file.exists())
                file.delete();
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void copyFileFromAssetToLocalPath(String assetPath, Context context, String localPath) throws IOException {
        copyFileFromAssetToLocalPath(context.getAssets().open(assetPath), localPath, context);
    }

    /**
     * 开启5555端口进行远程调试
     */
    public static void openDebugRemote() {
        try {
            ShellUtils.CommandResult result = ShellUtils.execCommand("getprop | grep service.adb.tcp.port", ShellUtils.hasRoot);
            if (result.successMsg == null || "".equals(result.successMsg)) {
                result = ShellUtils.execCommand("setprop service.adb.tcp.port 5555", ShellUtils.hasRoot);
                ShellUtils.execCommand("stop adbd", ShellUtils.hasRoot);
                ShellUtils.execCommand("start adbd", ShellUtils.hasRoot);
                ShellUtils.execCommand("getprop | grep service.adb.tcp.port", ShellUtils.hasRoot);
                if (result != null && result.successMsg != null) {
                    RunningLog.info("远程调试开启成功:" + result.successMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   /* public static void createFile(String assetPath, String localPath, Context context) throws IOException {
        createFile(context.getAssets().open(assetPath), localPath, context);
    }*/

    /**
     * 获取外置SD卡路径以及TF卡的路径
     * <p>
     * 返回的数据：paths.get(0)肯定是外置SD卡的位置，因为它是primary external storage.
     *
     * @return 所有可用于存储的不同的卡的位置，用一个List来保存
     */
    public static List<String> getExtSDCardPathList() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();
        //首先判断一下外置SD卡的状态，处于挂载状态才能获取的到
        if (extFileStatus.equals(Environment.MEDIA_MOUNTED)
                && extFile.exists() && extFile.isDirectory()
                && extFile.canWrite()) {
            //外置SD卡的路径
            paths.add(extFile.getAbsolutePath());
        }
        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile
                        .getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                //扩展存储卡即TF卡或者SD卡路径
                paths.add(mountPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 获取最大存取控件的路径
     * 谁最大就返回谁
     */
    public static String getMaxFreePath(Context context) {
        String freePath = null;
        List<String> exts = getExtSDCardPathList();
        long maxSize = 0;
        for (int i = 0; i < exts.size(); i++) {
            String dire = exts.get(i);
            File direcotory = new File(dire);
            if (direcotory.isDirectory() && direcotory.canWrite() && direcotory.canRead()) {
                long usableSpace = direcotory.getUsableSpace();//获取文件目录对象剩余空间
                if (usableSpace > maxSize) {
                    freePath = dire;
                    maxSize = usableSpace;
                }
            }
        }
        if (freePath == null) {
            File appFile = context.getExternalFilesDir(null);
            if (appFile != null && appFile.canWrite() && appFile.canRead()) {
                freePath = appFile.getAbsolutePath();
            } else {
                freePath = context.getFilesDir().getAbsolutePath();
            }
        }
        return freePath;
    }

    public static boolean isSystemApp(Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_SYSTEM) > 0;
    }


    public static boolean isSystemApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        if (packageName != null) {
            try {
                PackageInfo info = pm.getPackageInfo(packageName, 0);
                return (info != null) && (info.applicationInfo != null) &&
                        ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
