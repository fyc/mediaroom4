package com.sunmnet.mediaroom.tabsp.eventbus.events;

import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;

public class LiveStateEvent extends Event<Integer, EventType> {
    int switcherState;//0 停止录屏 1开启录屏

    public LiveStateEvent(int state) {
        this.switcherState = state;
        this.setMessage(state);
        this.setEventType(EventType.SYSTEMEVENT_DEBUG_SWITCHER);
    }

    public int getSwitcherState() {
        return switcherState;
    }

    public void setSwitcherState(int switcherState) {
        this.switcherState = switcherState;
    }
}
