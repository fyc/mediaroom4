package com.sunmnet.mediaroom.tabsp.eventbus.events;

public class EventMessage<T> {
    int type;
    T messgae;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getMessgae() {
        return messgae;
    }

    public void setMessgae(T messgae) {
        this.messgae = messgae;
    }
}
