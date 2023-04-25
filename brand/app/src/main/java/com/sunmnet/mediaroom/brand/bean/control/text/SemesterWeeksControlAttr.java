package com.sunmnet.mediaroom.brand.bean.control.text;

import com.sunmnet.mediaroom.brand.bean.control.base.PrefixSuffixTextControlAttr;

public class SemesterWeeksControlAttr extends PrefixSuffixTextControlAttr {

    /**
     * 教学周，0为自动匹配当前学周，其余为固定学周
     */
    String weeks;

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }
}
