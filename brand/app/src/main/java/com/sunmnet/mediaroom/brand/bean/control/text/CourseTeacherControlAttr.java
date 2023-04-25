package com.sunmnet.mediaroom.brand.bean.control.text;

import com.sunmnet.mediaroom.brand.bean.control.base.PrefixSuffixTextControlAttr;

public class CourseTeacherControlAttr extends PrefixSuffixTextControlAttr {


    /**
     * 匹配课节，0为自动匹配，其余为匹配对应课节，双课节用逗号隔开
     */
    String classNo;

    /**
     * 匹配星期，0为自动匹配，1-7对应周一到周日
     */
    String week;

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
