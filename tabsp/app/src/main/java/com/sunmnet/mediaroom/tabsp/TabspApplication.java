package com.sunmnet.mediaroom.tabsp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.bean.CommonStr;
import com.sunmnet.mediaroom.common.enums.SocketServiceType;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.common.tools.SocketServiceFactory;
import com.sunmnet.mediaroom.tabsp.bean.TabspConfig;
import com.sunmnet.mediaroom.tabsp.bean.enums.SoftType;
import com.sunmnet.mediaroom.tabsp.common.SystemEventHandler;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.version1.Version1Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.version2.Version2Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.version3.Version3Dispatcher;
import com.sunmnet.mediaroom.tabsp.controll.versionCustom.VersionCustomDispatcher;
import com.sunmnet.mediaroom.tabsp.socket.TabspSocketServiceFactory;
import com.sunmnet.mediaroom.util.JsonUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;


/**
 * 全局上下文
 */
public class TabspApplication extends BaseApplication {
    public static final String DB_NAME = "tabsp";
    public static final String DB_CONFIG_KEY = "config_key";
    public static final String DEFAULT_MAIN_OUTPUT_CHANNEL = "default_main_output_channel"; // 视频矩阵默认主屏通道
    public static final String LAST_PA_VOLUME_VALUE = "last_pa_volume_value"; // 功放音量缓存值
    public static final String RESET_APP_TAG = "reset_app_tag"; // 记录app重启的方式
    BootBroadcast booter;
    /**
     * 配置
     */
    TabspConfig config;
    public Handler handler = new Handler();

    public static TabspApplication getInstance() {
        return (TabspApplication) instance;
    }

    public TabspConfig getConfig() {
        return config;
    }

    Dispatcher dispatcher;
    SystemEventHandler systemEventHandler;

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * 软件版本设置
     */
    public void setDispatcher(SoftType origin, SoftType target) {
        if (origin != target) {
            switch (target) {
                case VERSION_1:
                    this.dispatcher = new Version1Dispatcher(config);
                    break;
                case VERSION_2:
                    this.dispatcher = new Version2Dispatcher(config);
                    break;
                case VERSION_3:
                    this.dispatcher = new Version3Dispatcher(config);
                    break;
                case VERSION_CUSTOM:
                    this.dispatcher = new VersionCustomDispatcher(config);
                    break;
            }
        }
    }

    /**
     * 异步线程加载
     */
    @Override
    protected void onAsyncInitialize() {
        /**
         * 程序运行后，启动网络保持的Service 并生成事件处理器
         * */
        if (systemEventHandler == null) {
            systemEventHandler = new SystemEventHandler(TabspApplication.this, TabspApplication.this.config, handler);
        }
        SocketServiceFactory.getInstance().startService(SocketServiceType.LOCAL.getKey());
        SocketServiceFactory.getInstance().startService(SocketServiceType.DEBUG.getKey());
        SocketServiceFactory.getInstance().startService(SocketServiceType.LOG.getKey());
        RunningLog.init(SocketServiceFactory.getInstance().getService(SocketServiceType.LOG.getKey()));
    }

    @Override
    protected String initStoragePath() {
        return Environment.getExternalStorageDirectory() + File.separator + DB_NAME;
    }

    /**
     * 初始化
     */
    @Override
    protected void init() {
        String localConfig = SharePrefUtil.getString(this, DB_NAME, DB_CONFIG_KEY);
        if (localConfig == null) {
            this.config = new TabspConfig();
        } else {
            config = (TabspConfig) JsonUtils.jsonToBean(localConfig, TabspConfig.class);
        }
        setDispatcher(null, config.getSoftType());
        //设置语言
        Locale.setDefault(this.config.getLang());
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = this.config.getLang();
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if (true) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
        this.config.setExclusivePath(getStoragePath());
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkBroadcast, filter);
        SocketServiceFactory.getInstance().initFactory(new TabspSocketServiceFactory());
        super.init();
    }

    @Override
    protected void initUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(restartHandler);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(booter);
        systemEventHandler.release();
        RunningLog.run("系统停止运行");
        unregisterReceiver(networkBroadcast);
    }

    /**
     * 网络连接状态
     */
    private BroadcastReceiver networkBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)
                ) {
                    if (!TabspNetworkUtil.isConnect(context)) {
                        //发送网络
                        Event<Object, EventType> event = new Event<>();
                        event.setEventType(EventType.DIALOG_EVENT);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", CommonStr.NETWORK_DISCONNECTED);
                        event.setMessage(jsonObject);
                        EventBus.getDefault().post(event);
                    }
                }
            }
        }
    };

    public void upgrade() {
        if (this.systemEventHandler != null) {
            systemEventHandler.updateCache();
        }
    }

    /**
     * 崩溃异常
     */
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            RunningLog.error(sw.toString());
            pw.close();
            try {
                sw.close();
            } catch (IOException e) {
                RunningLog.error("RunningLog.error(Exception err) 错误...");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            Intent intent = getPackageManager().getLaunchIntentForPackage(getApplicationContext().getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pendingIntent); // 2秒钟后重启应用
            Process.killProcess(Process.myPid());
            System.exit(1);
        }
    };

}
