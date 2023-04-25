package com.sunmnet.mediaroom.brand.control.media;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.BaseVideoControl;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.bean.control.media.StreamControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.media.StreamControlProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * 流媒体组件
 */
public class StreamControl extends BaseVideoControl<StreamControlProperty> {

    private int carousel = 10;//轮播时间 单位：秒

    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mVideoPosition++;
            refreshControlData();
        }
    };

    public StreamControl(Context context) {
        super(context);
    }

    public StreamControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public StreamControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        List<Map<String, Object>> list = TypeUtil.castToMapList_SO(attrDataMap.get("urls"));
//        videoList = new ArrayList<>();
//        for (Map<String, Object> map : list) {
//            videoList.add(TypeUtil.objToStr(map.get("path")));
//        }
//        carousel = TypeUtil.objToIntDef(attrDataMap.get("carousel"), 10);
//    }

    @Override
    public void setProperty(StreamControlProperty property) {
        super.setProperty(property);
        List<StreamControlAttr.Url> list = property.getAttr().getUrls();
        videoList = new ArrayList<>();
        for (StreamControlAttr.Url url : list) {
            videoList.add(url.getPath());
        }
        carousel = TypeUtil.objToIntDef(property.getAttr().getCarousel(), 10);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onControlShow();
    }

    @Override
    public void onControlShow() {
        super.onControlShow();
        if (timer != null)
            onControlHide();
        try {
            timer = new Timer();
            timer.schedule(new HandlerTimerTask(runnable), carousel * 1000, carousel * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onControlHide();
    }

    @Override
    public void onControlHide() {
        super.onControlHide();
        if(timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
