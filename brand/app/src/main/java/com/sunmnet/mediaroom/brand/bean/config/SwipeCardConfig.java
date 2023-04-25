package com.sunmnet.mediaroom.brand.bean.config;

/**
 * Create by WangJincheng on 2021/7/23
 * 刷卡配置
 */

public class SwipeCardConfig {

    // 是否允许开门禁，默认是关闭
    private boolean enableOpenDoor = false;

    public boolean isEnableOpenDoor() {
        return enableOpenDoor;
    }

    public void setEnableOpenDoor(boolean enableOpenDoor) {
        this.enableOpenDoor = enableOpenDoor;
    }
}
