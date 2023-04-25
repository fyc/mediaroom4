package com.sunmnet.mediaroom.brand.impl.device;

import android.app.Activity;

import com.sunmnet.mediaroom.brand.interfaces.device.IBrandDevice;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 班牌设备 总代理
 */
public class BrandDevice implements IBrandDevice {

    private static BrandDevice instance;

    private IBrandDevice mDevice;

    private List<Class<? extends IBrandDevice>> deviceEnumList;

    private BrandDevice() {
        deviceEnumList = new ArrayList<>(6);
        deviceEnumList.add(HikDmbDevice.class);
        deviceEnumList.add(SinmarDevice.class);
        deviceEnumList.add(InchDevice.class);
        deviceEnumList.add(LeTouchDevice.class);
        deviceEnumList.add(RockchipDevice.class);
        deviceEnumList.add(NormalDevice.class);
    }

    public static synchronized BrandDevice getInstance() {
        if (instance == null) {
            instance = new BrandDevice();
        }
        return instance;
    }

    public IBrandDevice getType() {
        return mDevice;
    }

    @Override
    public void init() {
        if (mDevice == null) {
            for (Class<? extends IBrandDevice> clzz : deviceEnumList) {
                try {
                    IBrandDevice brandDevice = clzz.newInstance();
                    if (brandDevice.checkFeature()) {
                        mDevice = brandDevice;
                        break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        if (mDevice != null) {
            mDevice.init();
            RunningLog.run("设备类型初始化：" + mDevice.getClass().getSimpleName() + " , has root:" + mDevice.hasRoot());
        }
    }

    @Override
    public boolean checkFeature() {
        return mDevice != null && mDevice.checkFeature();
    }

    @Override
    public void openAdb() {
        if (mDevice != null) {
            mDevice.openAdb();
        }
    }

    @Override
    public void closeAdb() {
        if (mDevice != null) {
            mDevice.closeAdb();
        }
    }

    @Override
    public boolean setTimingSwitch(long offTime, long onTime) {
        return mDevice != null && mDevice.setTimingSwitch(offTime, onTime);
    }

    @Override
    public boolean cancelTimingSwitch() {
        return mDevice != null && mDevice.cancelTimingSwitch();
    }

    @Override
    public boolean setSystemTime(long timeMillis) {
        return mDevice != null && mDevice.setSystemTime(timeMillis);
    }

    @Override
    public boolean installApk(String path) {
        return mDevice != null && mDevice.installApk(path);
    }

    @Override
    public boolean hasRoot() {
        return mDevice != null && mDevice.hasRoot();
    }

    @Override
    public void registerSwipeCard(Activity activity) {
        if (mDevice != null) {
            mDevice.registerSwipeCard(activity);
        }
    }

    @Override
    public void unregisterSwipeCard(Activity activity) {
        if (mDevice != null) {
            mDevice.unregisterSwipeCard(activity);
        }
    }

    @Override
    public void shutdown() {
        if (mDevice != null) {
            mDevice.shutdown();
        }
    }

    @Override
    public void reboot() {
        if (mDevice != null) {
            mDevice.reboot();
        }
    }
}
