package com.sunmnet.mediaroom.brand.impl.swipeCard;

import android.content.Context;

/**
 * Create by WangJincheng on 2021/7/23
 * 机器刷卡状态并操作抽象类
 */

public abstract class SwipeCardState {

    // 过渡状态
    SwipeCardContext swipeCardContext;

    public void setContext(SwipeCardContext swipeCardContext) {
        this.swipeCardContext = swipeCardContext;
    }

    /**
     * 打开门禁
     * @param context
     * @param cardNumber 卡号
     */
    public abstract void openDoor(Context context, String cardNumber);

    /**
     * 提交考勤
     * @param cardNumber 卡号
     * @param isSilent 是否静默刷卡
     */
    public abstract void submitAttendance(String cardNumber, boolean isSilent);

    /**
     * 刷卡状态恢复普通状态
     */
    public abstract void recovery();
}
