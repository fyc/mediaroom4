package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamLocationControlProperty;

/**
 * 考试地点组件
 */
public class ExamLocationControl extends AbsExamControl<ExamLocationControlProperty> {

    public ExamLocationControl(Context context) {
        super(context);
    }

    public ExamLocationControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamLocationControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamLocationControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamLocation());
        }
    }
}
