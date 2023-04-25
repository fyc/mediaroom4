package com.sunmnet.mediaroom.wirelessprojection.interfaces;

import java.util.List;

public interface IWirelessOperator<T> {
    /**
     * 获取投屏器配置中的所有设备
     */
    List<T> getScreens();

    /**
     * 调用批注功能
     */
    void invokeNote();

    /**
     * 调用白板功能
     */
    void invokeWhiteBoard();

    /**
     * 调用文件功能
     */
    void invokeFile();

    /**
     * 停止教室投屏
     */
    void invokeStop();

    /**
     * 恢复教师投屏
     */
    void invokeResume();

    /**
     * 锁定状态的反向操作
     */
    void reverseLock();

    /**
     * 调用音量功能
     */
    void invokeVolumn(int count);

    void setShowingMode(boolean isSingleton);

    void setShowingDevice(T t, boolean show);

    void broadcast();

    void broadcast(T t, boolean show);

    void stopBroadcast();
    void reset();
}
