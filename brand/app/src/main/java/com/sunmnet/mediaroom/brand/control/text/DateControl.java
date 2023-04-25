package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.DateControlProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期组件
 */
public class DateControl extends PrefixSuffixTextControl<DateControlProperty> {

    private String dateFormat;

    public DateControl(Context context) {
        super(context);
    }

    public DateControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public DateControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DateControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        if (dateFormat == null) {
            dateFormat = "yyyy.MM.dd";
        }
        super.init();
    }

    @Override
    public void refreshControlData() {
        //更新日期文本，获取系统时间，根据dateFormat格式化文本
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        setText(simpleDateFormat.format(new Date()));
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        dateFormat = TypeUtil.objToStrNotNull(attrDataMap.get("dateFormat"));
//    }

    @Override
    public void setProperty(DateControlProperty property) {
        super.setProperty(property);
        dateFormat = TypeUtil.objToStrNotNull(property.getAttr().getDateFormat());
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
