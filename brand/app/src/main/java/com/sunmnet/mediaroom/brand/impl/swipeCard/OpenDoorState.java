package com.sunmnet.mediaroom.brand.impl.swipeCard;

import android.content.Context;

import com.sunmnet.mediaroom.brand.bean.config.SwipeCardConfig;
import com.sunmnet.mediaroom.brand.data.sharepref.BrandConfig;
import com.sunmnet.mediaroom.common.tools.RunningLog;

/**
 * Create by WangJincheng on 2021/7/23
 * 刷卡是在开门禁状态
 */

public class OpenDoorState extends SwipeCardState {

    @Override
    public void openDoor(Context context, String cardNumber) {
        SwipeCardConfig config = BrandConfig.getInstance().getSwipeCardConfig(context);
        if (config.isEnableOpenDoor()) {
            // 使能开门禁
            // todo 打开门禁
        } else {
            RunningLog.info("设备配置了不允许开门禁");
        }
    }

    @Override
    public void submitAttendance(String cardNumber, boolean isSilent) {
        RunningLog.info("刷卡是在开门禁状态，不提交考勤");
    }

    @Override
    public void recovery() {
        super.swipeCardContext.setSwipeState(SwipeCardContext.NORMAL_STATE);
        super.swipeCardContext.recovery();
    }
}
