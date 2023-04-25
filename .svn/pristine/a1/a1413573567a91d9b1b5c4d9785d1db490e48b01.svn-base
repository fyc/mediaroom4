package com.sunmnet.mediaroom.device.bean;


import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.device.events.DeviceNotifyEvent;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class CategoryDevice extends AbstractDevice implements DeviceStateNotifier {

    List<IDevice> devices;
    int openDrawable;
    int closeDrawable;
    String name;
    int drawable = 0;
    boolean isProcessing = false;

    public DeviceType getDeviceType() {
        return this.devices.get(0).getDeviceType();
    }

    public void release() {
        registerEvent(0);
        notifier = null;
    }

    private void registerEvent(int type) {
        DeviceNotifyEvent event = new DeviceNotifyEvent();
        event.setMessage(this);
        StateEventType eventType = new StateEventType();
        eventType.type = type;
        eventType.key = devices.get(0).getDeviceType().getDeviceType();
        event.setEventType(eventType);
        EventBus.getDefault().post(event);
    }

    public CategoryDevice(List<IDevice> devices) {
        if (devices.size() > 0) {
            IDevice device = devices.get(0);
            this.deviceType = device.getDeviceType();
            this.devices = devices;
            //注册更新回调
            registerEvent(3);
            int resourceId = DeviceBuilder.getDeviceTypeNameResource(device.getDeviceTypeCode());
            if (resourceId == 0) {
                this.name = device.getDeviceTypeCodeName();
            } else {
                this.name = BaseApplication.getInstance().getString(resourceId);
            }
            this.deviceName = name;
            update();
        }
    }

    @Override
    public String getDeviceName() {
        return this.deviceName;
    }

    @Override
    public boolean isOpened() {
        switch (this.state) {
            case OPENED:
            case OPENNONET:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isProcessing() {
        return isProcessing;
    }

    public IDevice getDevice(int index) {
        return this.devices.get(index);
    }

    public List<IDevice> getDevices() {
        return devices;
    }

    public int getDeviceCount() {
        return this.devices.size();
    }

    public void update() {
        if (devices.size() > 1) {
            int enableCount = 0;
            boolean cacheProcessing = false;
            for (int i = 0; i < this.devices.size(); i++) {
                IDevice device = this.devices.get(i);
                switch (device.getDeviceState()) {
                    case OPENED:
                    case OPENNONET:
                        enableCount++;
                        break;
                    case CLOSING:
                    case OPENNING:
                        cacheProcessing = true;
                        break;
                }
            }
            this.isProcessing = cacheProcessing;
            if (enableCount == 0) {
                this.drawable = closeDrawable;
                this.state = DeviceState.CLOSED;
            } else {
                this.drawable = openDrawable;
                this.state = DeviceState.OPENED;
            }
            this.deviceName = this.name + " " + enableCount + "/" + this.devices.size();
        } else {
            this.state = devices.get(0).getDeviceState();
            if (this.state == DeviceState.CLOSING || this.state == DeviceState.OPENNING) {
                this.isProcessing = true;
            } else isProcessing = false;
        }
    }

    public void setNotifier(DeviceStateNotifier notifier) {
        this.notifier = notifier;
    }

    DeviceStateNotifier notifier;

    @Override
    public boolean isClosed() {
        switch (this.state) {
            case CLOSED:
            case ERROR:
            case OPENNING:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getListenerKey() {
        return null;
    }

    @Override
    public void callUpdate() {

    }

    @Override
    public void callUpdate(String code) {
        RunningLog.run("CategoryDevice 正在更新！！" + code);
        update();
        if (this.notifier != null) this.notifier.callUpdate(code);
    }
}
