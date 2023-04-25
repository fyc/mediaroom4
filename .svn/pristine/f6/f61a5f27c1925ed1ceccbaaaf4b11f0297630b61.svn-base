package com.sunmnet.mediaroom.tabsp.record.impl.simple;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.tabsp.record.R;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractPrenstation;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player_lib.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.player_lib.widget.media.IjkVideoView;

/**
 * Create by WangJincheng on 2021-10-20
 * 简易录播定制
 */

public class SimpleRecordPresentation extends AbstractPrenstation {

    protected boolean hasInit = false;
    protected IjkVideoView ijkVideoView = null;
    protected View inflateView = null;

    protected AndroidMediaController mMediaController = null;

    @Override
    public void init(View view) {
        super.init();
        RunningLog.run("SimpleRecordPresentation init");
        if (super.contentView == null) {
            super.contentView = view;
            FrameLayout frameLayout = (FrameLayout) view;
            View tipes = getView(frameLayout, R.id.tipes);
            if (tipes != null) {
                tipes.setVisibility(View.GONE);
            }
            if (inflateView == null) {
                inflateView =
                        LayoutInflater.from(view.getContext()).inflate(getLayout(), frameLayout, false);
            }
            frameLayout.addView(
                    inflateView,
                    new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            initControlPanel(inflateView);
            /*
            if (ijkVideoView == null) {
                IjkMediaPlayer.loadLibrariesOnce(null)
                IjkMediaPlayer.native_profileBegin("libijkplayer.so")
                ijkVideoView = IjkVideoView(super.screen.context)
                val params = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                screen.addView(ijkVideoView, params)
                mMediaController = AndroidMediaController(screen.context, false)
                mMediaController!!.setShowable(false)
                ijkVideoView!!.setMediaController(mMediaController)
                ijkVideoView!!.setRender(IjkVideoView.RENDER_TEXTURE_VIEW)
            }
            super.player.setPlayView(ijkVideoView)
            super.player.setPlayerParam<String>(record.playUrl)
             */
        } else {
            /*
            if (ijkVideoView != null) {
                super.player.setPlayView(ijkVideoView)
            }
             */
        }
        hasInit = true;
    }

    @Override
    public boolean isInit() {
        return this.hasInit;
    }

    @Override
    protected int getLayout() {
        return R.layout.record_simple_layout;
    }

    @Override
    public void release() {
        super.release();
        if (super.record != null) {
            super.record.setRecordListener(null);
        }
        ijkVideoView.setMediaController(null);
        ijkVideoView.setRender(IjkVideoView.RENDER_NONE);
        ijkVideoView.stopPlayback();
        ijkVideoView.release(true);
        ijkVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
        super.screen.removeView(ijkVideoView);
        ijkVideoView = null;

        if (mMediaController != null) {
            mMediaController.setAnchorView(null);
            mMediaController.setPrevNextListeners(null, null);
            mMediaController = null;
        }
        ((FrameLayout) super.contentView).removeView(inflateView);
        super.screen = null;
        super.contentView = null;
        this.hasInit = false;
        super.start = null;
        super.pause = null;
        super.stop = null;
    }

    @Override
    public void onException(Exception exception) {
        RunningLog.error(exception.getMessage());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (super.stop != null && super.start != null && super.pause != null) {
                super.stop.getChildAt(1).setVisibility(View.GONE);
                super.start.getChildAt(1).setVisibility(View.GONE);
                super.pause.getChildAt(1).setVisibility(View.GONE);
            }

        });

        handler.postDelayed(() -> {
            if (super.contentView != null) {
                Toast.makeText(super.contentView.getContext(), "操作出现异常", Toast.LENGTH_SHORT).show();
            }
        }, 1000);


    }
}
