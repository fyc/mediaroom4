package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamPeopleControlProperty;

/**
 * 考试人数组件
 */
public class ExamPeopleControl extends AbsExamControl<ExamPeopleControlProperty> {

    public ExamPeopleControl(Context context) {
        super(context);
    }

    public ExamPeopleControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamPeopleControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamPeopleControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExaminees() == null ? "0" : timeTableBean.getExaminees().size() + "");
        }
    }

}
