package com.sunmnet.mediaroom.device.events;

import com.sunmnet.mediaroom.device.bean.DeviceType;
import com.sunmnet.mediaroom.device.bean.IDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceChangeEvent {
    List<String> updates = new ArrayList<>();

    public List<String> getUpdates() {
        return updates;
    }

    public void clear() {
        updates.clear();
        devices = null;
    }

    public Map<DeviceType,Map<String, IDevice>> getDevices() {
        return devices;
    }

    public void setDevices(Map<DeviceType, Map<String, IDevice>> devices) {
        this.devices = devices;
    }

    Map<DeviceType,Map<String, IDevice>> devices;
}
