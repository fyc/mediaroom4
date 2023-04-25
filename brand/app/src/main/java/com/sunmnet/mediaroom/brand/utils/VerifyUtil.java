package com.sunmnet.mediaroom.brand.utils;

import com.sunmnet.mediaroom.util.MD5Util;

public class VerifyUtil {


    private static final String verifyKey = "mediaroom";

    /**
     * [deviceCode] + [timestamp] + [username]
     * @param params
     * @return
     */
    public static String getVerifyCode(String... params) {
        //[key] + [deviceCode] + [timestamp] + [username]
        StringBuilder total = new StringBuilder(verifyKey);
        for (String s : params) {
            total.append(s);
        }
        return MD5Util.getMD5String(total.toString());
    }

}
