package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.text.BorrowInfoControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.BorrowInfoResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.control.base.BaseTextControl;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;
import com.sunmnet.mediaroom.brand.view.AutoScrollView;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 教室借用信息
 */
public class BorrowInfoControl extends BaseFrameControl<BorrowInfoControlProperty> {

    AutoScrollView autoScrollView;
    BaseTextControl baseTextControl;

    public BorrowInfoControl(Context context) {
        super(context);
        initView(context);
    }

    public BorrowInfoControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
        initView(context);
    }

    public BorrowInfoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        baseTextControl = new BaseTextControl(context, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ControlTextHelper.setControlTextStyle(baseTextControl, property.getStyle());

        autoScrollView = new AutoScrollView(context);
        autoScrollView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        autoScrollView.setAutoToScroll(false);
        autoScrollView.addView(baseTextControl);
        addView(autoScrollView);
    }

    @Override
    public void refreshControlData() {
        getBorrowInfo();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        autoScrollView.startAutoScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        autoScrollView.stopAutoScroll();
    }

    private void getBorrowInfo() {
        if (DeviceApp.getApp().isRegistered()) {
            ApiManager.getCourseApi().getClassroomBorrowInfo(DeviceApp.getApp().getClassroomCode())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleTaskObserver<BorrowInfoResponse>() {
                        @Override
                        public void onNext(BorrowInfoResponse response) {
                            if (response != null && response.isSuccess()) {
                                if (response.getObj() != null) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (BorrowInfoResponse.Result result : response.getObj()) {
                                        /*申请人：李志勇
                                        申请类型：班级活动
                                        申请事由：班级活动
                                        开始时间：2018-11-20 09:30
                                        结束时间：2018-11-20 10:30*/
                                        stringBuilder.append("申请人：").append(result.getApplicantName()).append("\n")
                                                .append("申请类型：").append(result.getAppointmentTypeText()).append("\n")
                                                .append("申请事由：").append(result.getCause()).append("\n")
                                                .append("开始时间：").append(result.getStartTime()).append("\n")
                                                .append("结束时间：").append(result.getEndTime()).append("\n\n");
                                    }
                                    baseTextControl.setText(stringBuilder.toString());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }
}
