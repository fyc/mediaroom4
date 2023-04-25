package com.sunmnet.mediaroom.common.tools;

import android.content.Context;

import com.sunmnet.mediaroom.android.support.log4j2.AndroidLog4jHelper;
import com.sunmnet.mediaroom.common.R;
import com.sunmnet.mediaroom.common.interfaces.ISender;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;


public class Log4jUtil {

    private static Logger logger;
    private static ISender sender;
    private static SocketSender socketSender;

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
        AndroidLog4jHelper.initialize(context, configRawResource, logRootDir);
        logger = LogManager.getRootLogger();
    }

    public static void init(ISender s) {
        sender = s;
    }

    public static void init(SocketSender s) {
        socketSender = s;
    }

    private static void send(String msg) {
        if (sender != null) {
            sender.send(msg);
        }
        if (socketSender != null) {
            socketSender.sendMessage(msg);
        }
    }


    public static void error(String e) {
        if (logger != null) {
            logger.error(e);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[error][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += e;
            tempmsg += "\n";
            send(tempmsg);
        }
    }

    public static void error(Exception err) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        err.printStackTrace(pw);
        String msg = sw.toString();
        pw.close();
        error(msg);
    }

    public static void error(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String msg = sw.toString();
        pw.close();
        error(msg);
    }

    public static void error(String tag, Exception err) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        err.printStackTrace(pw);
        String msg = sw.toString();
        msg = tag + ":" + msg;
        pw.close();
        error(msg);
    }

    public static void warn(String msg) {
        if (logger != null) {
            logger.warn(msg);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[warn][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += msg;
            tempmsg += "\n";
            send(tempmsg);
        }
    }

    public static void info(String msg) {
        if (logger != null) {
            logger.info(msg);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[info][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += msg;
            tempmsg += "\n";
            send(tempmsg);
        }
    }

    public static void commu(String dest, String msg) {
        String source = "本机";
        commu(source, dest, msg);
    }

    public static void commu(String source, String dest, String message) {
        String msg = "[" + source + "-->" + dest + "]:" + message;
        commu(msg);
    }

    public static void commu(String msg) {
        if (logger != null) {
            Level level;
            if ((level = Level.getLevel("COMMU")) != null)
                logger.log(level, msg);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[commu][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += msg;
            tempmsg += "\n";
            send(tempmsg);
        }
    }

    public static void run(String msg) {
        if (logger != null) {
            Level level;
            if ((level = Level.getLevel("RUN")) != null)
                logger.log(level, msg);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[run][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += msg;
            tempmsg += "\n";
            send(tempmsg);
        }
    }

    public static void debug(String msg) {
        if (logger != null) {
            logger.debug(msg);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[debug][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += msg;
            tempmsg += "\n";
            send(tempmsg);
        }
    }

    public static void trace(String msg) {
        if (logger != null) {
            logger.trace(msg);
        }
        if (sender != null || socketSender != null) {
            String tempmsg = "[trace][" + DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT) + "][" + Thread.currentThread().getName() + "]:";
            tempmsg += msg;
            tempmsg += "\n";
            send(tempmsg);
        }
    }
}
