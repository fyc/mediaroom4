package com.sunmnet.mediaroom.brand.impl.swipeCard;

import android.content.Context;

/**
 * Create by WangJincheng on 2021/7/23
 * 机器的刷卡状态过渡管理
 */

public class SwipeCardContext {

    // 刷卡的普通状态
    public static final SwipeCardState NORMAL_STATE = new NormalState();
    // 刷卡的考勤状态
    public static final SwipeCardState ATTENDANCE_STATE = new AttendanceState();
    // 刷卡的开门禁状态
    public static final SwipeCardState OPEN_DOOR_STATE = new OpenDoorState();
    // 刷卡时设备的状态
    private SwipeCardState swipeCardState;

    private SwipeCardContext() {

    }

    public static SwipeCardContext getInstance() {
        return SingletonHolder.sInstance;
    }

    public SwipeCardState getSwipeState() {
        return swipeCardState;
    }

    public void setSwipeState(SwipeCardState swipeSwipeCardState) {
        this.swipeCardState = swipeSwipeCardState;
        this.swipeCardState.setContext(this);
    }

    /**
     * 打开门禁
     * @param context
     * @param cardNumber 卡号
     */
    public void openDoor(Context context, String cardNumber) {
        this.swipeCardState.openDoor(context, cardNumber);
    }

    /**
     * 提交考勤
     * @param cardNumber 卡号
     * @param isSilent 是否静默刷卡
     */
    public void submitAttendance(String cardNumber, boolean isSilent) {
        this.swipeCardState.submitAttendance(cardNumber, isSilent);
    }

    /**
     * 复原刷卡普通状态
     */
    public void recovery() {
        this.swipeCardState.recovery();
    }

    /**
     * 静态内部类单例模式
     */
    private static class SingletonHolder {
        private static final SwipeCardContext sInstance = new SwipeCardContext();
    }
}
