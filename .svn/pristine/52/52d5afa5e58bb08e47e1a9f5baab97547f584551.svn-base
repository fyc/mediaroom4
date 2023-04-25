package com.sunmnet.mediaroom.device.events;

import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.device.bean.StateEventType;
import com.sunmnet.mediaroom.device.listeners.DeviceStateNotifier;

public class DeviceNotifyEvent extends Event<DeviceStateNotifier, StateEventType> {
    public DeviceNotifyEvent() {
    }

    public DeviceNotifyEvent(StateEventType eventType) {
        super(eventType);
    }

    public DeviceNotifyEvent(StateEventType eventType, DeviceStateNotifier message) {
        super(eventType, message);
    }
}
