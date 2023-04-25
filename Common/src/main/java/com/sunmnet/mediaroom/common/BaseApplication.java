package com.sunmnet.mediaroom.common;

import android.support.annotation.CallSuper;
import android.support.multidex.MultiDexApplication;

import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.config.DeviceConfig;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

import java.io.File;

public abstract class BaseApplication extends MultiDexApplication {
    protected static BaseApplication instance;
    private String storagePath;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 异步初始化线程
     */
    Runnable asyncInit = new Runnable() {
        @Override
        public void run() {
            ShellUtils.init();
            onAsyncInitialize();
        }
    };


    @CallSuper
    protected void init() {
        instance = this;
        initUncaughtExceptionHandler();
        initDeviceInfo();
        RunningLog.init(this, getStoragePath());
        ThreadUtils.execute(asyncInit);
    }

    protected void initUncaughtExceptionHandler() {

    }

    /**
     * 异步线程回调，用于初始化事情
     */
    protected abstract void onAsyncInitialize();

    /**
     * 初始化存储路径
     */
    protected abstract String initStoragePath();

    protected String getStoragePath() {
        if (storagePath == null) {
            storagePath = initStoragePath();
        }
        return storagePath;
    }

    protected void initDeviceInfo() {
        String rootPath = getStoragePath();
        DeviceInfo deviceInfo = DeviceConfig.getInstance().getDeviceInfo(this);
        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo();
            deviceInfo.setFolderPath(rootPath);
            DeviceConfig.getInstance().saveDeviceInfo(this, deviceInfo);
        } else {
            if (deviceInfo.getFolderPath() != null) {
                File file = new File(deviceInfo.getFolderPath());
                if (!file.exists() || !file.canWrite() || !file.canRead()) {
                    //如果旧路径出现问题，则使用新路径
                    deviceInfo.setFolderPath(rootPath);
                    DeviceConfig.getInstance().saveDeviceInfo(this, deviceInfo);
                }
            } else {
                deviceInfo.setFolderPath(rootPath);
                DeviceConfig.getInstance().saveDeviceInfo(this, deviceInfo);
            }
        }
    }

}
