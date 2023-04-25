package com.sunmnet.mediaroom.brand.bean.config;


public class TimeSwitchConfig {

    /**
     * 规则使用状态，0未使用，1使用中
     */
    private Integer state;
    /**
     * 定时开关机周期,1：周日，1：周一..以此类推，多个星期用英文逗号,隔开
     */
    private String periodWeeks;

    /**
     * 开机时间，格式 HH:mm
     */
    private String onTime;

    /**
     * 关机时间，格式 HH:mm
     */
    private String offTime;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPeriodWeeks() {
        return periodWeeks;
    }

    public void setPeriodWeeks(String periodWeeks) {
        this.periodWeeks = periodWeeks;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }
}
