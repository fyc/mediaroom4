package com.sunmnet.mediaroom.common.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.SparseArray;

import java.util.Date;

/**
 * Created by dengzl_pc on 2017/9/21.
 */

public class AlarmUtils {
    static SparseArray<Date> alarms = new SparseArray<>();
    static SparseArray<PendingIntent> pendings = new SparseArray<>();

    public static void registerAlarm(Context context, Intent intent, int intentID, Date date) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intentID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarms.put(intentID, date);
        pendings.put(intentID, pendingIntent);
        registerAlarm(context, date.getTime(), pendingIntent);
    }

    public static void cancelAllAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        synchronized (alarms) {
            int size = pendings.size();
            for (int i = 0; i < size; i++) {
                int key = pendings.keyAt(i);
                alarmManager.cancel(pendings.get(key));
            }
        }
    }

    public static void cancelAlarm(int intentId, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = pendings.get(intentId);
        if (pendingIntent != null) {
            pendings.remove(intentId);
            alarmManager.cancel(pendingIntent);
        }
    }

    /**
     * 重置alarm
     */
    public static void resetAlarm(Context context) {
        synchronized (alarms) {
            int size = alarms.size();
            for (int i = 0; i < size; i++) {
                int key = alarms.keyAt(i);
                Date date = alarms.get(key);
                Date now = new Date();
                if (date.after(now)) {
                    registerAlarm(context, date, pendings.get(key));
                }
            }
        }
    }

    public static void cancelAlarm(Context context, int intentID, Intent intent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intentID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public static void cancelAlarm(Context context, Intent intent, int intentID) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, intentID, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    public static void registerAlarm(Context context, long timeMillions, PendingIntent sender) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

//        //MARSHMALLOW OR ABOVE
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeMillions, sender);
//        }
//        //LOLLIPOP 21 OR ABOVE
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(timeMillions, sender);
//            alarmManager.setAlarmClock(alarmClockInfo, sender);
//        }
//        //KITKAT 19 OR ABOVE
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMillions, sender);
//        }
//        //FOR BELOW KITKAT ALL DEVICES
//        else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillions, sender);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //参数2是开始时间、参数3是允许系统延迟的时间
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMillions, sender);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeMillions, sender);
        }
    }

    public static void registerAlarm(Context context, Date date, PendingIntent intent) {
        registerAlarm(context, date.getTime(), intent);
    }

}
