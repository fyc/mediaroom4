package com.sunmnet.mediaroom.tabsp.record.impl;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.VideoView;

import com.sunmnet.mediaroom.tabsp.record.R;


public class OrdinaryPresentation extends AbstractPrenstation {
    boolean isInit = false;
    VideoView videoView;
    @Override
    public void init(View view) {

        if (this.contentView != view) {
            this.contentView = view;
            init();
            FrameLayout frameLayout = (FrameLayout) view;
            this.contentView = frameLayout;
            View tipes = getView(frameLayout, R.id.tipes);
            if (tipes != null) {
                tipes.setVisibility(View.GONE);
            }
            View inflatView = LayoutInflater.from(view.getContext()).inflate(getLayout(), frameLayout, true);
            initControlPanel(inflatView);
            if (this.screen != null) {
                screen.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        int width = screen.getWidth();
                        int height = screen.getHeight();
                        if (width > 0 && height > 0) {
                            if (videoView == null) {
                                videoView = new VideoView(screen.getContext());
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                screen.addView(videoView, params);
                            }
                            player.setPlayView(videoView);
                            screen.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                    }
                });
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
    }
}
