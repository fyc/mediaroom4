package com.sunmnet.mediaroom.tabsp.record.impl.hik.services;

import java.util.List;

public interface InteractiveService<T> {
    /**
     * 获取设置中的所有设备
     */
    public List<T> getSettingParners();

    /**
     * 获取正在会议中的设备
     */
    public List<T> getPartificants();
    /**
     * 邀请加入
     * @param partificants
     * */
    public boolean invite(List<T> partificants);
    /**
     * 终端会议
     * */
    public boolean intrrupt();
    /**
     * 退出会议
     * */
    public boolean quit();
}
