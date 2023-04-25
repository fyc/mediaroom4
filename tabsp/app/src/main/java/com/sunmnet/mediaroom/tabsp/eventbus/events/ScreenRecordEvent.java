package com.sunmnet.mediaroom.tabsp.eventbus.events;

public class ScreenRecordEvent {
    int switcherState;//0 停止录屏 1开启录屏

    public int getSwitcherState() {
        return switcherState;
    }

    public void setSwitcherState(int switcherState) {
        this.switcherState = switcherState;
    }
}
