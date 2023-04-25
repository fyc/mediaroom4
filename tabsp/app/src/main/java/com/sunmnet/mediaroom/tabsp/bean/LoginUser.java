package com.sunmnet.mediaroom.tabsp.bean;

import android.databinding.BaseObservable;

public class LoginUser extends BaseObservable {
    String user, password;

    public LoginUser(String u, String p) {
        this.user = u;
        this.password = p;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
