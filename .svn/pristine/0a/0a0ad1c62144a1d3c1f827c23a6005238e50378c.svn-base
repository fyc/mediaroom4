package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamClassControlProperty;

/**
 * 考试班级组件
 */
public class ExamClassControl extends AbsExamControl<ExamClassControlProperty> {

    public ExamClassControl(Context context) {
        super(context);
    }

    public ExamClassControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamClassControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamClassControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamClass());
        }
    }
}
