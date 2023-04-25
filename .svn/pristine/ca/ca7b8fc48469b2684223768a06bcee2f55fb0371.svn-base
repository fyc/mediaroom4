package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.text.LeaveNoControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.IntegerResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.AbsAttendanceControl;

import io.reactivex.Observable;

/**
 * 请假人数组件
 */
public class LeaveNoControl extends AbsAttendanceControl<LeaveNoControlProperty> {

    public LeaveNoControl(Context context) {
        super(context);
    }

    public LeaveNoControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public LeaveNoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LeaveNoControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Observable<IntegerResponse> getRequestObservable(String classNo) {
        return ApiManager.getCourseApi().getAttendanceLeaveNo(DeviceApp.getApp().getClassroomCode(), classNo);
    }
}
