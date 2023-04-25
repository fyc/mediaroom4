package com.sunmnet.mediaroom.brand.enums;

public enum PlayType {

    PROGRAM(0), NOTIFICATION(1), TEMPLATE_PROGRAM(3);

    private int type;

    public int getType() {
        return type;
    }

    PlayType(int type) {
        this.type = type;
    }

    public static PlayType get(int type) {
        for (PlayType playType : values()) {
            if (type == playType.getType()) {
                return playType;
            }
        }
        return null;
    }
}
