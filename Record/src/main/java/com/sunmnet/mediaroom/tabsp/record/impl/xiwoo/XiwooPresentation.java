package com.sunmnet.mediaroom.tabsp.record.impl.xiwoo;

import android.view.View;

import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;

/**
 * Create by WangJincheng on 2021-08-24
 * 希沃录播控制定制
 */

public class XiwooPresentation extends IjkPresentation {

    @Override
    protected <T extends View> void initControlPanel(T inflateView) {
        super.initControlPanel(inflateView);
        if (super.pause != null) {
            super.pause.setVisibility(View.GONE);
        }
    }
}
