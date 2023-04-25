package com.sunmnet.mediaroom.tabsp.record.impl;

import android.widget.VideoView;

import com.sunmnet.mediaroom.tabsp.record.RecordMaker;


public class OrdinaryPlayer extends AbstractPlayer<VideoView> implements Runnable {
    VideoView videoView;
    String playUrl;
    boolean isPlaying = false;

    @Override
    public void setPlayView(VideoView videoView) {
        this.videoView = videoView;
        this.videoView.postDelayed(this, 300);
    }

    @Override
    public <E> void setPlayerParam(E e) {
        this.playUrl = (String) e;
    }

    @Override
    public void onReady() {
        if (this.playUrl==null){
            try {
                this.playUrl= RecordMaker.getRecord().getPlayUrl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.videoView.setVideoPath(this.playUrl);
        this.videoView.start();
    }

    @Override
    public void start() {
        if (this.playUrl != null && this.videoView != null) {
            if (isPlaying) {
                this.videoView.start();
                return;
            }
            this.onReady();
        }
    }

    @Override
    public void pause() {
        this.videoView.pause();
    }

    @Override
    public void release() {
        this.videoView.pause();
    }

    @Override
    public void run() {
        this.onReady();
    }
}
