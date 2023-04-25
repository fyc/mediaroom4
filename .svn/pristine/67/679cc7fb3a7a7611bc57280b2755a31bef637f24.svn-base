package com.sunmnet.mediaroom.brand.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sunmnet.mediaroom.brand.PlayableManager;
import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.service.TimedTaskService;

import org.greenrobot.eventbus.EventBus;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BroadcastAction.ACTION_ALARM_START_PLAY_PROGRAM.equals(action)) {
            PlayableManager.getInstance().initPlayingProgram();
            PlayableManager.getInstance().registerNextPlayProgramAlarm();
            return;
        }
        if (BroadcastAction.ACTION_ALARM_START_PLAY_NOTIFICATION.equals(action)) {
            PlayableManager.getInstance().initPlayingNotification();
            PlayableManager.getInstance().registerNextPlayNotificationAlarm();
            return;
        }
        if (BroadcastAction.ACTION_ALARM_STOP_PLAY_PROGRAM.equals(action)
                || BroadcastAction.ACTION_ALARM_STOP_PLAY_NOTIFICATION.equals(action)) {
            int type = intent.getIntExtra("type", -1);
            String id = intent.getStringExtra("id");
            PlayableManager.getInstance().stopPlay(id, type);
            PlayableManager.getInstance().initPlayingProgram();
            PlayableManager.getInstance().registerNextPlayProgramAlarm();
            PlayableManager.getInstance().initPlayingNotification();
            PlayableManager.getInstance().registerNextPlayNotificationAlarm();
            return;
        }
        if (BroadcastAction.ACTION_TIMED_TASK.equals(action)) {
            Intent i = new Intent(context, TimedTaskService.class);
            context.startService(i);
            return;
        }
        if (BroadcastAction.ACTION_REFRESH_CONTROL.equals(action)) {
            EventBus.getDefault().post(new RefreshControlEvent());
            return;
        }
    }
}
