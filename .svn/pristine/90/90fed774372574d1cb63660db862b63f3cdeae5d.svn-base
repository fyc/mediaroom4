package com.sunmnet.mediaroom.brand.enums;

public enum AirConditionerMode {

    AUTO("1", "自动"),
    COOL("2", "制冷"),
    DEHUMIDIFICATION("3", "除湿"),
    FAN("4", "出风"),
    HEAT("5", "制热");
    String mode;
    String name;

    AirConditionerMode(String mode, String name) {
        this.mode = mode;
        this.name = name;
    }

    public static AirConditionerMode getMode(String modeStr) {
        for (AirConditionerMode mode : values()) {
            if (mode.mode.equals(modeStr)) {
                return mode;
            }
        }
        return null;
    }
}
