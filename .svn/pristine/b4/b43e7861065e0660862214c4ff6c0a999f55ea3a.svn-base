package com.sunmnet.mediaroom.common.tools;

import android.content.Context;
import android.text.TextUtils;

import com.sunmnet.mediaroom.android.support.log4j2.AndroidLog4jHelper;
import com.sunmnet.mediaroom.common.R;
import com.sunmnet.mediaroom.common.interfaces.ISender;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dengzl_pc on 2018/2/27.
 */

public class RunningLog {


    public static void init(Context context) {
        init(context, R.raw.log4j2);
    }

    public static void init(Context context, String logRootDir) {
        init(context, R.raw.log4j2, logRootDir);
    }

    public static void init(Context context, int configRawResource) {
        init(context, configRawResource, null);
    }

    public static void init(Context context, int configRawResource, String logRootDir) {
        Log4jUtil.init(context, configRawResource, logRootDir);
    }

    public static void init(ISender s) {
        Log4jUtil.init(s);
    }

    public static void init(SocketSender s) {
        Log4jUtil.init(s);
    }

    public static String getLogDir() {
        File logDir = null;
        if (AndroidLog4jHelper.getLogRootDirPath() != null) {
            logDir = new File(AndroidLog4jHelper.getLogRootDirPath(), "log");
        }
        if (logDir == null) {
            logDir = AndroidLog4jHelper.getContext().getExternalFilesDir(null);
            if (logDir != null) {
                logDir = new File(logDir, "log");
            } else {
                logDir = new File(AndroidLog4jHelper.getContext().getFilesDir(), "log");
            }
        }
        return logDir.getAbsolutePath();
    }

    public static String getLogFile(String msg) {
        String logDir = getLogDir();
        if (TextUtils.isEmpty(msg))
            msg = "trace";
        File logFile = new File(logDir, msg + ".log");
        if (!logFile.exists() && "trace".equals(msg)) {
            logFile = new File(logDir, "trace.log");
        }
        return logFile.getAbsolutePath();
    }

    public static List<File> getLogFileList() {
        String logDir = getLogDir();
        List<File> fileList = getLogFileTree(new File(logDir));
        return fileList;
    }

    private static List<File> getLogFileTree(File file) {
        List<File> fileList = new ArrayList<>();
        if (file == null || !file.exists())
            return fileList;
        if (file.isFile()) {
            fileList.add(file);
            return fileList;
        }
        File[] child = file.listFiles();
        Arrays.sort(child);
        for (File f : child) {
            if (f.isDirectory()) {
                fileList.addAll(getLogFileTree(f));
            } else {
                fileList.add(f);
            }
        }
        return fileList;
    }

    public static void run(String msg) {
        Log4jUtil.run(msg);
    }

    public static void debug(String msg) {
        Log4jUtil.debug(msg);
    }

    public static void info(String msg) {
        Log4jUtil.info(msg);
    }

    public static void warn(String msg) {
        Log4jUtil.warn(msg);
    }

    public static void warn(Exception err) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        err.printStackTrace(pw);
        String msg = sw.toString();
        pw.close();
        warn(msg);
    }

    public static void error(Exception err) {
        Log4jUtil.error(err);
    }
    public static void error(Throwable throwable){
        Log4jUtil.error(throwable);
    }
    public static void commu(String dest, String msg) {
        Log4jUtil.commu(dest, msg);
    }

    public static void commu(String source, String dest, String message) {
        Log4jUtil.commu(source, dest, message);
    }

    public static void commu(String message) {
        Log4jUtil.commu(message);
    }

    public static void error(String e) {
        Log4jUtil.error(e);
    }

}
