package com.sunmnet.mediaroom.brand.impl.play;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.brand.interfaces.play.IPlayable;
import com.sunmnet.mediaroom.brand.bean.play.PublishingRule;

import java.util.Calendar;
import java.util.Date;

public abstract class AbstractPlayable<T extends PublishingRule> implements IPlayable<T> {

    protected PlayState playState;

    @Override
    public abstract void startPlay();

    @Override
    public abstract void stopPlay();

    @Override
    public void delete(){

    }

    @Override
    public boolean isAlive() {
        if (getRulePlayStatus() != PublishingRule.PLAY_STATUS_PLAY)
            return false;
        if (getRule().getTimeType() == PublishingRule.TIME_TYPE_ALL_DAY)
            return true;
        if (new Date().before(getRule().getEndTime()))
            return true;
        return false;
    }

    @Override
    public abstract boolean readyPlay();

    @Override
    public boolean isPlaying() {
        return playState == PlayState.PLAYING;
    }

    @Override
    public String getPlayId() {
        return getRule().getId();
    }

    @Override
    public int getType() {
        return getRule().getType();
    }

    @Override
    public boolean isInterCut() {
        return getRule().getPlayType() == PublishingRule.PLAY_TYPE_INTER_CUT;
    }

    @Override
    public abstract T getRule();

    @Override
    public boolean isWithinPlayTime() {
        if (getRule().getTimeType() == PublishingRule.TIME_TYPE_ALL_DAY)
            return true;
        if (DateUtil.isBetween(new Date(), getRule().getStartTime(), getRule().getEndTime())) {
            String[] weekArr = getRule().getWeek().split(",");
            String weekNoNow = DateUtil.getWeekNoNow() + "";
            boolean withinToday = false;//是否在今天内
            for (String weekNo : weekArr) {
                if (TextUtils.equals(weekNo, weekNoNow)) {
                    withinToday = true;
                    break;
                }
            }
            if (!withinToday)
                return false;
            String[] timeArr = getRule().getTime().split("-");
            Date now = new Date();
            String ymd = DateUtil.formatDate(now, DateUtil.DEFAULT_FORMAT_DATE);
            Date start = DateUtil.parseDateStr(ymd + timeArr[0], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            Date end = DateUtil.parseDateStr(ymd + timeArr[1], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            return DateUtil.isBetween(now, start, end);
        }
        return false;
    }

    @Override
    public PlayState getState() {
        return playState;
    }

    @Override
    public int getRulePlayStatus() {
        return getRule().getPlayStatus();
    }

    @Override
    public Date getPlayStartTime() {
        Calendar calendar = Calendar.getInstance();
        if (getRulePlayStatus() != PublishingRule.PLAY_STATUS_PLAY) {
            calendar.setTimeInMillis(Long.MAX_VALUE);
            return calendar.getTime();
        }
        if (getRule().getTimeType() == PublishingRule.TIME_TYPE_ALL_DAY) {
            calendar.setTimeInMillis(0);
            return calendar.getTime();
        }
        if (isWithinPlayTime()) {
            String[] timeArr = getRule().getTime().split("-");
            String ymd = DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT_DATE);
            Date start = DateUtil.parseDateStr(ymd + timeArr[0], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            return start;
        } else {
            if(getRule().getWeek()==null){
                calendar.setTimeInMillis(Long.MAX_VALUE);
                return calendar.getTime();
            }
            String[] weekArr = getRule().getWeek().split(",");
            int weekNo = DateUtil.getWeekNoNow();
            int i = 1;//从第二天开始计算
            boolean ok = false;

            //先判断今天是否有播放时间
            for (String weekNo2 : weekArr) {
                if (TextUtils.equals(weekNo2, weekNo + "")) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                Date date = calendar.getTime();
                String[] timeArr = getRule().getTime().split("-");
                String ymd = DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT_DATE);
                Date end = DateUtil.parseDateStr(ymd + timeArr[1], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
                Date now = new Date();
                //已经过了今天播放时间
                if (now.after(end)) {
                    ok = false;
                } else {
                    return DateUtil.parseDateStr(ymd + timeArr[0], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
                }
            }

            for (; i <= 7; i++, weekNo++) {//查找下个播放时间段的播放开始时间
                weekNo = weekNo % 8;
                weekNo = weekNo == 0 ? 1 : weekNo;
                for (String weekNo2 : weekArr) {
                    if (TextUtils.equals(weekNo2, weekNo + "")) {
                        ok = true;
                        break;
                    }
                }
                if (ok)
                    break;
            }
            if (!ok) {
                calendar.setTimeInMillis(Long.MAX_VALUE);
                return calendar.getTime();
            }
            Date date = calendar.getTime();
            date.setDate(date.getDate() + i);
            String[] timeArr = getRule().getTime().split("-");
            String ymd = DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT_DATE);
            Date start = DateUtil.parseDateStr(ymd + timeArr[0], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            if (start != null && start.before(getRule().getEndTime())) {
                return start;
            } else {
                calendar.setTimeInMillis(Long.MAX_VALUE);
                return calendar.getTime();
            }
        }
    }

    @Override
    public Date getPlayEndTime() {
        Calendar calendar = Calendar.getInstance();
        if (getRulePlayStatus() != PublishingRule.PLAY_STATUS_PLAY) {
            calendar.setTimeInMillis(0);
            return calendar.getTime();
        }
        if (getRule().getTimeType() == PublishingRule.TIME_TYPE_ALL_DAY) {
            calendar.setTimeInMillis(Long.MAX_VALUE);
            return calendar.getTime();
        }
        if (isWithinPlayTime()) {//正好在播放时间段里
            String[] timeArr = getRule().getTime().split("-");
            String ymd = DateUtil.getNowDateString(DateUtil.DEFAULT_FORMAT_DATE);
            Date end = DateUtil.parseDateStr(ymd + timeArr[1], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            return end;
        } else {//不在播放时间段里
            if (getRule().getWeek() == null) {
                calendar.setTimeInMillis(0);
                return calendar.getTime();
            }
            String[] weekArr = getRule().getWeek().split(",");
            int weekNo = DateUtil.getWeekNoNow();
            int i = 1;
            boolean ok = false;

            //先判断今天是否有播放时间
            for (String weekNo2 : weekArr) {
                if (TextUtils.equals(weekNo2, weekNo + "")) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                Date date = calendar.getTime();
                String[] timeArr = getRule().getTime().split("-");
                String ymd = DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT_DATE);
                Date end = DateUtil.parseDateStr(ymd + timeArr[1], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
                Date now = new Date();
                //已经过了今天播放时间
                if (now.after(end)) {
                    ok = false;
                } else {
                    return end;
                }
            }

            for (; i <= 7; i++, weekNo++) {//查找下个播放时间段的播放结束时间
                weekNo = weekNo % 8;
                weekNo = weekNo == 0 ? 1 : weekNo;
                for (String weekNo2 : weekArr) {
                    if (TextUtils.equals(weekNo2, weekNo + "")) {
                        ok = true;
                        break;
                    }
                }
                if (ok)
                    break;
            }
            if (!ok) {
                calendar.setTimeInMillis(Long.MAX_VALUE);
                return calendar.getTime();
            }
            Date date = calendar.getTime();
            date.setDate(date.getDate() + i);
            String[] timeArr = getRule().getTime().split("-");
            String ymd = DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT_DATE);
            Date end = DateUtil.parseDateStr(ymd + timeArr[1], DateUtil.DEFAULT_FORMAT_DATE + DateUtil.DEFAULT_FORMAT_HOUR_MINUTE);
            if (end != null && end.before(getRule().getEndTime())) {
                return end;
            } else {
                calendar.setTimeInMillis(0);
                return calendar.getTime();
            }
        }
    }
}
