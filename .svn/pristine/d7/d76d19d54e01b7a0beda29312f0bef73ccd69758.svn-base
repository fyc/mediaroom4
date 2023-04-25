package com.sunmnet.mediaroom.device.events;

public class ListenerEvent<T, E> {
    public ListenerEvent(int type,T topic, E listener) {
        this.key=topic;
        this.type=type;
        this.listener=listener;
    }

    T key;
    E listener;

    public int getType() {
        return type;
    }

    int type;
    public T getTopic() {
        return key;
    }

    public void setTopic(T topic) {
        this.key = topic;
    }

    public E getListener() {
        return listener;
    }

    public void setListener(E listener) {
        this.listener = listener;
    }
}
