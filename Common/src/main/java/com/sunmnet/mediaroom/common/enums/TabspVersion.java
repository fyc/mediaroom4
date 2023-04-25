package com.sunmnet.mediaroom.common.enums;

public enum TabspVersion {
    VERSION_3("3.0"),
    VERSION_4("4.0"),
    VERSION_3_4("版本3和4")
    ;
    TabspVersion(String description) {
        this.description = description;
    }

    String description;

    public String toString() {
        return "版本号：" + this.description;
    }
}
