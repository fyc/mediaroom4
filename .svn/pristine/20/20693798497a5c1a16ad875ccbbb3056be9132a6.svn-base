package com.sunmnet.mediaroom.brand;

import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.brand.impl.swipeCard.SwipeCardContext;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

/**
 * Create by WangJincheng on 2021-11-12
 * 武汉广播电视大学定制
 */

public class WhtvuMainActivity extends MainActivity {

    @Subscribe
    public void onSwipeCard(SwipeCardEvent cardEvent) {
        ToastUtil.show(this, "刷卡成功");
        RunningLog.run("WhtvuMainActivity收到静默刷卡信息");
        // 提交静默刷卡记录
        uploadSilentSwipeCardLog(cardEvent.getCardId());
    }

    /**
     * 上传静默刷卡记录
     * @param cardNumber
     */
    private void uploadSilentSwipeCardLog(String cardNumber) {
        SwipeCardContext.getInstance().submitAttendance(cardNumber, true);
    }
}
