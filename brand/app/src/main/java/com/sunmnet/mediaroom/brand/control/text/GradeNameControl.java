package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.GradeNameControlProperty;

import java.util.Date;

/**
 * 班级组件
 */
public class GradeNameControl extends PrefixSuffixTextControl<GradeNameControlProperty> {


    private String classNo;//匹配课节，0为自动匹配，其余为匹配对应课节，双课节用逗号隔开
    private String week;//匹配星期，0为自动匹配，1-7对应周一到周日

    public GradeNameControl(Context context) {
        super(context);
    }

    public GradeNameControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public GradeNameControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradeNameControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        if (classNo == null) {
            classNo = "0";
        }
        if (week == null) {
            week = "0";
        }
        super.init();
    }

    @Override
    public void refreshControlData() {
        //根据匹配星期和课节从课程表获取上课的班级
        if (CourseHelper.getDefault().isLoaded()) {
            CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(new Date(), TypeUtil.objToInt(week), classNo, CourseConfig.PRE_START_TIME);
            if (courseSchedule != null) {
                setText(TextUtils.isEmpty(courseSchedule.getGradeName()) ? "无" : courseSchedule.getGradeName());
            } else
                setText(null);
        } else
            setText(null);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        classNo = TypeUtil.objToStrNotNull(attrDataMap.get("classNo"));
//        week = TypeUtil.objToStrNotNull(attrDataMap.get("week"));
//    }

    @Override
    public void setProperty(GradeNameControlProperty property) {
        super.setProperty(property);
        classNo = TypeUtil.objToStrNotNull(property.getAttr().getClassNo());
        week = TypeUtil.objToStrNotNull(property.getAttr().getWeek());
    }

    public String getClassNo() {
        return classNo;
    }

    public String getWeek() {
        return week;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
