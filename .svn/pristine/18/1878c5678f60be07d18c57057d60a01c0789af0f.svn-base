package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.PlayableManager;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.SysBuildHelper;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import org.apache.mina.core.session.IoSession;

public class DebugProtocol implements ProtocolHandler<SocketMessage, String> {


    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        if (message == null || TextUtils.isEmpty(message.getMsg()))
            return SocketResult.fail();
        String cmd = message.getMsg();
        if (cmd.trim().startsWith("open adb")) {
            BrandDevice.getInstance().openAdb();
            return SocketResult.success("open adb successed");
        }
        if (cmd.trim().startsWith("close adb")) {
            BrandDevice.getInstance().closeAdb();
            return SocketResult.success("close adb successed");
        }
        if (cmd.trim().startsWith("clean")) {
            String args = cmd.trim().substring(5);
            String[] prams = args.split(" ");
            for (String param : prams) {
                if (TextUtils.equals("program", param)) {
                    PlayableManager.getInstance().cleanProgram();
                }
                if (TextUtils.equals("notification", param)) {
                    PlayableManager.getInstance().cleanNotification();
                }
            }
            if (args.trim().length() == 0) {
                PlayableManager.getInstance().cleanProgram();
                PlayableManager.getInstance().cleanNotification();
            }
            return SocketResult.success("clean program and notification successed");
        }
        if (cmd.trim().startsWith("vol")) {
            String operate = cmd.trim().substring(3).trim();
            if (!operate.startsWith("+") && !operate.startsWith("-"))
                return SocketResult.success("use '+' or '-' to change volume");
            int value = TypeUtil.objToIntDef(operate.trim().substring(1).trim(), -1);
            if (value == 0)
                return SocketResult.success("volume no change");
            if (value == -1)
                value = 1;
            AudioManager audioManager = (AudioManager) DeviceApp.getApp().getSystemService(Context.AUDIO_SERVICE);
            int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (operate.startsWith("+")) {
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                curVolume = curVolume + value > maxVolume ? maxVolume : curVolume + value;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, AudioManager.FLAG_PLAY_SOUND);
            } else if (operate.startsWith("-")) {
                curVolume = curVolume - value >= 0 ? curVolume - value : 0;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, AudioManager.FLAG_PLAY_SOUND);
            }
            DeviceApp.getApp().saveSharedPreferencesValue("musicVolume", curVolume + "");
            return SocketResult.success("volume change to " + curVolume);
        }
        if (cmd.trim().startsWith("sys build info")) {
            return SocketResult.success(JacksonUtil.objToJsonStr(SysBuildHelper.getAllBuildInformation()));
        }
        if (cmd.trim().startsWith("android id")) {
            return SocketResult.success(Settings.System.getString(DeviceApp.getApp().getContentResolver(), Settings.Secure.ANDROID_ID));
        }
        return null;
    }

    @Override
    public String getTagName() {
        return "dg";
    }

}
