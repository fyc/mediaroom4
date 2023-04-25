package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.text.ReserveInfoControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.ReserveInfoResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.control.base.BaseTextControl;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;
import com.sunmnet.mediaroom.brand.view.AutoScrollView;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 教室预约信息
 */
public class ReserveInfoControl extends BaseFrameControl<ReserveInfoControlProperty> {

    AutoScrollView autoScrollView;
    BaseTextControl baseTextControl;

    public ReserveInfoControl(Context context) {
        super(context);
        initView(context);
    }

    public ReserveInfoControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
        initView(context);
    }

    public ReserveInfoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
        getInfo();
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

    private void getInfo() {
        if (DeviceApp.getApp().isRegistered()) {
            ApiManager.getCourseApi().getClassroomReserveInfo(DeviceApp.getApp().getClassroomCode())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleTaskObserver<ReserveInfoResponse>() {
                        @Override
                        public void onNext(ReserveInfoResponse response) {
                            if (response != null && response.isSuccess()) {
                                if (response.getObj() != null) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (ReserveInfoResponse.Result result : response.getObj()) {
                                        stringBuilder.append("预约人：").append(result.getNameCn())
                                                .append("，预约时间：").append(result.getStartTime())
                                                .append("至").append(result.getEndTime()).append("\n");
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
