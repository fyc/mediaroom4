package com.sunmnet.mediaroom.brand.bean.control.media;

public class TeacherPortraitControlAttr {

    /**
     * classNo : 0
     * week : 1
     * portrait : self
     */

    /**
     * 匹配课节，0为自动匹配，其余为匹配对应课节，双课节用逗号隔开
     */
    private String classNo;
    /**
     * 匹配星期，0为自动匹配，1-7对应周一到周日
     */
    private String week;
    /**
     * 头像。默认default，本人self
     */
    private String portrait;

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
