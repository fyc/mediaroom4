package com.sunmnet.mediaroom.tabsp.record.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.R;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player_lib.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.player_lib.widget.media.IjkVideoView;

public class IjkPresentation extends AbstractPrenstation {
    protected boolean isInit = false;
    protected IjkVideoView ijkVideoView;
    protected View inflateView;

    protected AndroidMediaController mMediaController;

    @Override
    public void init(View view) {
        super.init();
        RunningLog.run("IjkPresentation init");
        if (this.contentView == null) {
            this.contentView = view;
            FrameLayout frameLayout = (FrameLayout) view;
            View tipes = getView(frameLayout, R.id.tipes);
            if (tipes != null) {
                tipes.setVisibility(View.GONE);
            }
            if (inflateView == null) {
                inflateView = LayoutInflater.from(view.getContext()).inflate(getLayout(), frameLayout, false);
            }
            frameLayout.addView(inflateView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initControlPanel(inflateView);
            if (ijkVideoView == null) {
                IjkMediaPlayer.loadLibrariesOnce(null);
                IjkMediaPlayer.native_profileBegin("libijkplayer.so");
                ijkVideoView = new IjkVideoView(screen.getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                screen.addView(ijkVideoView, params);
                mMediaController = new AndroidMediaController(screen.getContext(), false);
                mMediaController.setShowable(false);
                ijkVideoView.setMediaController(mMediaController);
                ijkVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
            }
            player.setPlayView(ijkVideoView);
            player.setPlayerParam(record.getPlayUrl());
        } else {
            if (ijkVideoView != null) {
                player.setPlayView(ijkVideoView);
            }
        }
        isInit = true;
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    @Override
    public void release() {
        super.release();
        if (this.record != null) {
            this.record.setRecordListener(null);
        }
        this.ijkVideoView.setMediaController(null);
        this.ijkVideoView.setRender(IjkVideoView.RENDER_NONE);
        this.ijkVideoView.stopPlayback();
        this.ijkVideoView.release(true);
        this.ijkVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
        screen.removeView(ijkVideoView);
        this.ijkVideoView = null;

        if (this.mMediaController != null) {
            this.mMediaController.setAnchorView(null);
            this.mMediaController.setPrevNextListeners(null, null);
            this.mMediaController = null;
        }
        ((FrameLayout) contentView).removeView(this.inflateView);
        screen = null;
        contentView = null;
        this.isInit = false;
        start = null;
        pause = null;
        stop = null;
    }
}
