package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamDateControlProperty;

/**
 * 考试日期组件 格式如5月10日
 */
public class ExamDateControl extends AbsExamControl<ExamDateControlProperty> {

    public ExamDateControl(Context context) {
        super(context);
    }

    public ExamDateControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamDateControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamDateControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamDate());
        }
    }

}
