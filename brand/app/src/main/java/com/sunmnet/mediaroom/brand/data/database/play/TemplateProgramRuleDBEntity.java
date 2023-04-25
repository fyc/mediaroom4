package com.sunmnet.mediaroom.brand.data.database.play;

import com.sunmnet.mediaroom.brand.bean.play.TemplateProgramInfo;
import com.sunmnet.mediaroom.brand.bean.play.TemplateProgramRule;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class TemplateProgramRuleDBEntity extends AbsPublishingRuleDBEntity<TemplateProgramRule> {

    /**
     * 服务器规则ID
     */
    @Id
    protected String id;

    /**
     * 规则名称
     */
    protected String name;

    /**
     * 播放时间类型。全天播放：0；时间规则：1。
     * 全天播放不限日期时间，一直播放直到接收到停止播放的信息。
     */
    protected int timeType;

    /**
     * "2018.03.12-2018.03.28" 播放的日期段
     */
    protected String date;

    /**
     * "08:00-23:59" 播放的时间段
     */
    protected String time;

    /**
     * "1,2,3,4,5,6,7" 播放的星期，1-7对应星期一到星期日，由逗号隔开
     */
    protected String week;

    /**
     * 发布类型。节目：0；通知：1;模板节目:2
     */
    protected int type;

    /**
     * 发布类型。节目：0；通知：1
     */
    protected int templateType;

    /**
     * 发布方式。普通：0；插播：1
     */
    protected int playType;

    /**
     * 播放状态。播放：0；停止：1
     */
    protected int playStatus;

    /**
     * 是否清除终端的普通/插播节目(由"playType"和"controlType"决定种类)。不清除：false； 清除：true
     */
    protected boolean cleanOld;

    @Convert(converter = TemplateProgramInfoPropertyConverter.class, columnType = String.class)
    private TemplateProgramInfo resource;

    public TemplateProgramRuleDBEntity(TemplateProgramRule rule) {
        setData(rule);
    }

    @Generated(hash = 1228540651)
    @Keep
    public TemplateProgramRuleDBEntity(String id, String name, int timeType, String date, String time,
            String week, int type, int templateType, int playType, int playStatus, boolean cleanOld,
            TemplateProgramInfo resource) {
        this.id = id;
        this.name = name;
        this.timeType = timeType;
        this.date = date;
        this.time = time;
        this.week = week;
        this.type = type;
        this.templateType = templateType;
        this.playType = playType;
        this.playStatus = playStatus;
        this.cleanOld = cleanOld;
        this.resource = resource;
        initStartTime();
        initEndTime();
    }

    @Generated(hash = 982985481)
    public TemplateProgramRuleDBEntity() {
    }

    @Override
    public void setData(TemplateProgramRule rule) {
        super.setData(rule);
        resource = rule.getResource();
        templateType = rule.getTemplateType();
    }

    @Override
    public TemplateProgramRule mappingToPublishingRule() {
        TemplateProgramRule rule = new TemplateProgramRule();
        rule.setId(getId());
        rule.setName(getName());
        rule.setTimeType(getTimeType());
        rule.setDate(getDate());
        rule.setWeek(getWeek());
        rule.setType(getType());
        rule.setTime(getTime());
        rule.setPlayType(getPlayType());
        rule.setPlayStatus(getPlayStatus());
        rule.setCleanOld(getCleanOld());
        rule.initStartTime();
        rule.initEndTime();
        rule.setTemplateType(getTemplateType());
        rule.setResource(getResource());
        return rule;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeType() {
        return this.timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPlayType() {
        return this.playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public int getPlayStatus() {
        return this.playStatus;
    }

    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }

    public boolean getCleanOld() {
        return this.cleanOld;
    }

    public void setCleanOld(boolean cleanOld) {
        this.cleanOld = cleanOld;
    }

    public int getTemplateType() {
        return templateType;
    }

    public void setTemplateType(int templateType) {
        this.templateType = templateType;
    }

    public TemplateProgramInfo getResource() {
        return resource;
    }

    public void setResource(TemplateProgramInfo resource) {
        this.resource = resource;
    }
}
