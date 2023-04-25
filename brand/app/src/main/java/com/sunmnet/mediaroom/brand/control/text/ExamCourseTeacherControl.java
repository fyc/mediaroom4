package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamCourseTeacherControlProperty;

/**
 * 考试-任课老师组件
 */
public class ExamCourseTeacherControl extends AbsExamControl<ExamCourseTeacherControlProperty> {

    public ExamCourseTeacherControl(Context context) {
        super(context);
    }

    public ExamCourseTeacherControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamCourseTeacherControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamCourseTeacherControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamCourseTeacher());
        }
    }

}
