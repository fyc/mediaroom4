package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.text.ArriveNoControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.IntegerResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.AbsAttendanceControl;

import io.reactivex.Observable;

/**
 * 实到人数组件
 */
public class ArriveNoControl extends AbsAttendanceControl<ArriveNoControlProperty> {

    public ArriveNoControl(Context context) {
        super(context);
    }

    public ArriveNoControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ArriveNoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArriveNoControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Observable<IntegerResponse> getRequestObservable(String classNo) {
        return ApiManager.getCourseApi().getAttendanceArriveNo(DeviceApp.getApp().getClassroomCode(), classNo);
    }
}
