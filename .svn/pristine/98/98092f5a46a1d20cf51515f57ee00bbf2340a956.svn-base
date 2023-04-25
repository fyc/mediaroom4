package com.sunmnet.mediaroom.brand.impl.swipeCard;

import android.content.Context;

import com.sunmnet.mediaroom.common.tools.RunningLog;

/**
 * Create by WangJincheng on 2021/7/23
 * 刷卡，普通状态
 */

public class NormalState extends SwipeCardState {

    @Override
    public void openDoor(Context context, String cardNumber) {
        super.swipeCardContext.setSwipeState(SwipeCardContext.OPEN_DOOR_STATE);
        super.swipeCardContext.openDoor(context, cardNumber);
    }

    @Override
    public void submitAttendance(String cardNumber, boolean isSilent) {
        super.swipeCardContext.setSwipeState(SwipeCardContext.ATTENDANCE_STATE);
        super.swipeCardContext.submitAttendance(cardNumber, isSilent);
    }

    @Override
    public void recovery() {
        RunningLog.info("刷卡功能已恢复普通状态");
    }
}
