package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.text.ShouldArriveNoControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.IntegerResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.AbsAttendanceControl;

import io.reactivex.Observable;

/**
 * 应到人数组件
 */
public class ShouldArriveNoControl extends AbsAttendanceControl<ShouldArriveNoControlProperty> {

    public ShouldArriveNoControl(Context context) {
        super(context);
    }

    public ShouldArriveNoControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ShouldArriveNoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShouldArriveNoControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Observable<IntegerResponse> getRequestObservable(String classNo) {
        return ApiManager.getCourseApi().getAttendanceShouldArriveNo(DeviceApp.getApp().getClassroomCode(), classNo);
    }
}
