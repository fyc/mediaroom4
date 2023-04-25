package com.sunmnet.mediaroom.brand.impl.device;


import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.ComBean;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.ComDataListener;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.MyFunc;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.SerialHelper;
import com.sunmnet.mediaroom.brand.utils.HikDmbCommandExecutor;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import org.greenrobot.eventbus.EventBus;

import android_serialport_api.SerialPortFinder;

/**
 * 星马班牌
 */
public class SinmarDevice extends RockchipDevice implements ComDataListener {

    private SerialHelper serialHelper;

    @Override
    public void init() {
        RunningLog.run("星马班牌初始化");
        super.init();
        serialHelper = new SerialHelper();
        String[] allDevices = new SerialPortFinder().getAllDevicesPath();
        if (allDevices.length > 0) {
            String serial = null;
            for (String str : allDevices) {
                if (!str.contains("ttyGS")) {
                    serial = str;
                    break;
                }
            }
            if (TextUtils.isEmpty(serial)) {
                RunningLog.run("无有效串口");
                return;
            }
            RunningLog.run("打开串口:" + serial);
            serialHelper.setPort(serial);
            try {
                serialHelper.open();
                //卡号格式切换正向16进制
                RunningLog.run("初始化卡号格式");
                serialHelper.sendHex("AABB0600000001060304");
                //延时20ms 等串口处理完数据
                Thread.sleep(20);
                //LED熄灭
                RunningLog.run("初始化LED");
                serialHelper.sendHex("AA12000055");
                RunningLog.run("星马班牌初始化完毕");
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.run("打开串口初始化设置失败");
            }
        }
    }

    @Override
    public boolean checkFeature() {
        boolean exist = false;
        try {
            DeviceApp.getApp().getPackageManager().getServiceInfo(new ComponentName("com.ys.ys_receiver", "com.ys.ys_receiver.AIDLService"), 0);
            exist = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        broadcastSwitch(offTime, onTime);
        return true;
    }

    @Override
    public boolean cancelTimingSwitch() {
        cancelBroadcastSwitch();
        return true;
    }

    @Override
    public boolean hasRoot() {
        return true;
    }

    @Override
    public void registerSwipeCard(Activity activity) {
        serialHelper.register(this);
    }

    @Override
    public void unregisterSwipeCard(Activity activity) {
        serialHelper.unregister();
    }

    @Override
    public void onDataReceived(ComBean comBean) {
        String s = MyFunc.ByteArrToHex(comBean.bRec);
        RunningLog.run("发送卡号：" + s);
        EventBus.getDefault().post(new SwipeCardEvent(s));
    }
}
