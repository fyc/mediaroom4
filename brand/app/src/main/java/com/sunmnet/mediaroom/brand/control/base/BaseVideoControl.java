package com.sunmnet.mediaroom.brand.control.base;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import org.videolan.vlc.VlcVideoView;
import org.videolan.vlc.listener.MediaListenerEvent;

import java.util.List;

public class BaseVideoControl<P extends ControlProperty> extends BaseFrameControl<P> {


    public BaseVideoControl(Context context) {
        super(context);
    }

    public BaseVideoControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public BaseVideoControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected VlcVideoView mVideoView;

    protected List<String> videoList;

    protected int mVideoPosition;
    protected String mVideoPath;

    MediaListenerEvent listenerEvent = new MediaListenerEvent() {
        @Override
        public void eventBuffing(int event, float buffing) {
        }

        @Override
        public void eventStop(boolean isPlayError) {
            post(new Runnable() {
                @Override
                public void run() {
                    onPlayCompletion();
                }
            });
        }

        @Override
        public void eventError(int event, boolean show) {
            RunningLog.debug("VLC播放出错：" + mVideoPath);
        }

        @Override
        public void eventPlay(boolean isPlaying) {

        }

        @Override
        public void eventPlayInit(boolean openClose) {
        }
    };

    @Override
    public void refreshControlData() {
        try {
            if (mVideoView != null && videoList != null && videoList.size() > 0) {
                mVideoPosition = mVideoPosition % videoList.size();
                if (!TextUtils.equals(mVideoPath, TypeUtil.objToStr(videoList.get(mVideoPosition)))) {
                    pause();
                    mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition));
                    mVideoView.setPath(mVideoPath);
                    mVideoView.startPlay();
                } else if (!mVideoView.isPlaying()) {
                    mVideoView.setPath(mVideoPath);
                    mVideoView.startPlay();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        try {
            mVideoView = new VlcVideoView(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mVideoView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        removeAllViews();
        addView(mVideoView);

        mVideoView.setMediaListenerEvent(listenerEvent);
        mVideoView.setLoop(false);

        if (mVideoView != null && videoList != null && videoList.size() > 0) {
            mVideoPosition = mVideoPosition % videoList.size();
            mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition));
            mVideoView.setPath(mVideoPath);
            mVideoView.startPlay();
        }
    }

    @Override
    public void onControlShow() {
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVideoView != null) {
            mVideoView.setMediaListenerEvent(null);
            mVideoView.onStop();
            mVideoView.onDestory();

            removeView(mVideoView);
            mVideoView = null;
        }
    }

    @Override
    public void onControlHide() {
        pause();
    }


    protected void playPrev(){
        if (mVideoView != null && videoList != null && videoList.size() > 0) {
            if (mVideoPosition > 0) {
                if(!TextUtils.equals(mVideoPath, TypeUtil.objToStr(videoList.get(mVideoPosition = mVideoPosition - 1)))) {
                    mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition = mVideoPosition - 1));
                }
            } else {
                if (!TextUtils.equals(mVideoPath, TypeUtil.objToStr(videoList.get(mVideoPosition = videoList.size() - 1)))) {
                    mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition = videoList.size() - 1));
                }
            }
            mVideoView.setPath(mVideoPath);
            mVideoView.startPlay();
        }
    }

    protected void playNext(){
        if (mVideoView != null && videoList != null && videoList.size() > 0) {
            mVideoPosition++;
            mVideoPosition = mVideoPosition % videoList.size();
            mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition));
            mVideoView.setPath(mVideoPath);
            mVideoView.startPlay();
        }
    }

    protected void onPlayCompletion(){
        if (mVideoView != null && videoList != null && videoList.size() > 0) {
            mVideoPosition++;
            mVideoPosition = mVideoPosition % videoList.size();
            mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition));
            mVideoView.setPath(mVideoPath);
            mVideoView.startPlay();
        }
    }

    public void stop() {
        try {
            if (mVideoView != null && mVideoView.isPlaying()) {
                mVideoView.pause();
                mVideoView.seekTo(0);
                mVideoView.onStop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            if (mVideoView != null && mVideoView.isPlaying()) {
                mVideoView.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            if (mVideoView != null && !mVideoView.isPlaying() && videoList != null && videoList.size() > 0) {
                mVideoPosition = mVideoPosition % videoList.size();
                if (TextUtils.isEmpty(mVideoPath)) {
                    mVideoPath = TypeUtil.objToStr(videoList.get(mVideoPosition));
                }
                mVideoView.setPath(mVideoPath);
                mVideoView.startPlay();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVideoList(List<String> videoList) {
        this.videoList = videoList;
    }
}
