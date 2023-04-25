package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamCourseControlProperty;

/**
 * 考试时间组件
 */
public class ExamCourseControl extends AbsExamControl<ExamCourseControlProperty> {

    public ExamCourseControl(Context context) {
        super(context);
    }

    public ExamCourseControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamCourseControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamCourseControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamCourse());
        }
    }
}
