package com.sunmnet.mediaroom.tabsp.bean;

import java.util.List;

/**
 * <p>
 * 系统配置中心-系统配置-面板模板配置(发送面板配置实体类)
 * </p>
 *
 * @author zhangjw
 * @since 2020-08-26
 */
public class SysSpTempConfigFileDto {
    private String id;
    private String name;
    private Integer templateType;
    private String background;
    private String classroomCode;

    private List<MenuSetting> menuSettingList;

    public List<LectureMode> getLectureModeList() {
        return lectureModeList;
    }

    public void setLectureModeList(List<LectureMode> lectureModeList) {
        this.lectureModeList = lectureModeList;
    }

    public List<DiscussMode> getDiscussionModeList() {
        return discussionModeList;
    }

    public void setDiscussionModeList(List<DiscussMode> discussionModeList) {
        this.discussionModeList = discussionModeList;
    }

    private List<LectureMode> lectureModeList;
    /*public List<LectureMode> getDiscussionModeList() {
        return discussionModeList;
    }

    public void setDiscussionModeList(List<LectureMode> discussionModeList) {
        this.discussionModeList = discussionModeList;
    }
*/
    private List<DiscussMode> discussionModeList;

    public static class DiscussMode {
        String key, value, unselectedIcon, selectedIcon;
        boolean enable = false;
        int id;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUnselectedIcon() {
            return unselectedIcon;
        }

        public void setUnselectedIcon(String unselectedIcon) {
            this.unselectedIcon = unselectedIcon;
        }

        public String getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(String selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class LectureMode {

        String id;
        String sceneName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSceneName() {
            return sceneName;
        }

        public void setSceneName(String sceneName) {
            this.sceneName = sceneName;
        }

        public String getUnselectedIcon() {
            return unselectedIcon;
        }

        public void setUnselectedIcon(String unselectedIcon) {
            this.unselectedIcon = unselectedIcon;
        }

        public String getSelectedIcon() {
            return selectedIcon;
        }

        public void setSelectedIcon(String selectedIcon) {
            this.selectedIcon = selectedIcon;
        }

        String unselectedIcon;
        String selectedIcon;
    }

    private String lectureMode;
    private String discussionMode;

    public static class MenuSetting {
        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getAliasName() {
            return aliasName;
        }

        public void setAliasName(String aliasName) {
            this.aliasName = aliasName;
        }

        public Integer getSortIndex() {
            return sortIndex;
        }

        public void setSortIndex(Integer sortIndex) {
            this.sortIndex = sortIndex;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        private String menuName;
        private String aliasName;
        private Integer sortIndex;
        private boolean enable;
        private String key;
    }

    // @ApiModelProperty(value = "自定义配置")
    private CustomConfig customConfig;

    public static class CustomConfig {
        public String getLoginPageLogo() {
            return loginPageLogo;
        }

        public void setLoginPageLogo(String loginPageLogo) {
            this.loginPageLogo = loginPageLogo;
        }

        public String getHomePageLogo() {
            return homePageLogo;
        }

        public void setHomePageLogo(String homePageLogo) {
            this.homePageLogo = homePageLogo;
        }

        public String getLoginSet() {
            return loginSet;
        }

        public void setLoginSet(String loginSet) {
            this.loginSet = loginSet;
        }

        public Integer isDeviceEvaluateSet() {
            return deviceEvaluateSet;
        }

        public void setDeviceEvaluateSet(Integer deviceEvaluateSet) {
            this.deviceEvaluateSet = deviceEvaluateSet;
        }

        public Integer getOnekeyClassBeginCountdown() {
            return onekeyClassBeginCountdown;
        }

        public void setOnekeyClassBeginCountdown(Integer onekeyClassBeginCountdown) {
            this.onekeyClassBeginCountdown = onekeyClassBeginCountdown;
        }

        public Integer getOnekeyClassOverCountdown() {
            return onekeyClassOverCountdown;
        }

        public void setOnekeyClassOverCountdown(Integer onekeyClassOverCountdown) {
            this.onekeyClassOverCountdown = onekeyClassOverCountdown;
        }

        public String getFirstAidUsername() {
            return firstAidUsername;
        }

        public void setFirstAidUsername(String firstAidUsername) {
            this.firstAidUsername = firstAidUsername;
        }

        public String getFirstAidPassword() {
            return firstAidPassword;
        }

        public void setFirstAidPassword(String firstAidPassword) {
            this.firstAidPassword = firstAidPassword;
        }

        //@JSONField(name = "login_page_logo")
        // @ApiModelProperty(value = "登录页Logo")
        private String loginPageLogo;

        //@JSONField(name = "home_page_logo")
        //@ApiModelProperty(value = "首页Logo")
        private String homePageLogo;

        // @JSONField(name = "login_set")
        // @ApiModelProperty(value = "登陆设置(1：账号、2：刷卡、3：二维码)")
        private String loginSet;

        // @JSONField(name = "device_evaluate_set")
        //  @ApiModelProperty(value = "设备评价设置  1：启用、0：禁用")
        private Integer deviceEvaluateSet;

        // 超级用户名
        private String firstAidUsername;
        // 超级用户密码
        private String firstAidPassword;

        // @JSONField(name = "onekey_class_begin_countdown")
        // @ApiModelProperty(value = "一键上课弹窗倒计时")
        private Integer onekeyClassBeginCountdown;

        // @JSONField(name = "onekey_class_over_countdown")
        // @ApiModelProperty(value = "一键下课弹窗倒计时")
        private Integer onekeyClassOverCountdown;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public List<MenuSetting> getMenuSettingList() {
        return menuSettingList;
    }

    public void setMenuSettingList(List<MenuSetting> menuSettingList) {
        this.menuSettingList = menuSettingList;
    }

    public String getLectureMode() {
        return lectureMode;
    }

    public void setLectureMode(String lectureMode) {
        this.lectureMode = lectureMode;
    }

    public String getDiscussionMode() {
        return discussionMode;
    }

    public void setDiscussionMode(String discussionMode) {
        this.discussionMode = discussionMode;
    }

    public CustomConfig getCustomConfig() {
        return customConfig;
    }

    public void setCustomConfig(CustomConfig customConfig) {
        this.customConfig = customConfig;
    }
}
