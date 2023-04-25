package com.sunmnet.mediaroom.brand.fragment.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.event.RefreshAttendanceEvent;
import com.sunmnet.mediaroom.brand.bean.request.AttendSignInRequest2;
import com.sunmnet.mediaroom.brand.bean.response.ReserveInfoResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.data.database.face.UserInfo;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;
import com.sunmnet.mediaroom.brand.utils.VerifyUtil;
import com.sunmnet.mediaroom.brand.view.CvCameraView;
import com.sunmnet.mediaroom.common.bean.course.CourseSchedule;
import com.sunmnet.mediaroom.common.config.CourseConfig;
import com.sunmnet.mediaroom.common.tools.CourseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class FaceAttendanceDialogFragment extends AbstractFaceRecogDialogFragment {

    protected boolean isShown;
    protected Button btn_back;
    protected View image_circle, image_circle2;
    //识别结果提示框
    private TextView recognizeResult;

    protected int sendResultCount = 0;
    protected ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    protected FaceRecogListener faceRecogListener = new FaceRecogListener() {

        @Override
        public void recogCount(int count) {
            if (count > 0) {
                if (recognizeSuccessAgain) {
                    setRecogResutl("识别中，请保持正对摄像头");
                }
            } else {
                recognizeSuccessAgain = true;
                hideRecogResult();
            }
        }

        @Override
        public void recogSuccess(UserInfo userInfo) {
            if (recognizeSuccessAgain) {
                recognizeSuccessAgain = false;
                sendRecogResult(userInfo);
            }
        }

        @Override
        public void recogFail() {
            if (!isDetached() && getContext() != null) {
                if (recognizeSuccessAgain) {
                    setRecogResutl("识别失败");
                }
            }
        }
    };

    // 重新识别到身份
    private volatile boolean recognizeSuccessAgain = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_face_attendance, container, false);
    }

    @Override
    protected FaceRecogListener getFaceRecogListener() {
        return faceRecogListener;
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        super.onCameraViewStarted(width, height);
        image_circle.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.face_preview_rotate));
        image_circle2.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.face_preview_rotate2));
    }

    @Override
    public void onCameraViewStopped() {
        super.onCameraViewStopped();
        image_circle.clearAnimation();
        image_circle2.clearAnimation();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image_circle = view.findViewById(R.id.image_circle1);
        image_circle2 = view.findViewById(R.id.image_circle2);
        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShown) {
                    dismiss();
                }
            }
        });
        this.recognizeResult = view.findViewById(R.id.tv_recog_result);
    }

    @Override
    protected CvCameraView getCameraView(View view) {
        return view.findViewById(R.id.camera_view);
    }


    @Override
    public void onResume() {
        super.onResume();
        isShown = true;
        ControlBaseHelper.refreshControlRecursion(getView());
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        isShown = false;
        enableRecog.compareAndSet(true, false);
    }


    private void count() {
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        sendResultCount += 1;
        enableRecog.compareAndSet(true, false);
        writeLock.unlock();
    }

    private void countdown() {
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        sendResultCount -= 1;
        if (sendResultCount == 0) {
            enableRecog.compareAndSet(false, true);
        }
        writeLock.unlock();
    }

    protected void sendRecogResult(UserInfo userInfo) {
        count();
        Date nowDate = new Date();
        CourseSchedule courseSchedule = CourseHelper.getDefault().getCourseSchedule(nowDate, 0, "0", CourseConfig.PRE_START_TIME);
        if (courseSchedule == null) {
            if (!isDetached() && getContext() != null) {
                // ToastUtil.show(DeviceApp.getApp(), "当前不在考勤时间内");
                setRecogResutl("当前不在考勤时间内");
            }
            RunningLog.run("当前不在考勤时间内，中止上传签到结果");
            countdown();
            return;
        }

        AttendSignInRequest2 request = new AttendSignInRequest2();
        request.setAccountId(userInfo.getId());
        request.setDeviceCode(DeviceApp.getApp().getDevice().getDeviceCode());
        request.setClassNo(Integer.parseInt(courseSchedule.getClassNo()));
        request.setClassroomCode(DeviceApp.getApp().getClassroomCode());
        request.setAttenceMethod(AttendSignInRequest2.FACE_ATTEND_METHOD);
        request.setTimestamp(System.currentTimeMillis() + "");
        String verifyCode = VerifyUtil.getVerifyCode(request.getClassroomCode(), request.getTimestamp(), request.getAccountId());
        request.setVerifyCode(verifyCode);

        ApiManager.getCourseApi().attendSignIn(request).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<ReserveInfoResponse>() {
                    @Override
                    public void onNext(ReserveInfoResponse response) {
                        countdown();
                        RunningLog.debug("上传签到结果:" + (response != null && response.isSuccess()));
                        if (!isDetached() && getContext() != null) {
                            if (response != null && response.isSuccess()) {
                                setRecogResutl(userInfo.getName() + " 签到成功");
                            } else {
                                setRecogResutl(userInfo.getName() + ", " + response.getMsg());
                            }

                            ThreadUtils.execute(() -> {
                                try {
                                    // 延时，等待平台添加数据
                                    Thread.sleep(800);
                                    // 发送刷新考勤控件的考勤数据事件
                                    EventBus.getDefault().post(new RefreshAttendanceEvent());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        countdown();
                        RunningLog.debug("上传签到结果失败:" + (e == null ? "null" : e.getMessage()));
                        if (!isDetached() && getContext() != null) {
                            setRecogResutl(userInfo.getName() + " 签到失败");
                        }
                    }
                });
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, getTheme());
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        setCancelable(true);
        return dialog;
    }

    protected void resetDialogWh() {
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = (int) (dm.widthPixels * 0.75f);
            int height = (int) (dm.heightPixels * 0.90f);
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        resetDialogWh();
    }

    /**
     * 设置结果提示
     * @param result
     */
    private void setRecogResutl(String result) {
        requireActivity().runOnUiThread(() -> {
            this.recognizeResult.setText(result);
            this.recognizeResult.setVisibility(View.VISIBLE);
        });
    }

    /**
     * 隐藏结果提示
     */
    private void hideRecogResult() {
        requireActivity().runOnUiThread(() -> {
            this.recognizeResult.setText("");
            this.recognizeResult.setVisibility(View.INVISIBLE);
        });
    }
}
