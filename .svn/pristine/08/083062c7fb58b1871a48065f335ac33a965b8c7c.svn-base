package com.sunmnet.mediaroom.brand.impl.swipeCard;

import android.content.Context;

import com.sunmnet.mediaroom.brand.impl.attendance.AbstractAttendance;
import com.sunmnet.mediaroom.brand.impl.attendance.SilentSwipeCardAttendance;
import com.sunmnet.mediaroom.brand.impl.attendance.SwipeCardAttendance;
import com.sunmnet.mediaroom.common.tools.RunningLog;

/**
 * Create by WangJincheng on 2021/7/23
 * 刷卡是在考勤状态
 */

public class AttendanceState extends SwipeCardState {

    @Override
    public void openDoor(Context context, String cardNumber) {
        RunningLog.info("刷卡是在考勤状态，不执行开门禁");
    }

    @Override
    public void submitAttendance(String cardNumber, boolean isSilent) {
        AbstractAttendance attendance;
        if (isSilent) {
            // 静默刷卡
            attendance = new SilentSwipeCardAttendance();
        } else {
            // 有考勤界面时的刷卡
            attendance = new SwipeCardAttendance();
        }
        attendance.attendance(cardNumber);
    }

    @Override
    public void recovery() {
        super.swipeCardContext.setSwipeState(SwipeCardContext.NORMAL_STATE);
        super.swipeCardContext.recovery();
    }
}
