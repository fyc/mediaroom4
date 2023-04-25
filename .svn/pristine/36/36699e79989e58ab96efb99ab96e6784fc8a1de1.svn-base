package com.sunmnet.mediaroom.brand.data.database.play;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.brand.bean.play.PublishingRule;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public abstract class AbsPublishingRuleDBEntity<T extends PublishingRule> {

    protected Date startTime;
    protected Date endTime;

    public void setData(T rule) {
        setId(rule.getId());
        setName(rule.getName());
        setTimeType(rule.getTimeType());
        setDate(rule.getDate());
        setWeek(rule.getWeek());
        setType(rule.getType());
        setTime(rule.getTime());
        setPlayType(rule.getPlayType());
        setPlayStatus(rule.getPlayStatus());
        setCleanOld(rule.isCleanOld());

        initStartTime();
        initEndTime();
    }

    public abstract T mappingToPublishingRule();

    protected void initEndTime() {
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

    protected void initStartTime() {
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

    public abstract String getId();

    protected abstract void setId(String id);

    public abstract String getName();

    protected abstract void setName(String name);

    public abstract int getTimeType();

    protected abstract void setTimeType(int timeType);

    public abstract String getDate();

    protected abstract void setDate(String date);

    public abstract String getTime();

    protected abstract void setTime(String time);

    public abstract String getWeek();

    protected abstract void setWeek(String week);

    public abstract int getType();

    protected abstract void setType(int type);

    public abstract int getPlayType();

    protected abstract void setPlayType(int playType);

    public abstract int getPlayStatus();

    protected abstract void setPlayStatus(int playStatus);

    public abstract boolean getCleanOld();

    protected abstract void setCleanOld(boolean cleanOld);

    @Nullable
    public Date getEndTime() {
        return endTime;
    }

    @Nullable
    public Date getStartTime() {
        return startTime;
    }
}
