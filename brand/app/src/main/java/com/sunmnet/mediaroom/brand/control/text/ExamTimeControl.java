package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamTimeControlProperty;

/**
 * 考试时间组件
 */
public class ExamTimeControl extends AbsExamControl<ExamTimeControlProperty> {

    public ExamTimeControl(Context context) {
        super(context);
    }

    public ExamTimeControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamTimeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamTimeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamTimeStart() + "-" + timeTableBean.getExamTimeEnd());
        }
    }
}
