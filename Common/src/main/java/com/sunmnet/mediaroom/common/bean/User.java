package com.sunmnet.mediaroom.common.bean;

import com.sunmnet.mediaroom.common.events.LoginType;
import com.sunmnet.mediaroom.common.events.UserType;

import java.util.List;

/**
 * 设备使用者
 */
public class User {

    /**
     * 用户名
     */
    private String userName;

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    private String messgae;
    /**
     * 用户类型，超级管理员，管理员，教师，学生
     */
    private UserType userType;
    /**
     * 登录方式  刷卡，账号，二维码
     */
    private LoginType loginType;
    /**
     * 授权菜单
     */
    private List<String> authorizations;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public List<String> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(List<String> authorizations) {
        this.authorizations = authorizations;
    }
}
