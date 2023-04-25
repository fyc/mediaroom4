package com.sunmnet.mediaroom.brand.control.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.control.other.AttendanceQRCodeControlProperty;
import com.sunmnet.mediaroom.brand.bean.response.dto.AttendanceQrCodeDto;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.QRCodeTool;
import com.sunmnet.mediaroom.common.bean.Result;
import com.sunmnet.mediaroom.common.tools.GsonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.lang.ref.WeakReference;
import java.util.Timer;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AttendanceQRCodeControl extends BaseFrameControl<AttendanceQRCodeControlProperty> {

    private ImageView imageView;
    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshControlData();
        }
    };

    private WeakReference<Bitmap> cacheBitmapReference;

    public AttendanceQRCodeControl(Context context) {
        super(context);
    }

    public AttendanceQRCodeControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public AttendanceQRCodeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        removeAllViews();
        addView(imageView);
    }

    @Override
    public void refreshControlData() {
        super.refreshControlData();
        //调用接口获取考勤二维码
        if (!DeviceApp.getApp().isRegistered()) {
            return;
        }
        RunningLog.run("正在请求考勤二维码数据");
        ApiManager.getCourseApi().getAttendQrCode(DeviceApp.getApp().getClassroomCode())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<Result<AttendanceQrCodeDto>>() {
                    @Override
                    public void onNext(Result<AttendanceQrCodeDto> response) {
                        //数据处理
                        if (response != null && response.isSuccess() && response.getObj() != null) {
                            String qrCodeStr = GsonUtil.objToJsonStr(response.getObj());
                            Bitmap bitmap = QRCodeTool.createQRCode(qrCodeStr);
                            imageView.setImageBitmap(bitmap);
                            if (cacheBitmapReference != null) {
                                Bitmap cacheBitmap = cacheBitmapReference.get();
                                if (cacheBitmap != null && !cacheBitmap.isRecycled()) {
                                    cacheBitmap.recycle();
                                }
                            }
                            cacheBitmapReference = new WeakReference<>(bitmap);
                        } else {
                            RunningLog.run("考勤二维码错误, response: " + response);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("请求考勤二维码错误： " + e.getMessage());
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
        if (timer != null)
            onControlHide();
        try {
            timer = new Timer();
            timer.schedule(new HandlerTimerTask(runnable), 0, 55000);//55秒刷新
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
