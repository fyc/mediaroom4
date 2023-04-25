package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamRoomControlProperty;

/**
 * 考场名称组件
 */
public class ExamRoomControl extends AbsExamControl<ExamRoomControlProperty> {

    private String examTimetableId;

    public ExamRoomControl(Context context) {
        super(context);
    }

    public ExamRoomControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamRoomControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamRoomControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            setText(timeTableBean.getExamRoom());
        }
    }
}
