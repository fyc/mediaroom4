package com.sunmnet.mediaroom.brand.bean.item;

import android.support.annotation.NonNull;

public class CampusItem {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name == null ? super.toString() : name;
    }
}
