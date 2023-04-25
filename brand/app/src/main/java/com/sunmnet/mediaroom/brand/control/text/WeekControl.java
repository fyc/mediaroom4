package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.WeekControlProperty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 星期组件
 */
public class WeekControl extends PrefixSuffixTextControl<WeekControlProperty> {

    private String week;//匹配星期，0为自动匹配，1-7对应周一到周日

    private final static Map<Integer,String> weekMap = new HashMap<>();

    static {
        weekMap.put(1, "一");
        weekMap.put(2, "二");
        weekMap.put(3, "三");
        weekMap.put(4, "四");
        weekMap.put(5, "五");
        weekMap.put(6, "六");
        weekMap.put(7, "日");
    }

    public WeekControl(Context context) {
        super(context);
    }

    public WeekControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public WeekControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeekControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshControlData() {
        //根据匹配星期显示数据
        if (TypeUtil.objToInt(week) == 0)
            setText("星期" + weekMap.get(DateUtil.getWeekNo(new Date())));
        else {
            setText("星期" + weekMap.get(TypeUtil.objToInt(week)));
        }
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        week = TypeUtil.objToStrNotNull(attrDataMap.get("week"));
//    }

    @Override
    public void setProperty(WeekControlProperty property) {
        super.setProperty(property);
        week = TypeUtil.objToStrNotNull(property.getAttr().getWeek());
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
