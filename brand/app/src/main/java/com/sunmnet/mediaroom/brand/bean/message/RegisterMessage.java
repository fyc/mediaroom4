package com.sunmnet.mediaroom.brand.bean.message;

public class RegisterMessage {

    /**
     * platformIp : 192.168.21.120
     * platformPort : 80
     * coreIp : 1.1.1.1
     * corePort : 6003
     * classroomCode : T1-0102
     * classroomName : T1-0102
     * deviceCode : CLASSBRAND-992
     */

    private String platformIp;
    private int platformPort;
    private String coreIp;
    private int corePort;
    private String classroomCode;
    private String classroomName;
    private String deviceCode;

    public String getPlatformIp() {
        return platformIp;
    }

    public void setPlatformIp(String platformIp) {
        this.platformIp = platformIp;
    }

    public int getPlatformPort() {
        return platformPort;
    }

    public void setPlatformPort(int platformPort) {
        this.platformPort = platformPort;
    }

    public String getCoreIp() {
        return coreIp;
    }

    public void setCoreIp(String coreIp) {
        this.coreIp = coreIp;
    }

    public int getCorePort() {
        return corePort;
    }

    public void setCorePort(int corePort) {
        this.corePort = corePort;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }
}
