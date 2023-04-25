package com.sunmnet.mediaroom.common.bean;

import java.io.Serializable;

public class SocketMessage implements Serializable {

    //消息UUID
    protected String uuid;
    //通信协议编号
    protected String key;
    //消息
    protected String msg;

    public SocketMessage() {
    }

    public SocketMessage(String uuid, String key, String msg) {
        this.uuid = uuid;
        this.key = key;
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "uuid='" + uuid + '\'' +
                ", key='" + key + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
