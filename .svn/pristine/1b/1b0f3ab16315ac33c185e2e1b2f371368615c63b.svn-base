package com.sunmnet.mediaroom.common.config;

import android.content.Context;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;

public class DeviceConfig {

    public static final String KEY_DEVICE_INFO = "deviceInfo";
    public static final String SHARE_PREF_NAME = "device";

    private static DeviceConfig instance;
    private DeviceInfo info;


    private DeviceConfig() {
    }

    public static DeviceConfig getInstance() {
        if (instance == null)
            instance = new DeviceConfig();
        return instance;
    }

    public DeviceInfo getDeviceInfo() {
        return info;
    }

    public DeviceInfo getDeviceInfo(Context context) {
        if (info != null) {
            return info;
        }
        if (context == null) {
            return null;
        }
        String str = SharePrefUtil.getString(context, SHARE_PREF_NAME, KEY_DEVICE_INFO);
        if (TextUtils.isEmpty(str))
            return null;
        info = JacksonUtil.jsonStrToBean(str, DeviceInfo.class);
        return info;
    }


    public boolean saveDeviceInfo(Context context, DeviceInfo upgradeInfo) {
        if (upgradeInfo == null) {
            return false;
        }
        String s = JacksonUtil.objToJsonStr(upgradeInfo);
        if (TextUtils.isEmpty(s))
            return false;
        info = upgradeInfo;
        return SharePrefUtil.saveValue(context, SHARE_PREF_NAME, KEY_DEVICE_INFO, s);
    }

}
