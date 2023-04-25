package com.sunmnet.mediaroom.tabsp.record.impl;

import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.util.concurrent.atomic.AtomicInteger;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player_lib.widget.media.IjkVideoView;

public class IjkPlayer extends AbstractPlayer<IjkVideoView> implements Runnable, IMediaPlayer.OnErrorListener, IMediaPlayer.OnCompletionListener {

    private IjkVideoView playView = null;
    private String playUrl = null;

    private AtomicInteger playState = new AtomicInteger();
    private static final int INIT = 0;
    private static final int PLAY = 1;
    private static final int PAUSE = 2;
    private static final int STOP = 3;

    public IjkPlayer() {
    }

    @Override
    public void setPlayView(IjkVideoView vlcVideoView) {
        playView = vlcVideoView;
        playView.setOnErrorListener(this);
        playView.setOnCompletionListener(this);
    }

    @Override
    public <E> void setPlayerParam(E e) {
        this.playUrl = (String) e;
    }

    @Override
    public void onReady() {
        if (this.playView != null) {
            this.playView.getHandler().removeCallbacksAndMessages(null);
        }
        start();
    }

    @Override
    public void start() {
        if (this.playUrl != null && this.playView != null) {
            int i = playState.getAndSet(PLAY);
            switch (i) {
                case INIT:
                case STOP:
                    playView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
                    playView.setVideoPath(playUrl);
                    playView.start();
                    break;
                case PLAY:
                    playView.stopPlayback();
                    playView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
                    playView.setVideoPath(playUrl);
                    playView.start();
                    break;
                case PAUSE:
                    playView.start();
                    break;
            }
        }
    }

    @Override
    public void pause() {
        int i = playState.getAndSet(PAUSE);
        if (i == PLAY) {
            if (playView != null) {
                this.playView.getHandler().removeCallbacksAndMessages(null);
                this.playView.pause();
            }
        }
    }

    @Override
    public void stop() {
        int i = playState.getAndSet(STOP);
        if (i == PLAY || i == PAUSE) {
            if (playView != null) {
                this.playView.getHandler().removeCallbacksAndMessages(null);
                playView.stopPlayback();
            }
        }
    }

    @Override
    public void release() {
        stop();
    }

    @Override
    public void run() {
        this.onReady();
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        RunningLog.run("ijkplayer play error");
        int state = playState.get();
        if (state == PLAY) {
            this.playView.postDelayed(this, 5000);
        }
        return true;
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        stop();
        start();
    }
}
