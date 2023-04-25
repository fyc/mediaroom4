package com.sunmnet.mediaroom.device.bean;

public class ConnectConfig {
    public static final int CONNECT_TYPE_SERVER=1;
    public static final int CONNECT_TYPE_LINK=0;
    String host;
    int port;
    int connectType = 0;//0：表示启动为客户端   1：启动为服务器

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    /**
     * 构造器
     *
     * @param host        有值则为连接到目标，无值则为开启服务器
     * @param port
     *
     */
    public ConnectConfig(String host, int port) {
        this.host = host;
        this.port = port;
        this.connectType = CONNECT_TYPE_LINK;
    }
    public ConnectConfig(String host, int port,int type) {
        this.host = host;
        this.port = port;
        this.connectType = type;
    }
    public ConnectConfig(int port,String name) {
        this.port = port;
        this.connectType = CONNECT_TYPE_SERVER;
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectType() {
        return connectType;
    }

    public void setConnectType(int connectType) {
        this.connectType = connectType;
    }
}
