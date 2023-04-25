package com.sunmnet.mediaroom.common.events;


import com.sunmnet.mediaroom.common.enums.SystemEventType;

public class SystemEvent<T> {
    public SystemEvent(SystemEventType type){
        this.eventType=type;
    }
    public SystemEvent(SystemEventType type,T t){

    }

    public SystemEventType getEventType() {
        return eventType;
    }

    SystemEventType eventType;
    public T getMessage() {
        return t;
    }

    public void setMessage(T t) {
        this.t = t;
    }

    T t;
}
