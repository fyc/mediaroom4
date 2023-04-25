package com.sunmnet.mediaroom.tabsp.bean;


import java.util.List;
import java.util.Locale;

import com.sunmnet.mediaroom.common.enums.TabspVersion;
import com.sunmnet.mediaroom.common.bean.UpgradeInfo;
import com.sunmnet.mediaroom.device.bean.TabspRegisterInfo;
import com.sunmnet.mediaroom.tabsp.bean.enums.SoftType;
import com.sunmnet.mediaroom.tabsp.bean.enums.ThemeType;

public class TabspConfig {
    int classOverCountdown,//一键下课到倒计时间
            classOnCountdown;//一键上课倒计时间
    boolean swipCard = false;//显示刷卡
    boolean account = true;//显示账号登录按钮
    boolean qrcode = false;//显示二维码登录

    public NetworkInterface getNetwork() {
        return network;
    }

    public void setNetwork(NetworkInterface network) {
        this.network = network;
    }

    NetworkInterface network;

    public boolean isRate() {
        return rate;
    }

    public void setRate(boolean rate) {
        this.rate = rate;
    }

    boolean rate = false;

    public boolean isSwipCard() {
        return swipCard;
    }

    public void setSwipCard(boolean swipCard) {
        this.swipCard = swipCard;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public boolean isQrcode() {
        return qrcode;
    }

    public void setQrcode(boolean qrcode) {
        this.qrcode = qrcode;
    }

    public boolean isAntiTouchMode() {
        return antiTouchMode;
    }

    public void setAntiTouchMode(boolean antiTouchMode) {
        this.antiTouchMode = antiTouchMode;
    }

    /**
     * 设备关机提示
     */
    boolean antiTouchMode = false;//防误触模式

    public int getClassOverCountdown() {
        return classOverCountdown;
    }

    public void setClassOverCountdown(int classOverCountdown) {
        this.classOverCountdown = classOverCountdown;
    }

    public int getClassOnCountdown() {
        return classOnCountdown;
    }

    public void setClassOnCountdown(int classOnCountdown) {
        this.classOnCountdown = classOnCountdown;
    }

    public List<SysSpTempConfigFileDto.MenuSetting> getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(List<SysSpTempConfigFileDto.MenuSetting> menuNames) {
        this.menuNames = menuNames;
    }

    SysSpTempConfigFileDto configuration;

    public SysSpTempConfigFileDto getConfiguration() {
        return configuration;
    }

    public void setConfiguration(SysSpTempConfigFileDto configuration) {
        this.configuration = configuration;
    }

    List<SysSpTempConfigFileDto.MenuSetting> menuNames;
    String classRoomCode;
    /**
     * 当前程序所使用的文件夹路径
     */
    String exclusivePath;
    String classRoomName;
    /**
     * 语言选择
     */
    Locale lang = Locale.CHINA;
    /**
     * 主题类型，可用于切换主题
     */
    ThemeType themeType = ThemeType.NORMAL;
    /**
     * 软件版本，一共设计了3版。
     */
    SoftType softType = SoftType.VERSION_1;

    public String getLoginLogo() {
        return loginLogo;
    }

    public void setLoginLogo(String loginLogo) {
        if (loginLogo != null && !"".equals(loginLogo)) {
            this.loginLogo = loginLogo;
        }
    }

    String loginLogo;
    /**
     * 平台配置的logo路径
     */
    String baseLogo;
    /**
     * 平台配置的背景路径
     */
    String baseLoginBackground;
    /**
     * 用于中控连接时的版本切换，目前已同步
     */
    TabspVersion versionType = TabspVersion.VERSION_4;
    /**
     * 注册信息
     */
    TabspRegisterInfo registerInfo;

    public TabspRegisterInfo getRegisterInfo() {
        return registerInfo;
    }

    public void setRegisterInfo(TabspRegisterInfo registerInfo) {
        this.registerInfo = registerInfo;
    }

    public String getClassRoomName() {
        return classRoomName;
    }

    public void setClassRoomName(String classRoomName) {
        this.classRoomName = classRoomName;
    }

    public TabspVersion getVersionType() {
        return versionType;
    }

    public void setVersionType(TabspVersion versionType) {
        this.versionType = versionType;
    }

    public String getBaseLogo() {
        return baseLogo;
    }

    public void setBaseLogo(String baseLogo) {
        if (baseLogo != null && !"".equals(baseLogo)) {
            this.baseLogo = baseLogo;
        }
    }

    public String getBaseLoginBackground() {
        return baseLoginBackground;
    }

    public void setBaseLoginBackground(String baseLoginBackground) {
        if (baseLoginBackground != null && !"".equals(baseLoginBackground)) {
            this.baseLoginBackground = baseLoginBackground;
        }
    }

    public String getBaseContentBackground() {
        return baseContentBackground;
    }

    public void setBaseContentBackground(String baseContentBackground) {
        this.baseContentBackground = baseContentBackground;
    }

    String baseContentBackground;

    public Locale getLang() {
        return lang;
    }

    public void setLang(Locale lang) {
        this.lang = lang;
    }

    public ThemeType getThemeType() {
        return themeType;
    }

    public void setThemeType(ThemeType themeType) {
        this.themeType = themeType;
    }

    public SoftType getSoftType() {
        return softType;
    }

    public void setSoftType(SoftType softType) {
        this.softType = softType;
    }

    public String getClassRoomCode() {
        return classRoomCode;
    }

    public void setClassRoomCode(String classRoomCode) {
        this.classRoomCode = classRoomCode;
    }

    public String getExclusivePath() {
        return exclusivePath;
    }

    public void setExclusivePath(String exclusivePath) {
        this.exclusivePath = exclusivePath;
    }

    public UpgradeInfo getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(UpgradeInfo upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    UpgradeInfo upgradeInfo;

}
