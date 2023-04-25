package com.sunmnet.mediaroom.brand.fragment.dialog;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.brand.impl.swipeCard.SwipeCardContext;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Create by WangJincheng on 2021-07-29
 * 刷卡考勤抽象类
 */

public abstract class AbstractSwipeCardDialogFragment extends DialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        unregisterEventBus();
        recoverSwipeCard();
        attendanceFinish();
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayUtil.hideNavigationBar(getDialog().getWindow());
    }

    /**
     * 这里提高接收等级，目的是比MainActivity的订阅接收刷卡方法更早接收到信息，
     * 才很好地控制刷卡的功能（考勤或开门禁）
      * @param cardEvent 刷卡事件
     */
    @Subscribe(priority = 1)
    public void onSwipeCard(SwipeCardEvent cardEvent) {
        // 终止刷卡事件的传递，避免其它地方收到刷卡事件
        EventBus.getDefault().cancelEventDelivery(cardEvent);
        RunningLog.run("收到刷卡信信息, 卡号:" + cardEvent.getCardId());
        ToastUtil.show(requireContext(), "刷卡成功");
        // 上传考勤信息
        uploadAttendanceInfo(cardEvent.getCardId());
    }


    /**
     * 恢复刷卡功能为普通功能
     */
    private void recoverSwipeCard() {
        SwipeCardContext.getInstance().recovery();
    }

    /**
     * 注册EventBus总线
     */
    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 注销EventBus总线
     */
    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 上传刷卡考勤信息
     * @param cardNumber 卡号
     */
    private void uploadAttendanceInfo(String cardNumber) {
        SwipeCardContext.getInstance().submitAttendance(cardNumber, false);
    }

    /**
     * 考勤结束
     */
    private void attendanceFinish() {
        // 恢复刷卡功能为普通功能
        SwipeCardContext.getInstance().recovery();
    }
}
