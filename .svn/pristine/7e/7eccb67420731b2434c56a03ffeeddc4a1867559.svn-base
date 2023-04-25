package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ClassroomControlProperty;
import com.sunmnet.mediaroom.common.bean.DeviceInfo;

/**
 * 教室组件
 */
public class ClassroomControl extends PrefixSuffixTextControl<ClassroomControlProperty> {

    public ClassroomControl(Context context) {
        super(context);
    }

    public ClassroomControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ClassroomControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClassroomControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshControlData() {
        //根据注册信息获取数据
        DeviceInfo device = DeviceApp.getApp().getDevice();
        if (device != null && !TextUtils.isEmpty(device.getClassroomName())) {
            setText(device.getClassroomName());
        } else
            setText(null);
    }
}
