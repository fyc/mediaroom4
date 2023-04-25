package com.sunmnet.mediaroom.tabsp.record.impl.hik.impl;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.tabsp.record.R;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractPrenstation;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.HikPlayView;
import com.sunmnet.mediaroom.tabsp.record.impl.hik.HikRecord;

import java.util.HashMap;
import java.util.Map;

public class HikPresentation extends AbstractPrenstation {
    private HikPlayView hikPlayView;
    private boolean isInit = false;
    private View inflateView;

    @Override
    public void init(View view) {
        super.init();
        if (!isInit) {
            this.contentView = view;
            FrameLayout frameLayout = (FrameLayout) view;
            View tipes = getView(frameLayout, R.id.tipes);
            if (tipes != null) {
                tipes.setVisibility(View.GONE);
            }
            inflateView = LayoutInflater.from(view.getContext()).inflate(getLayout(), frameLayout, false);
            frameLayout.addView(inflateView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initControlPanel(inflateView);
            if (this.screen != null) {
                screen.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        int width = screen.getWidth();
                        int height = screen.getHeight();
                        if (width > 0 && height > 0) {
                            if (hikPlayView == null) {
                                hikPlayView = new HikPlayView(screen.getContext());
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                screen.addView(hikPlayView, params);
                                hikPlayView.setSdk(((HikRecord) record).getSdk());
                                setPlayerParam();
                                hikPlayView.setM_iWidth(width);
                                hikPlayView.setM_iHeight(height);
                            }
                            player.setPlayView(hikPlayView);
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


    private void setPlayerParam() {
        HikRecord record = (HikRecord) this.record;
        int userId = record.getSdk().getUserID().intValue();
        int playHandle = record.getSdk().getPlayerHandle();
        Map<String, Integer> params = new HashMap<>();
        params.put("uid", userId);
        params.put("playHandle", playHandle);
        player.setPlayerParam(params);
    }

    @Override
    public void release() {
        super.release();
        ((ViewGroup)contentView).removeView(inflateView);
        this.hikPlayView = null;
        this.contentView = null;
        this.inflateView = null;
        isInit = false;
    }

}
