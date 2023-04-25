package com.sunmnet.mediaroom.tabsp.bean;

/**
 * Create by WangJincheng on 2021-11-05
 * 超级用户实体类
 */
public class SuperUserDto {
    public String name;
    public String password;

    public SuperUserDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
