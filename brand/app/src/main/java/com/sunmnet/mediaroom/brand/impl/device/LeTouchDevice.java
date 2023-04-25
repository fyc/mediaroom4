package com.sunmnet.mediaroom.brand.impl.device;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.ComBean;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.ComDataListener;
import com.sunmnet.mediaroom.brand.impl.device.sinmar.serial.SerialHelper;
import com.sunmnet.mediaroom.brand.utils.HikDmbCommandExecutor;
import com.sunmnet.mediaroom.common.tools.HexUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import org.greenrobot.eventbus.EventBus;

import java.nio.ByteBuffer;

import android_serialport_api.SerialPortFinder;

/**
 * 乐触世界 系统版本7.1.2
 */
public class LeTouchDevice extends RockchipDevice implements ComDataListener {

    @Override
    public boolean checkFeature() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return false;
        }
        boolean exist = false;
        try {
            DeviceApp.getApp().getPackageManager().getPackageInfo("cn.huidu.service.telephony", 0);
            exist = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return exist;
    }


    private SerialHelper serialHelper;

    @Override
    public void init() {
        RunningLog.run("乐触班牌初始化");
        super.init();
        serialHelper = new SerialHelper("/dev/ttyS3", 9600);
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
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.run("打开串口失败");
            }
        }
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        sleepSwitch(offTime, onTime);
        return true;
    }

    @Override
    public boolean cancelTimingSwitch() {
        cancelSleepSwitch();
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

    //    刷卡主动输出数据为：
//    数据包起始     数据包长度	卡号类型    卡号        异或校验        数据包结束
//    02            1byte       1byte       4-8byte     1byte           03
//    数据包长度包括02、03。
//    异或校验为数据包长度、卡号类型、卡号的异或结果。
//    例如：
//            02 09 10 72 17 0C 0B‬ 7B 03
//            （卡号类型0x10，IC卡，卡号为72 17 0C 0B，转为十进制：0x72170C0B->1914113035）
//            02 0A 01 2E 00 3C 52 DE 95 03
//            （卡号类型0x01，ID卡，卡号为2E 00 3C 52 DE，转为十进制：0x003C52DE->3953374）
    @Override
    public void onDataReceived(ComBean comBean) {
        ByteBuffer buffer = ByteBuffer.wrap(comBean.bRec);
        while (buffer.hasRemaining()) {
            buffer.mark();
            int head = buffer.get() & 0xFF;
            if (head == 0x02) {
                int allDataLen = buffer.get() & 0xFF;//数据包长度,包括数据头和数据尾
                int lastDataLen = allDataLen - 2;//剩余数据长度
                int cardDataLen = lastDataLen - 3;//卡号长度
                if (lastDataLen > 0 && cardDataLen > 0 && buffer.remaining() >= lastDataLen) {
                    int cardType = buffer.get() & 0xFF;//卡号类型
                    byte[] cardData = new byte[cardDataLen];
                    buffer.get(cardData);
                    int bbc = buffer.get() & 0xFF;
                    int END = buffer.get() & 0xFF;
                    if (END != 0x03) {
                        continue;
                    }
                    int bbcCal = allDataLen ^ cardType;//异或计算结果
                    StringBuilder builder = new StringBuilder();
                    for (int i = cardData.length - 1; i >= 0; i--) {
                        bbcCal = bbcCal ^ cardData[i];
                        builder.append(HexUtil.byteToHex(cardData[i]));
                    }
                    bbcCal = bbcCal & 0xFF;
                    if (bbcCal == bbc) {
                        String s = builder.toString();
                        RunningLog.run("发送卡号：" + s);
                        EventBus.getDefault().post(new SwipeCardEvent(s));
                    }
                }
            }
        }
    }

    @Override
    public boolean hasRoot() {
        return true;
    }
}
