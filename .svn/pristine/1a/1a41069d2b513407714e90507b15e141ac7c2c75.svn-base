package com.sunmnet.mediaroom.tabsp.connector;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sunmnet.mediaroom.tabsp.connector.service.ITransformer;

public class ConnectService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    IBinder binder = new Binder();
    ITransformer souceClient;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
