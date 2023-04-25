package com.sunmnet.mediaroom.brand.impl.device;

import android.app.Activity;

import com.hikvision.dmb.SwingCardCallback;
import com.hikvision.dmb.system.InfoSystemApi;
import com.hikvision.dmb.time.InfoTimeApi;
import com.hikvision.dmb.util.InfoUtilApi;
import com.sunmnet.mediaroom.brand.bean.event.SwipeCardEvent;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.interfaces.device.IBrandDevice;
import com.sunmnet.mediaroom.brand.utils.HikDmbCommandExecutor;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 海康DMB设备，使用dmb.jar的接口
 */
public class HikDmbDevice implements IBrandDevice {

    @Override
    public void init() {
        ShellUtils.setCommandExecutor(new HikDmbCommandExecutor());
        ShellUtils.hasRoot = true;
    }

    @Override
    public boolean checkFeature() {
        return InfoUtilApi.isAvailable();
    }

    @Override
    public void openAdb() {
        InfoSystemApi.openAdb();
    }

    @Override
    public void closeAdb() {
        InfoSystemApi.closeAdb();
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        int res = InfoTimeApi.setTimeSwitch(offTime, onTime);
        return res == 0;
    }

    @Override
    public boolean cancelTimingSwitch() {
        return InfoTimeApi.clearPlan();
    }

    @Override
    public boolean setSystemTime(long timeMillis) {
        int res = InfoTimeApi.setTime(timeMillis);
        return res == 0;
    }

    @Override
    public boolean installApk(String path) {
//        return CommandUtil.silentInstallApk(path,false);
        int res = InfoUtilApi.silentInstallation(path);
        return res == 0;
    }

    @Override
    public boolean hasRoot() {
        return true;
    }

    @Override
    public void registerSwipeCard(Activity activity) {
        InfoUtilApi.swingCard(attendSwingCardCallback);
    }

    @Override
    public void unregisterSwipeCard(Activity activity) {
        InfoUtilApi.unregisterSwingCard(attendSwingCardCallback);
    }

    @Override
    public void shutdown() {
        InfoSystemApi.shutdown();
    }

    @Override
    public void reboot() {
        InfoSystemApi.reboot();
    }

    private String getHexUid(String s) {
        int index = s.lastIndexOf(":");
        if (index > -1) {
            String idStr = s.substring(index + 1);
            long idInt = TypeUtil.objToLongDef(idStr, -1);
            if (idInt >= 0) {
                StringBuilder builder = new StringBuilder();
                //8位16进制字符串
                try {
                    for (int i = 0; i < 4; i++) {
                        long temp = idInt & 0xff;
                        if (temp < 0x10)
                            builder.append("0");
                        builder.append(Long.toHexString(temp));
                        idInt = idInt >> 8;
                    }
                    return builder.toString().toUpperCase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private SwingCardCallback.Stub attendSwingCardCallback = new SwingCardCallback.Stub() {

        @Override
        public void getInfo(final String s) {
            final String id = getHexUid(s);
            if (id != null && id.length() > 0) {
                RunningLog.run("海康, 读卡成功,卡片id：" + id);
                EventBus.getDefault().post(new SwipeCardEvent(id));
            } else {
                ToastUtil.show(DeviceApp.getApp(), "无法识别的卡片");
            }
        }
    };

}
