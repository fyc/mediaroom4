package com.sunmnet.mediaroom.tabsp.record.impl.hbe.bean;


public class HBERecordBean {
    public HBERecordBean(){
        this.setHost("");
        this.setPort("");
        this.setProgramName("");
        this.setStreamName("");
        this.setStreamPort("");

    }

    /**
     * 录播登录账户名
     * */
    private String uName="";
    /**
     * 录播登录密码
     * */
    private String uPwd="";
    /**
     * 录播IP
     * */
    private String host="";
    /**
     * 录播端口
     * */
    private String port="";

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    private String brand;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    private String streamName;
    private String streamPort;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getStreamPort() {
        return streamPort;
    }

    public void setStreamPort(String streamPort) {
        this.streamPort = streamPort;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    private String programName;
}
