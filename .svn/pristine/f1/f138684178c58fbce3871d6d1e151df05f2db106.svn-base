package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.CourseTimeControlProperty;

import java.util.Date;

/**
 * 课程时间组件
 */
public class CourseTimeControl extends PrefixSuffixTextControl<CourseTimeControlProperty> {

    private String classNo;//匹配课节，0为自动匹配，其余为匹配对应课节，双课节用逗号隔开

    public CourseTimeControl(Context context) {
        super(context);
    }

    public CourseTimeControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public CourseTimeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CourseTimeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        if (classNo == null) {
            classNo = "0";
        }
        super.init();
    }

    @Override
    public void refreshControlData() {
        //根据匹配课节从课程表获取数据
        if (CourseHelper.getDefault().isLoaded()) {
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), 0, classNo, CourseConfig.PRE_START_TIME);
            if (courseSchedule != null) {
                setText(courseSchedule.getCourseTime());
            } else
                setText(null);
        } else
            setText(null);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        classNo = TypeUtil.objToStrNotNull(attrDataMap.get("classNo"));
//    }

    @Override
    public void setProperty(CourseTimeControlProperty property) {
        super.setProperty(property);
        classNo = TypeUtil.objToStrNotNull(property.getAttr().getClassNo());
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getClassNo() {

        return classNo;
    }
}
