package com.sunmnet.mediaroom.brand.data.sharepref;

import android.content.Context;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.UpgradeInfo;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

public class UpgradePref {

    private static UpgradePref instance;
    private static UpgradeInfo info;

    private static final String SHARE_PREF_NAME = "device";

    private static final String KEY = "upgradeInfo";

    private UpgradePref() {
    }

    public static UpgradePref getInstance() {
        if (instance == null)
            instance = new UpgradePref();
        return instance;
    }

    public UpgradeInfo getUpgradeInfo(Context context) {
        if (info != null) {
            return info;
        }
        String str = SharePrefUtil.getString(context, SHARE_PREF_NAME, KEY);
        if (TextUtils.isEmpty(str))
            return null;
        info = JacksonUtil.jsonStrToBean(str, UpgradeInfo.class);
        return info;
    }

    public boolean saveUpgradeInfo(Context context, UpgradeInfo upgradeInfo) {
        if (upgradeInfo == null) {
            return false;
        }
        String s = JacksonUtil.objToJsonStr(upgradeInfo);
        if (TextUtils.isEmpty(s))
            return false;
        info = upgradeInfo;
        return SharePrefUtil.saveValue(context, SHARE_PREF_NAME, KEY, s);
    }

}
