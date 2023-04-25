package com.sunmnet.mediaroom.device.bean;

public enum DeviceState {
    OPENED("1"),
    CLOSED("0"),
    OPENNING("2"),
    CLOSING("3"),
    OPENNONET("4"),
    ERROR("5"),
    DOORLOCKER_LOCKED("7"),//锁开磁关
    DOORLOCKER_RELEASE("8"),//锁关磁开
    CUSTOM(""),
    FORBIDDEN("-1");//设备已禁用

    DeviceState(String value) {
        this.value = value;
    }

    String value;

    public String getStateValue() {
        return value;
    }

    public void setStateValue(String val) {
        this.value = val;
    }

    public static DeviceState fromString(String value) {
        DeviceState state = DeviceState.ERROR;
        switch (value) {
            case "0":
                state = DeviceState.CLOSED;
                break;
            case "1":
                state = DeviceState.OPENED;
                break;
            case "2":
                state = DeviceState.OPENNING;
                break;
            case "3":
                state = DeviceState.CLOSING;
                break;
            case "4":
                state = DeviceState.OPENNONET;
                break;
            case "5":
                state = DeviceState.ERROR;
                break;
            default:
                try {
                    int val = Integer.parseInt(value);
                    if (val > 0) state = DeviceState.OPENED;
                    else state = DeviceState.CLOSED;
                } catch (Exception e) {

                }
                break;
        }
        return state;
    }
}
