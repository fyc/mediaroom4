package com.sunmnet.mediaroom.brand.control.media;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.media.VideoMonitorControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.CameraDeviceResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.BaseVideoControl;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.impl.camera.MonitorCamera;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.util.ArrayList;
import java.util.Timer;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 视频监控组件
 */
public class VideoMonitorControl extends BaseVideoControl<VideoMonitorControlProperty> {

    private int carousel;//轮播时间 单位：秒

    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mVideoPosition++;
            VideoMonitorControl.super.refreshControlData();
        }
    };

    public VideoMonitorControl(Context context) {
        super(context);
    }

    public VideoMonitorControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public VideoMonitorControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        if (carousel == 0)
            carousel = 10;
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        carousel = TypeUtil.objToIntDef(attrDataMap.get("carousel"), 10);
//    }

    @Override
    public void setProperty(VideoMonitorControlProperty property) {
        super.setProperty(property);
        carousel = TypeUtil.objToIntDef(property.getAttr().getCarousel(), 10);
    }

    @Override
    public void refreshControlData() {
        //super.refreshControlData();
        if (!DeviceApp.getApp().isRegistered()) {
            return;
        }
        //调用接口获取教室的监控摄像头数据
        RunningLog.run("正在请求教室的监控摄像头数据：" + DeviceApp.getApp().getClassroomCode());
        ApiManager.getDeviceApi()
                .getCameraByClassroom(DeviceApp.getApp().getClassroomCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<CameraDeviceResponse>() {
                    @Override
                    public void onNext(CameraDeviceResponse response) {
                        if (response == null) {
                            RunningLog.debug("获取教室的摄像头数据解析出错：null");
                            return;
                        }
                        if (!response.isSuccess() || response.getObj() == null) {
                            RunningLog.debug("获取教室的摄像头失败，success: " + response.isSuccess() + ".obj:" + response.getObj());
                            return;
                        }
                        if (response.getObj().size() == 0) {
                            ToastUtil.show(getContext(), "该教室无监控摄像头");
                        }
                        videoList = new ArrayList<>();
                        for (CameraDeviceResponse.Result result : response.getObj()) {
                            String url = MonitorCamera.create(result).getPlayUrl();
                            if (!TextUtils.isEmpty(url)) {
                                videoList.add(url);
                            } else {
                                Toast.makeText(getContext(), "请配置'" + result.getDeviceName() + "'的'视频平台码流'", Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), "然后重新发布节目", Toast.LENGTH_LONG).show();
                                RunningLog.error("请配置'" + result.getDeviceName() + "'的'视频平台码流', 然后重新发布节目");
                            }
                        }
                        VideoMonitorControl.super.refreshControlData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.error("请求教室的监控摄像头数据失败：" + e.getMessage());
                    }
                });
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
        if (timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
