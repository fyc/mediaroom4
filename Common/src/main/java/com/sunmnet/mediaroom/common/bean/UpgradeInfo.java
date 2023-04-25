package com.sunmnet.mediaroom.common.bean;

public class UpgradeInfo {
    public static final String UPGRADE_FAILURE="01";
    public static final String UPGRADE_SUCCESS="00";
    public String getUpgradeResponsePath() {
        return upgradeResponsePath;
    }

    public void setUpgradeResponsePath(String upgradeResponsePath) {
        this.upgradeResponsePath = upgradeResponsePath;
    }

    String upgradeResponsePath;
    String currentVersionName;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    String uuid;
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    String fileMd5;
    public String getCurrentVersionName() {
        return currentVersionName;
    }

    public void setCurrentVersionName(String currentVersionName) {
        this.currentVersionName = currentVersionName;
    }

    public String getPrevVersionName() {
        return prevVersionName;
    }

    public void setPrevVersionName(String prevVersionName) {
        this.prevVersionName = prevVersionName;
    }

    String prevVersionName;
    boolean isUpgrading = false;

    public String getUpgradeFileDownloadPath() {
        return upgradeFileDownloadPath;
    }

    public void setUpgradeFileDownloadPath(String upgradeFileDownloadPath) {
        this.upgradeFileDownloadPath = upgradeFileDownloadPath;
    }

    String upgradeFileDownloadPath;

    public boolean isUpgrading() {
        return isUpgrading;
    }

    public void setUpgrading(boolean upgrading) {
        isUpgrading = upgrading;
    }


}
