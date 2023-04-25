package com.sunmnet.mediaroom.brand.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

public class VolumeReceiver extends BroadcastReceiver {

    public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (VOLUME_CHANGED_ACTION.equals(action)) {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            RunningLog.run("系统音量发生变化，当前媒体音量大小: " + musicVolume);
            int curVolume = TypeUtil.objToInt(DeviceApp.getApp().getSharedPreferencesValue("musicVolume"));
            RunningLog.run("系统音量与班牌存储音量值对比变化量: " + (musicVolume - curVolume));
            if (Math.abs(curVolume - musicVolume) > 0) {
                RunningLog.run("可能非人为改变音量，重新设置班牌存储音量大小: " + curVolume);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
            } else {
                DeviceApp.getApp().saveSharedPreferencesValue("musicVolume", musicVolume + "");
            }
        } else if (BroadcastAction.ACTION_CHANGE_VOLUME.equals(action)) {
            String change = intent.getStringExtra("change");
            if (change == null || (!change.startsWith("+") && !change.startsWith("-")))
                return;
            int value = TypeUtil.objToIntDef(change.trim().substring(1).trim(), -1);
            if (value == 0)
                return;
            if (value == -1)
                value = 1;
            AudioManager audioManager = (AudioManager) DeviceApp.getApp().getSystemService(Context.AUDIO_SERVICE);
            int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (change.startsWith("+")) {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                curVolume = curVolume + value > maxVolume ? maxVolume : curVolume + value;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, AudioManager.FLAG_PLAY_SOUND);
            } else if (change.startsWith("-")) {
                curVolume = curVolume - value >= 0 ? curVolume - value : 0;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, AudioManager.FLAG_PLAY_SOUND);
            }
            DeviceApp.getApp().saveSharedPreferencesValue("musicVolume", curVolume + "");
        }
    }
}
