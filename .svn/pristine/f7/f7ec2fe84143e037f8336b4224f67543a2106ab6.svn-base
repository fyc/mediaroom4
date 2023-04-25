package com.sunmnet.mediaroom.brand.bean.play;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.DateUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PublishingRule implements Serializable {

    public static final int TYPE_PROGRAM = 0;
    public static final int TYPE_NOTIFICATION = 1;
    public static final int TYPE_TEMPLATE_PROGRAM = 3;

    public static final int TIME_TYPE_ALL_DAY = 0;
    public static final int TIME_TYPE_RULE = 1;

    public static final int PLAY_TYPE_NORMAL = 0;
    public static final int PLAY_TYPE_INTER_CUT = 1;

    public static final int PLAY_STATUS_PLAY = 1;
    public static final int PLAY_STATUS_STOP = 0;


    private String id;
    private String name;
    private int timeType;
    private String date;
    private String time;
    private String week;
    private int type;
    private int playType;
    private int playStatus;
    private boolean cleanOld;

    protected Date startTime;
    protected Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public int getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }

    public boolean isCleanOld() {
        return cleanOld;
    }

    public void setCleanOld(boolean cleanOld) {
        this.cleanOld = cleanOld;
    }

    public void initEndTime() {
        if (getTimeType() == PublishingRule.TIME_TYPE_ALL_DAY) {
            endTime = Calendar.getInstance().getTime();
            endTime.setTime(Long.MAX_VALUE);
        } else if (getTimeType() == PublishingRule.TIME_TYPE_RULE) {
            if (TextUtils.isEmpty(getDate()) || TextUtils.isEmpty(getTime()))
                return;
            String endDate = getDate().split("-")[1];
            endDate = endDate.replace(".", "-");
            String tt = endDate + " " + getTime().split("-")[1];
            endTime = DateUtil.parseDateStr(tt, "yyyy-MM-dd HH:mm");
            if (endTime.before(this.getStartTime())) {
                this.endTime.setTime(endTime.getTime() + TimeUnit.DAYS.toMillis(1));
            }
        }
    }

    public void initStartTime() {
        if (getTimeType() == PublishingRule.TIME_TYPE_ALL_DAY) {
            startTime = Calendar.getInstance().getTime();
        } else if (getTimeType() == PublishingRule.TIME_TYPE_RULE) {
            if (TextUtils.isEmpty(getDate()) || TextUtils.isEmpty(getTime()))
                return;
            String s = getDate().split("-")[0];
            String tt = s + " " + getTime().split("-")[0];
            startTime = DateUtil.parseDateStr(tt, "yyyy.MM.dd HH:mm");
        }
    }

    @Nullable
    public Date getEndTime() {
        if (endTime == null) {
            initEndTime();
        }
        return endTime;
    }

    @Nullable
    public Date getStartTime() {
        if (startTime == null) {
            initStartTime();
        }
        return startTime;
    }
}
