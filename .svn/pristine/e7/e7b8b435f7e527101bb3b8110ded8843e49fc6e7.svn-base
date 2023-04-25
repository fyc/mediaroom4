package com.sunmnet.mediaroom.brand.utils;

import android.graphics.Color;
import android.text.TextUtils;

import com.sunmnet.mediaroom.common.tools.TypeUtil;


public class ColorUtil {

    /**
     * @param color rgba(12,31,23,0.3) or rgb(12,31,23) or #123456 or #12345678
     */
    public static int parseColor(String color) {
        if (TextUtils.isEmpty(color))
            return 0;
        if (color.startsWith("#")) {
            return Color.parseColor(color);
        }
        color = color.toLowerCase();
        if (color.startsWith("rgb") || color.startsWith("rgba")) {
            color = color.replace("rgb", "");
            color = color.replace("a", "");
            color = color.replace("(", "");
            color = color.replace(")", "");
            String rgba[] = color.split(",");
            float[] rgbaF = {0, 0, 0, 1};
            for (int i = 0; i < rgba.length; i++) {
                rgbaF[i] = TypeUtil.objToFloat(rgba[i].trim());
            }
            return Color.argb((int) (rgbaF[3] * 255), (int) rgbaF[0], (int) rgbaF[1], (int) rgbaF[2]);
        }
        return 0;
    }
}
