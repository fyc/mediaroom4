package com.sunmnet.mediaroom.common.events;

public class Event<M, T> {
    T eventType;
    M message;

    public Event() {
    }

    public Event(T eventType) {
        this.eventType = eventType;
    }

    public Event(T eventType, M message) {
        this.eventType = eventType;
        this.message = message;
    }

    public T getEventType() {
        return eventType;
    }

    public void setEventType(T eventType) {
        this.eventType = eventType;
    }

    public M getMessage() {
        return message;
    }

    public void setMessage(M message) {
        this.message = message;
    }
}
