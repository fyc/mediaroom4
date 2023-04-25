package com.sunmnet.mediaroom.brand.bean.request;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AttendSignInRequest2 implements Serializable  {
    /**
     * accountId: string
     * classNo: 0
     * classroomCode: string
     * attenceMethod: int, 1:刷卡,2:人脸识别, 3:app扫码, 4: 扫码器扫码(app个人二维码)
     * timestamp: string
     * verifyCode: string
     */

    private String accountId;
    private String deviceCode;
    private int classNo;
    private String classroomCode;
    private int attenceMethod; // 1:刷卡,2:人脸识别, 3:app扫码, 4: 扫码器扫码(app个人二维码)
    private String timestamp;
    private String verifyCode;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public int getClassNo() {
        return classNo;
    }

    public void setClassNo(int classNo) {
        this.classNo = classNo;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public int getAttenceMethod() {
        return attenceMethod;
    }

    public void setAttenceMethod(@AttendanceMethod int attenceMethod) {
        this.attenceMethod = attenceMethod;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    // 刷卡考勤方式
    public static final int SWIPE_CARD_ATTEND_METHOD = 1;
    // 人脸识别考勤方式
    public static final int FACE_ATTEND_METHOD = 2;

    /**
     * 考勤方式限定注解
     */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SWIPE_CARD_ATTEND_METHOD, FACE_ATTEND_METHOD})
    @interface AttendanceMethod {

    }
}
