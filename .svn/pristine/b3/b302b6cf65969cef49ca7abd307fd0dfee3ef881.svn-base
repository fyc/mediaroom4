package com.sunmnet.mediaroom.brand.bean.event;

import com.sunmnet.mediaroom.brand.interfaces.play.IPlayable;

public class PlayEvent {

    public static final int START_PLAY = 1;
    public static final int STOP_PLAY = 2;

    private int status;
    private IPlayable playable;

    public PlayEvent(int status, IPlayable playable) {
        this.status = status;
        this.playable = playable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public IPlayable getPlayable() {
        return playable;
    }

    public void setPlayable(IPlayable playable) {
        this.playable = playable;
    }
}
