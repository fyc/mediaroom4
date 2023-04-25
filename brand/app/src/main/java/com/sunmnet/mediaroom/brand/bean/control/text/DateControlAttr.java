package com.sunmnet.mediaroom.brand.bean.control.text;

import com.sunmnet.mediaroom.brand.bean.control.base.PrefixSuffixTextControlAttr;

public class DateControlAttr extends PrefixSuffixTextControlAttr {

    /**
     * 日期格式
     */
    String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
