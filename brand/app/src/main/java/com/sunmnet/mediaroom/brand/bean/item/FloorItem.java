package com.sunmnet.mediaroom.brand.bean.item;

import android.support.annotation.NonNull;

public class FloorItem {
    private String code;
    private String name;
    private String buildingCode;
    private String campusCode;

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

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getCampusCode() {
        return campusCode;
    }

    public void setCampusCode(String campusCode) {
        this.campusCode = campusCode;
    }

    @NonNull
    @Override
    public String toString() {
        return name == null ? super.toString() : name;
    }

}
