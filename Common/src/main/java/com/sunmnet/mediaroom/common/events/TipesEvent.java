package com.sunmnet.mediaroom.common.events;

public class TipesEvent {
    public TipesEvent(String message) {
        this(message, 2);
    }

    public TipesEvent(String message, int level) {
        this.tipesMsg = message = message;
        this.tipesLevel = level;
    }

    String tipesMsg;
    int tipesLevel;

    public String getTipesMsg() {
        return tipesMsg;
    }

    public int getTipesLevel() {
        return tipesLevel;
    }
}
