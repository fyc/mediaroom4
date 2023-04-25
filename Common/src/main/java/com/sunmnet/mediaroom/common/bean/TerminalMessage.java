package com.sunmnet.mediaroom.common.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TerminalMessage extends SocketMessage {

    public static final int BRAND = 1;
    public static final int TABSP = 2;

    @IntDef({BRAND, TABSP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    protected int source;

    public TerminalMessage() {
    }

    public TerminalMessage(SocketMessage message, @Source int source) {
        super(message.getUuid(), message.getKey(), message.getMsg());
        this.source = source;
    }

    public TerminalMessage(String uuid, String key, String msg, @Source int source) {
        super(uuid, key, msg);
        this.source = source;
    }

    public int getSource() {
        return source;
    }

    public void setSource(@Source int source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "TerminalMessage{" +
                "source=" + source +
                ", uuid='" + uuid + '\'' +
                ", key='" + key + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
