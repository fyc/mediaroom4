package com.sunmnet.mediaroom.brand.bean.config;

public class CustomConfig {

    private String homeScreen;
    private String autoRefreshTime;
    private String signInTimeBeforeClassBegin;
    private String signInTimeAfterClassBegin;
    private String signBackTimeBeforeClassOver;
    private String signBackTimeAfterClassOver;

    public String getHomeScreen() {
        return homeScreen;
    }

    public void setHomeScreen(String homeScreen) {
        this.homeScreen = homeScreen;
    }

    public String getAutoRefreshTime() {
        return autoRefreshTime;
    }

    public void setAutoRefreshTime(String autoRefreshTime) {
        this.autoRefreshTime = autoRefreshTime;
    }

    public String getSignInTimeBeforeClassBegin() {
        return signInTimeBeforeClassBegin;
    }

    public void setSignInTimeBeforeClassBegin(String signInTimeBeforeClassBegin) {
        this.signInTimeBeforeClassBegin = signInTimeBeforeClassBegin;
    }

    public String getSignInTimeAfterClassBegin() {
        return signInTimeAfterClassBegin;
    }

    public void setSignInTimeAfterClassBegin(String signInTimeAfterClassBegin) {
        this.signInTimeAfterClassBegin = signInTimeAfterClassBegin;
    }

    public String getSignBackTimeBeforeClassOver() {
        return signBackTimeBeforeClassOver;
    }

    public void setSignBackTimeBeforeClassOver(String signBackTimeBeforeClassOver) {
        this.signBackTimeBeforeClassOver = signBackTimeBeforeClassOver;
    }

    public String getSignBackTimeAfterClassOver() {
        return signBackTimeAfterClassOver;
    }

    public void setSignBackTimeAfterClassOver(String signBackTimeAfterClassOver) {
        this.signBackTimeAfterClassOver = signBackTimeAfterClassOver;
    }
}
