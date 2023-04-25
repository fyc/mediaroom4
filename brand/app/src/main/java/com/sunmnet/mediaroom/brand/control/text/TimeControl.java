package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.control.base.PrefixSuffixTextControl;
import com.sunmnet.mediaroom.brand.bean.control.text.TimeControlProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;

/**
 * 时间组件
 */
public class TimeControl extends PrefixSuffixTextControl<TimeControlProperty> {

    private String timeType;//12H 12小时制， 24H 24小时制

    private String format12H = "hh:mm:ss a";
    private String format24H = "HH:mm:ss";

    private SimpleDateFormat dateFormat12H;
    private SimpleDateFormat dateFormat24H;

    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshControlData();
        }
    };


    public TimeControl(Context context) {
        super(context);
    }

    public TimeControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public TimeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshControlData() {
        if ("12H".equals(timeType)) {
            if (dateFormat12H == null)
                dateFormat12H = new SimpleDateFormat(format12H, Locale.getDefault());
            setText(dateFormat12H.format(Calendar.getInstance().getTime()));
        } else {
            if (dateFormat24H == null)
                dateFormat24H = new SimpleDateFormat(format24H, Locale.getDefault());
            setText(dateFormat24H.format(Calendar.getInstance().getTime()));
        }
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        timeType = TypeUtil.objToStrNotNull(attrDataMap.get("timeType"));
//    }

    @Override
    public void setProperty(TimeControlProperty property) {
        super.setProperty(property);
        timeType = TypeUtil.objToStrNotNull(property.getAttr().getTimeType());
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onControlShow();
    }

    @Override
    public void onControlShow() {
        if (timer != null)
            onControlHide();
        try {
            timer = new Timer();
            timer.schedule(new HandlerTimerTask(runnable), 0, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onControlHide();
    }

    @Override
    public void onControlHide() {
        if(timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
