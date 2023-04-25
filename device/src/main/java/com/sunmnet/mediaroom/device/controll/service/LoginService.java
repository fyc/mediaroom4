package com.sunmnet.mediaroom.device.controll.service;
/**
 * 登录接口
 * */
public interface LoginService extends IRelease {
    public void doLogin(String userName,String pwd);
    public void doLogin(String url,String userName,String pwd,String classroomCode);
}
