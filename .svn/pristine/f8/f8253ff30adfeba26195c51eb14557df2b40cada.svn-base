package com.sunmnet.mediaroom.videoremoter;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

public class VideoApplication extends BaseApplication {
    @Override
    protected void onAsyncInitialize() {
        MQDataHandler.getInstance(this);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MQDataHandler.getInstance(getApplicationContext()).publish("我是安卓客户端");
            }
        });
    }
}
