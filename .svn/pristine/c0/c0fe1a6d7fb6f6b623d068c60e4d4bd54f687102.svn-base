package com.sunmnet.mediaroom.brand.data.database.play;

import com.sunmnet.mediaroom.brand.bean.play.SignatureContent;
import com.sunmnet.mediaroom.brand.bean.play.NotificationRule;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

@Entity
public class NotificationRuleDBEntity extends AbsPublishingRuleDBEntity<NotificationRule> {

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
     * 发布类型。节目：0；通知：1
     */
    protected int type;
    /**
     * 发布方式。普通：0；插播：1
     */
    protected int playType;

    /**
     * 播放状态。播放：0；停止：1
     */
    protected int playStatus;

    /**
     * 是否清除终端的普通/插播节目(由"playType"和"type"决定种类)。不清除：false； 清除：true
     */
    protected boolean cleanOld;

    private String color;
    private String background;
    private boolean underline;
    private boolean italic;
    private boolean bold;
    private int size;
    private int alignment;
    private String font;
    private String gravity;
    private String rollDirection;

    @Convert(converter = NotificationContentPropertyConverter.class, columnType = String.class)
    private List<SignatureContent> contents;

    @Generated(hash = 915022037)
    public NotificationRuleDBEntity() {
    }

    public NotificationRuleDBEntity(NotificationRule rule) {
        setData(rule);
    }

    @Generated(hash = 281103918)
    @Keep
    public NotificationRuleDBEntity(String id, String name, int timeType, String date, String time,
            String week, int type, int playType, int playStatus, boolean cleanOld, String color,
            String background, boolean underline, boolean italic, boolean bold, int size, int alignment,
            String font, String gravity, String rollDirection, List<SignatureContent> contents) {
        this.id = id;
        this.name = name;
        this.timeType = timeType;
        this.date = date;
        this.time = time;
        this.week = week;
        this.type = type;
        this.playType = playType;
        this.playStatus = playStatus;
        this.cleanOld = cleanOld;
        this.color = color;
        this.background = background;
        this.underline = underline;
        this.italic = italic;
        this.bold = bold;
        this.size = size;
        this.alignment = alignment;
        this.font = font;
        this.gravity = gravity;
        this.rollDirection = rollDirection;
        this.contents = contents;
        initStartTime();
        initEndTime();
    }


    @Override
    public void setData(NotificationRule rule) {
        super.setData(rule);
        this.color = rule.getColor();
        this.background = rule.getBackground();
        this.underline = rule.isUnderline();
        this.italic = rule.isItalic();
        this.bold = rule.isBold();
        this.size = rule.getSize();
        this.alignment = rule.getAlignment();
        this.font = rule.getFont();
        this.gravity = rule.getGravity();
        this.rollDirection = rule.getRollDirection();
        this.contents = rule.getContents();
    }

    @Override
    public NotificationRule mappingToPublishingRule() {
        NotificationRule rule = new NotificationRule();
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

        rule.setColor(getColor());
        rule.setBackground(getBackground());
        rule.setUnderline(getUnderline());
        rule.setItalic(getItalic());
        rule.setBold(getBold());
        rule.setSize(getSize());
        rule.setAlignment(getAlignment());
        rule.setFont(getFont());
        rule.setGravity(getGravity());
        rule.setRollDirection(getRollDirection());
        rule.setContents(getContents());
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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public boolean getUnderline() {
        return this.underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean getItalic() {
        return this.italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean getBold() {
        return this.bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAlignment() {
        return this.alignment;
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getGravity() {
        return this.gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getRollDirection() {
        return this.rollDirection;
    }

    public void setRollDirection(String rollDirection) {
        this.rollDirection = rollDirection;
    }

    public List<SignatureContent> getContents() {
        return this.contents;
    }

    public void setContents(List<SignatureContent> contents) {
        this.contents = contents;
    }

}
