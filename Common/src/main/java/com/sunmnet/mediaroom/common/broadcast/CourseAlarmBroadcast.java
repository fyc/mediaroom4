package com.sunmnet.mediaroom.common.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;

import com.sunmnet.mediaroom.common.R;
import com.sunmnet.mediaroom.common.bean.course.CourseTime;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.enums.TimeEventType;
import com.sunmnet.mediaroom.common.events.TimeEvent;
import com.sunmnet.mediaroom.common.tools.AlarmUtils;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class CourseAlarmBroadcast extends BroadcastReceiver {
    private SparseArray<CourseTime> courseTimeArray;
    private int curClassNo;
    private Context context;

    public CourseAlarmBroadcast(Context context) {
        this.context = context;
        registerMidnight();
    }

    private void registerMidnight() {
        AlarmUtils.cancelAlarm(R.id.MID_NIGHT, this.context);
        Date date = new Date();
        //+1天
        date.setTime(date.getTime() + TimeUnit.DAYS.toMillis(1));
        //取午夜时间
        date = DateUtil.getMidnightDate(date);
        //+1S
        date.setTime(date.getTime() + TimeUnit.SECONDS.toMillis(1));

        RunningLog.debug("午夜铃声:" + DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT));

        Intent intent = new Intent(BroadcastAction.ACTION_MIDNIGHT);
        AlarmUtils.registerAlarm(context, intent, R.id.MID_NIGHT, date);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BroadcastAction.ACTION_MIDNIGHT.equals(action)) {
            RunningLog.debug("午夜铃声，准备重新加载第二天的课程");
            setTimeRings();
            registerMidnight();
            EventBus.getDefault().post(new TimeEvent(TimeEventType.MIDNIGHT, null));
        } else if (BroadcastAction.ACTION_TIME_SET.equals(action)
                || BroadcastAction.ACTION_RESET_ALARM.equals(action)) {
            RunningLog.debug("时间改变，重新注册上下课铃声");
            setTimeRings();
            registerMidnight();
            EventBus.getDefault().post(new TimeEvent(TimeEventType.RESET, null));
        } else if (BroadcastAction.ACTION_CLASS_PRE_ON.equals(action)) {
            RunningLog.debug("上课预备铃，即将上课:第" + curClassNo + "节");
            int classN = isOnClass(this.curClassNo);
            if (classN == -1) {
                EventBus.getDefault().post(new TimeEvent(TimeEventType.CLASS_PRE_ON, curClassNo + ""));
            }
        } else if (BroadcastAction.ACTION_CLASS_ON.equals(action)) {
            RunningLog.debug("上课铃，当前课节:第" + curClassNo + "节");
            int classN = isOnClass(this.curClassNo);
            if (classN != -1) {
                registerClassOverAlarm(curClassNo);
                EventBus.getDefault().post(new TimeEvent(TimeEventType.CLASS_ON, curClassNo + ""));
            }
        } else if (BroadcastAction.ACTION_CLASS_ON_MID.equals(action)) {
            RunningLog.debug("上课课间铃声，当前课节:第" + curClassNo + "节");
            int classN = isOnClass(this.curClassNo);
            if (classN != -1) {
                EventBus.getDefault().post(new TimeEvent(TimeEventType.CLASS_ON_MID, curClassNo + ""));
            }
        } else if (BroadcastAction.ACTION_CLASS_OVER.equals(action)) {
            RunningLog.debug("下课铃，第" + curClassNo + "节下课了");
            int classN = isOnClass(this.curClassNo);
            if (classN == -1) {
                EventBus.getDefault().post(new TimeEvent(TimeEventType.CLASS_OVER, curClassNo + ""));
            }
            curClassNo += 1;
            CourseTime time = courseTimeArray.get(curClassNo);
            RunningLog.debug("下课休息中，下一节课:第" + curClassNo + "节");
            if (time != null) {
                String dateStr = DateUtil.formatDate(new Date(), DateUtil.DEFAULT_FORMAT_DATE);
                registerClassOnAlarm(curClassNo, time.getStartTime(dateStr));
            } else if (curClassNo > courseTimeArray.keyAt(courseTimeArray.size() - 1)) {
                RunningLog.debug("没有下一节课时间，注册第二天上课闹钟");
                registerTomorrow();
            }
        }
    }


    private int isOnClass(int classNo) {
        try {
            if (this.courseTimeArray != null && this.courseTimeArray.indexOfKey(classNo) >= 0) {
                CourseTime time = this.courseTimeArray.get(classNo);
                Date now = new Date();
                String dtFormate = DateUtil.formatDate(now, DateUtil.DEFAULT_FORMAT_DATE);
                Date end = DateUtil.parseDateStr(dtFormate + " " + time.getEnd() + ":00", DateUtil.DEFAULT_FORMAT);
                Date start = DateUtil.parseDateStr(dtFormate + " " + time.getStart() + ":00", DateUtil.DEFAULT_FORMAT);
                if (now.after(start) && now.before(end)) {
                    return classNo;
                }
            }

        } catch (Exception e) {
            RunningLog.error(e);
        }
        return -1;
    }

    public void setClassTime(SparseArray<CourseTime> times) {
        this.courseTimeArray = times;
        setTimeRings();
    }

    private void registerClassOverAlarm(int classNo) {
        AlarmUtils.cancelAlarm(R.id.CLASS_OVER_CLOCK, context);
        CourseTime courseTime = courseTimeArray.get(classNo);
        if (courseTime == null) {
            RunningLog.run("无该课节的课节时间，停止注册下课铃声：" + classNo);
            return;
        }
        Date now = new Date();
        String dtFormate = DateUtil.formatDate(now, DateUtil.DEFAULT_FORMAT_DATE);
        Date end = DateUtil.parseDateStr(dtFormate + " " + courseTime.getEnd() + ":00", DateUtil.DEFAULT_FORMAT);
        Intent intent = new Intent(BroadcastAction.ACTION_CLASS_OVER);
        StringBuffer buffer = new StringBuffer();
        buffer.append("正在注册下课广播，当前上课课程:");
        buffer.append("第").append(classNo).append("节");
        buffer.append("下课时间:" + DateUtil.formatDate(end, DateUtil.DEFAULT_FORMAT));
        RunningLog.run(buffer.toString());
        AlarmUtils.registerAlarm(context, intent, R.id.CLASS_OVER_CLOCK, end);
    }

    private void registerClassOnAlarm(int classNo, Date date) {
        AlarmUtils.cancelAlarm(R.id.CLASS_ON_CLOCK, context);
        Intent intent = new Intent(BroadcastAction.ACTION_CLASS_ON);
        StringBuffer buffer = new StringBuffer();
        buffer.append("正在注册上课广播:");
        buffer.append("第").append(classNo).append("节");
        buffer.append("上课时间:" + DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT));
        RunningLog.run(buffer.toString());
        AlarmUtils.registerAlarm(context, intent, R.id.CLASS_ON_CLOCK, date);

        registerClassPreOnAlarm(classNo, new Date(date.getTime() - CourseConfig.PRE_START_TIME));
        registerClassOnMidAlarm(classNo, new Date(date.getTime() + CourseConfig.SUF_START_TIME));
    }

    /**
     * 设置下一节课的时间改变事件
     */
    private void setTimeRings() {
        AlarmUtils.cancelAlarm(R.id.CLASS_ON_CLOCK, this.context);
        AlarmUtils.cancelAlarm(R.id.CLASS_OVER_CLOCK, this.context);
        AlarmUtils.cancelAlarm(R.id.CLASS_PRE_ON_CLOCK, this.context);
        AlarmUtils.cancelAlarm(R.id.CLASS_ON_QUARTER_CLOCK, this.context);
        if (courseTimeArray != null) {
            Date now = new Date();
            String dateStr = DateUtil.formatDate(now, "yyyy-MM-dd");
            boolean hasClass = false;//当天是否还有课
            for (int i = 0; i < courseTimeArray.size(); i++) {
                CourseTime courseTime = courseTimeArray.valueAt(i);
                int classNo = TypeUtil.objToInt(courseTime.getClassNo());
                Date start = courseTime.getStartTime(dateStr);
                Date end = courseTime.getEndTime(dateStr);
                if (now.getTime() >= start.getTime()) {
                    //当前时间在该课节上课时间之后
                    if (now.before(end)) {
                        //当前时间在该课节下课时间之前
                        //上课中
                        this.curClassNo = classNo;
                        RunningLog.run("当前正在上课:" + classNo);
                        EventBus.getDefault().post(new TimeEvent(TimeEventType.CLASS_ON, classNo + ""));
                        registerClassOverAlarm(classNo);
                        hasClass = true;
                        break;
                    }
                } else {
                    //当前时间在该课节上课时间之前
                    //课间休息
                    RunningLog.run("课间休息，下一节课:" + classNo);
                    this.curClassNo = classNo;
                    registerClassOnAlarm(classNo, start);
                    hasClass = true;
                    break;
                }
            }
            if (!hasClass) {
                RunningLog.run("当天已经无课，注册第二天的上课铃声");
                registerTomorrow();
            }
        }
    }

    private void registerTomorrow() {
        Date now = new Date();
        now.setTime(now.getTime() + TimeUnit.DAYS.toMillis(1));
        String dateStr = DateUtil.formatDate(now, "yyyy-MM-dd");
        CourseTime nowCourse = courseTimeArray.get(1);
        if (nowCourse != null) {
            curClassNo = 1;
            registerClassOnAlarm(1, nowCourse.getStartTime(dateStr));
        }
    }

    //上课前5分钟
    private void registerClassPreOnAlarm(int classNo, Date date) {
        AlarmUtils.cancelAlarm(R.id.CLASS_PRE_ON_CLOCK, context);
        if (System.currentTimeMillis() > date.getTime())
            return;
        Intent intent = new Intent(BroadcastAction.ACTION_CLASS_PRE_ON);
        StringBuffer buffer = new StringBuffer();
        buffer.append("正在注册上课预备铃广播:");
        buffer.append("第" + classNo + "节");
        buffer.append("上课预备铃时间:" + DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT));
        RunningLog.run(buffer.toString());
        AlarmUtils.registerAlarm(context, intent, R.id.CLASS_PRE_ON_CLOCK, date);
    }

    //上课中课间
    private void registerClassOnMidAlarm(int classNo, Date date) {
        AlarmUtils.cancelAlarm(R.id.CLASS_ON_QUARTER_CLOCK, context);
        if (System.currentTimeMillis() > date.getTime())
            return;
        Intent intent = new Intent(BroadcastAction.ACTION_CLASS_ON_MID);
        StringBuffer buffer = new StringBuffer();
        buffer.append("正在注册上课后15分钟广播:");
        buffer.append("第" + classNo + "节");
        buffer.append("上课后15分钟时间:" + DateUtil.formatDate(date, DateUtil.DEFAULT_FORMAT));
        RunningLog.run(buffer.toString());
        AlarmUtils.registerAlarm(context, intent, R.id.CLASS_ON_QUARTER_CLOCK, date);
    }

}
