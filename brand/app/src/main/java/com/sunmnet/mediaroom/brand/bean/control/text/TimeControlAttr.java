package com.sunmnet.mediaroom.brand.bean.control.text;

import com.sunmnet.mediaroom.brand.bean.control.base.PrefixSuffixTextControlAttr;

public class TimeControlAttr extends PrefixSuffixTextControlAttr {
    /**
     * 12H 12小时制， 24H 24小时制
     */
    String timeType;

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }
}
