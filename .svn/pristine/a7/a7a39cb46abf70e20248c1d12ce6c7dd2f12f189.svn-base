package com.sunmnet.mediaroom.brand.fragment.template;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;

import org.greenrobot.eventbus.EventBus;

public abstract class AbstractDeviceFragment extends Fragment implements DeviceStateNotifier {
    public static final String DEVICETYPE_KEY = "deviceTypeCode";
    protected String deviceTypeCode;
    protected Handler mHandler = new Handler();

    protected Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateData();
        }
    };

    protected void bindClick(View view, View.OnClickListener listener) {
        if (view != null) view.setOnClickListener(listener);
    }

    protected abstract int getContentLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle params = getArguments();
        this.deviceTypeCode = params.getString(DEVICETYPE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentLayout(), container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        RunningLog.run("注册监听，" + deviceTypeCode);
        event.setEventType(new StateEventType(1, deviceTypeCode));
        event.setMessage(this);
        EventBus.getDefault().post(event);
    }

    protected abstract void updateData();

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        RunningLog.run("取消监听，" + deviceTypeCode);
        event.setEventType(new StateEventType(3, deviceTypeCode));
        event.setMessage(this);
        EventBus.getDefault().post(event);
    }

    @Override
    public String getListenerKey() {
        return null;
    }

    @Override
    public void callUpdate() {
        RunningLog.run("设备状态更新 callUpdate()" + deviceTypeCode);
        mHandler.post(runnable);
    }

    @Override
    public void callUpdate(String code) {
        RunningLog.run("设备状态更新 callUpdate(String code)" + deviceTypeCode);
        mHandler.post(runnable);
    }
}