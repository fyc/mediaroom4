package com.sunmnet.mediaroom.brand.bean.request;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AttendSignInRequest implements Serializable {


    /**
     * accountId : string
     * attenceMethod : 0
     * classroomCode : string
     * deviceCode : string
     * timestamp : string
     * username : string
     * verifyCode : string
     */

    private String accountId;
    /**
     * 签到方式(1:刷卡,2:人脸识别, 3:app扫码, 4: 扫码器扫码(app个人二维码))
     */
    @AttendType
    private int attenceMethod;
    private String classroomCode;
    private String deviceCode;
    private String timestamp;
    private String username;
    private String verifyCode;

    public static final int TYPE_CARD = 1;
    public static final int TYPE_FACE = 2;

    @IntDef({TYPE_CARD, TYPE_FACE})
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    @interface AttendType {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAttenceMethod() {
        return attenceMethod;
    }

    public void setAttenceMethod(@AttendType int attenceMethod) {
        this.attenceMethod = attenceMethod;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
