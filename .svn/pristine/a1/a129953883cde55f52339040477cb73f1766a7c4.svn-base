package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.CapacityNoControlProperty;

/**
 * 容纳人数组件
 */
public class CapacityNoControl extends PrefixSuffixTextControl<CapacityNoControlProperty> {

    public CapacityNoControl(Context context) {
        super(context);
    }

    public CapacityNoControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public CapacityNoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CapacityNoControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshControlData() {
        //课程表里获取数据 maxCapacityNum
        if (CourseHelper.getDefault().isLoaded()) {
            try {
                setText(CourseHelper.getDefault().getCourseDto().getSu().getMaxCapacityNum());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            setText(null);
    }
}
