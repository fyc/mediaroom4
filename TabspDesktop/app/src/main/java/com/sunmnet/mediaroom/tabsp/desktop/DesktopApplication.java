package com.sunmnet.mediaroom.tabsp.desktop;

import com.sunmnet.mediaroom.common.BaseApplication;

public class DesktopApplication extends BaseApplication {
    public static final String DB_NAME="com.sunmnet.mediaroom.desktop";
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }

    @Override
    protected void onAsyncInitialize() {
        //检查当前启动的网卡

    }
}
