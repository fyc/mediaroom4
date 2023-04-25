package com.sunmnet.mediaroom.device;

public class CustomDeviceIcon {
    int openIconResId;
    int closeIconResId;
    int menuSelectedResId;
    int menuUnselectedResId;

    public CustomDeviceIcon() {
    }

    public CustomDeviceIcon(int openIconResId, int closeIconResId) {
        this.openIconResId = openIconResId;
        this.closeIconResId = closeIconResId;
    }

    public CustomDeviceIcon(int openIconResId, int closeIconResId, int menuSelectedResId, int menuUnselectedResId) {
        this.openIconResId = openIconResId;
        this.closeIconResId = closeIconResId;
        this.menuSelectedResId = menuSelectedResId;
        this.menuUnselectedResId = menuUnselectedResId;
    }

    public int getOpenIconResId() {
        return openIconResId;
    }

    public void setOpenIconResId(int openIconResId) {
        this.openIconResId = openIconResId;
    }

    public int getCloseIconResId() {
        return closeIconResId;
    }

    public void setCloseIconResId(int closeIconResId) {
        this.closeIconResId = closeIconResId;
    }

    public int getMenuSelectedResId() {
        return menuSelectedResId;
    }

    public void setMenuSelectedResId(int menuSelectedResId) {
        this.menuSelectedResId = menuSelectedResId;
    }

    public int getMenuUnselectedResId() {
        return menuUnselectedResId;
    }

    public void setMenuUnselectedResId(int menuUnselectedResId) {
        this.menuUnselectedResId = menuUnselectedResId;
    }
}
