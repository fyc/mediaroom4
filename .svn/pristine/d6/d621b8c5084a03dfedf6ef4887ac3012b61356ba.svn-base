package com.sunmnet.mediaroom.brand.impl.play;

import com.sunmnet.mediaroom.brand.bean.event.PlayEvent;
import com.sunmnet.mediaroom.brand.bean.play.NotificationRule;

import org.greenrobot.eventbus.EventBus;


public class Notification extends AbstractPlayable<NotificationRule> {

    private NotificationRule rule;

    public Notification(NotificationRule rule) {
        this.rule = rule;
        playState = PlayState.INITIALIZED;
    }

    @Override
    public void startPlay() {
        if (playState != PlayState.PLAYING) {
            //开始播放
            playState = PlayState.LOADED;
            EventBus.getDefault().postSticky(new PlayEvent(PlayEvent.START_PLAY, this));
            playState = PlayState.PLAYING;
        }
    }

    @Override
    public void stopPlay() {
        playState = PlayState.STOP;
        EventBus.getDefault().post(new PlayEvent(PlayEvent.STOP_PLAY, this));
    }

    @Override
    public boolean readyPlay() {
        return true;
    }


    @Override
    public NotificationRule getRule() {
        return rule;
    }
}
