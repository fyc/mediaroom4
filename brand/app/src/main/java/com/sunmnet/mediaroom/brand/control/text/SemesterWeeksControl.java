package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.bean.control.text.SemesterWeeksControlProperty;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 学周组件
 */
public class SemesterWeeksControl extends PrefixSuffixTextControl<SemesterWeeksControlProperty> {

    private final static Map<Integer, String> integerStringMap = new HashMap<Integer, String>();

    private String weeks;//教学周，0为自动匹配当前学周，其余为固定学周


    public SemesterWeeksControl(Context context) {
        super(context);
    }

    public SemesterWeeksControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public SemesterWeeksControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SemesterWeeksControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        if (weeks == null) {
            weeks = "0";
        }
        super.init();
    }

    @Override
    public void refreshControlData() {
        if (CourseHelper.getDefault().isLoaded()) {
            if (TypeUtil.objToInt(weeks) == 0) {
                //自动匹配
                int week = CourseHelper.getDefault().getWeekOfSemester(new Date());
                if (week > 0) {
                    setText(week + "");
                } else{
                    setText(null);
                }
            } else {//固定周数
                setText(weeks);
            }
        } else
            setText(null);
    }


    @Override
    public void setProperty(SemesterWeeksControlProperty property) {
        super.setProperty(property);
        weeks = property.getAttr().getWeeks();
    }

}
