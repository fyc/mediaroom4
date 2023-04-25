package com.sunmnet.mediaroom.common.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sunmnet.mediaroom.common.broadcast.BroadcastAction;
import com.sunmnet.mediaroom.common.broadcast.CourseAlarmBroadcast;
import com.sunmnet.mediaroom.common.tools.CourseHelper;

public class BellService extends Service implements CourseHelper.CourseListener {

    private CourseAlarmBroadcast courseBroadcast;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCourseLoaded() {
        if (courseBroadcast != null) {
            courseBroadcast.setClassTime(CourseHelper.getDefault().getCourseTimeArray());
        }
    }

    public class BellBinder extends Binder {
        public BellService getService() {
            return BellService.this;
        }
    }

    private BellBinder binder = new BellBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        CourseHelper.getDefault().addListener(this);
        courseBroadcast = new CourseAlarmBroadcast(this);
        courseBroadcast.setClassTime(CourseHelper.getDefault().getCourseTimeArray());
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastAction.ACTION_CLASS_PRE_ON);
        filter.addAction(BroadcastAction.ACTION_CLASS_ON);
        filter.addAction(BroadcastAction.ACTION_CLASS_ON_MID);
        filter.addAction(BroadcastAction.ACTION_CLASS_OVER);
        filter.addAction(BroadcastAction.ACTION_MIDNIGHT);
        filter.addAction(BroadcastAction.ACTION_TIME_SET);
        filter.addAction(BroadcastAction.ACTION_RESET_ALARM);
        registerReceiver(courseBroadcast, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        CourseHelper.getDefault().removeListener(this);
        unregisterReceiver(courseBroadcast);
        courseBroadcast = null;
    }
}
