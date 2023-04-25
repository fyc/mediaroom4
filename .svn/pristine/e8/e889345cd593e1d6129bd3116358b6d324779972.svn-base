package com.sunmnet.mediaroom.brand.data.sharepref;

import android.content.Context;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.config.CustomConfig;
import com.sunmnet.mediaroom.brand.bean.config.SwipeCardConfig;
import com.sunmnet.mediaroom.brand.bean.config.TimeSwitchConfig;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;

public class BrandConfig {

    private static BrandConfig brandConfig;

    public static final String SHARE_PREF_NAME = "config";

    public static final String CUSTOM_CONFIG_KEY = "custom";

    public static final String TIME_SWITCH_CONFIG_KEY = "timeSwitch";

    // 刷卡使能状态值key
    public static final String SWIPE_CARD_KEY = "swipeCard";

    private BrandConfig() {
    }

    public static BrandConfig getInstance() {
        if (brandConfig == null)
            brandConfig = new BrandConfig();
        return brandConfig;
    }


    public TimeSwitchConfig getTimeSwitchConfig(Context context) {
        String configStr = SharePrefUtil.getString(context, SHARE_PREF_NAME, TIME_SWITCH_CONFIG_KEY);
        if (TextUtils.isEmpty(configStr))
            return null;
        return JacksonUtil.jsonStrToBean(configStr, TimeSwitchConfig.class);
    }

    public boolean saveTimeSwitchConfig(Context context, TimeSwitchConfig config) {
        String s = JacksonUtil.objToJsonStr(config);
        if (TextUtils.isEmpty(s))
            return false;
        return SharePrefUtil.saveValue(context, SHARE_PREF_NAME, TIME_SWITCH_CONFIG_KEY, s);
    }

    public CustomConfig getCustomConfig(Context context) {
        String configStr = SharePrefUtil.getString(context, SHARE_PREF_NAME, CUSTOM_CONFIG_KEY);
        if (TextUtils.isEmpty(configStr))
            return null;
        return JacksonUtil.jsonStrToBean(configStr, CustomConfig.class);
    }

    public boolean saveCustomConfig(Context context, CustomConfig config) {
        String s = JacksonUtil.objToJsonStr(config);
        if (TextUtils.isEmpty(s))
            return false;
        return SharePrefUtil.saveValue(context, SHARE_PREF_NAME, CUSTOM_CONFIG_KEY, s);
    }

    public SwipeCardConfig getSwipeCardConfig(Context context) {
        String configStr = SharePrefUtil.getString(context, SHARE_PREF_NAME, SWIPE_CARD_KEY);
        if (TextUtils.isEmpty(configStr)) {
            SwipeCardConfig defaultConfig = new SwipeCardConfig();
            return defaultConfig;
        }
        return JacksonUtil.jsonStrToBean(configStr, SwipeCardConfig.class);
    }

    public boolean saveSwipeCardConfig(Context context, SwipeCardConfig config) {
        String s = JacksonUtil.objToJsonStr(config);
        if (TextUtils.isEmpty(s))
            return false;
        return SharePrefUtil.saveValue(context, SHARE_PREF_NAME, SWIPE_CARD_KEY, s);
    }
}
