package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.brand.bean.response.ExamDetailResponse;
import com.sunmnet.mediaroom.brand.control.base.AbsExamControl;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.bean.control.text.ExamTimeToStartToEndControlProperty;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * 离考试开始时间、结束时间轮播。
 * 先显示离考试开始时间，考试开始后显示离考试结束的时间。
 */
public class ExamTimeToStartToEndControl extends AbsExamControl<ExamTimeToStartToEndControlProperty> {

    private Date startDate;
    private Date endDate;

    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshTimeText();
        }
    };

    public ExamTimeToStartToEndControl(Context context) {
        super(context);
    }

    public ExamTimeToStartToEndControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ExamTimeToStartToEndControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExamTimeToStartToEndControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void setExamData(ExamDetailResponse.ExamDetail timeTableBean) {
        if (timeTableBean == null) {
            setText(null);
        } else {
            String startTime = timeTableBean.getExamDate() + " " + timeTableBean.getExamTimeStart();
            startDate = DateUtil.parseDateStr(startTime, DateUtil.DEFAULT_FORMAT_DATE + " " + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            String endTime = timeTableBean.getExamDate() + " " + timeTableBean.getExamTimeEnd();
            endDate = DateUtil.parseDateStr(endTime, DateUtil.DEFAULT_FORMAT_DATE + " " + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
        }
    }

    private void refreshTimeText() {
        if (startDate != null && endDate != null) {
            long toStart = startDate.getTime() - System.currentTimeMillis();
            if (toStart > 0) {
                int hour = (int) TimeUnit.MILLISECONDS.toHours(toStart);
                int minute = (int) TimeUnit.MILLISECONDS.toMinutes(toStart - TimeUnit.HOURS.toMillis(hour));
                if (minute == 0)
                    minute = 1;
                StringBuffer stringBuffer = new StringBuffer("离考试开始时间还有");
                if (hour > 0)
                    stringBuffer.append(hour).append("小时");
                stringBuffer.append(minute).append("分钟");
                setText(stringBuffer.toString());
            } else {
                long toEnd = endDate.getTime() - System.currentTimeMillis();
                if (toEnd > 0) {
                    int hour = (int) TimeUnit.MILLISECONDS.toHours(toEnd);
                    int minute = (int) TimeUnit.MILLISECONDS.toMinutes(toEnd - TimeUnit.HOURS.toMillis(hour));
                    if (minute == 0)
                        minute = 1;
                    StringBuffer stringBuffer = new StringBuffer("离考试结束时间还有");
                    if (hour > 0)
                        stringBuffer.append(hour).append("小时");
                    stringBuffer.append(minute).append("分钟");
                    setText(stringBuffer.toString());
                } else {
                    setText("考试已结束");
                }
            }
        } else {
            setText(null);
        }
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
            timer.schedule(new HandlerTimerTask(runnable), 0, 1000);
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
        if (timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
