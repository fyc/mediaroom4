package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamRoundControlProperty;

/**
 * 考试场次组件
 */
public class ExamRoundControl extends AbsExamControl<ExamRoundControlProperty> {

    public ExamRoundControl(Context context) {
        super(context);
    }

    public ExamRoundControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamRoundControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamRoundControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamRound());
        }
    }
}
