package com.sunmnet.mediaroom.brand.fragment.template.dialog;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.request.FaceLoginRequest;
import com.sunmnet.mediaroom.brand.bean.response.LoginResponse;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.data.database.face.UserInfo;
import com.sunmnet.mediaroom.brand.fragment.dialog.AbstractFaceRecogDialogFragment;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;
import com.sunmnet.mediaroom.brand.utils.VerifyUtil;
import com.sunmnet.mediaroom.brand.view.CvCameraView;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class FaceLoginDialogFragment extends AbstractFaceRecogDialogFragment implements View.OnClickListener {

    private static final String TAG = "FaceLoginDialog";

    public void setLoginListener(LoginFragmentDialog.LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private LoginFragmentDialog.LoginListener loginListener;

    protected AbstractFaceRecogDialogFragment.FaceRecogListener faceRecogListener = new AbstractFaceRecogDialogFragment.FaceRecogListener() {

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
            enableRecog.compareAndSet(true, false);
            if (recognizeSuccessAgain) {
                recognizeSuccessAgain = false;
                hideRecogResult();
                login(userInfo);
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

    private boolean isShown;
    private Button btn_back;
    private View btn_switch;
    private View image_circle;
    private View image_circle2;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    //识别结果提示框
    private TextView recognizeResult;

    // 重新识别到身份
    private volatile boolean recognizeSuccessAgain = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_face_login, container, false);
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
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvCameraView = (CvCameraView) view.findViewById(R.id.camera_view);
        image_circle = view.findViewById(R.id.image_circle1);
        image_circle2 = view.findViewById(R.id.image_circle2);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        btn_switch = view.findViewById(R.id.btn_switch);
        btn_switch.setOnClickListener(this);
        this.recognizeResult = view.findViewById(R.id.tv_recog_result);
    }

    @Override
    protected CvCameraView getCameraView(View view) {
        return view.findViewById(R.id.camera_view);
    }

    @Override
    protected FaceRecogListener getFaceRecogListener() {
        return faceRecogListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fullscreenDialogStyle);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        setCancelable(true);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        isShown = true;
        enableRecog.compareAndSet(false, true);
        ControlBaseHelper.refreshControlRecursion(getView());
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        isShown = false;
        enableRecog.compareAndSet(true, false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch:
                dismiss();
                LoginFragmentDialog fragmentDialog = new LoginFragmentDialog();
                fragmentDialog.setLoginListener(loginListener);
                fragmentDialog.show(getFragmentManager(), "login");
                break;
            case R.id.btn_back:
                if (isShown) {
                    dismiss();
                }
                break;
        }
    }


    private void login(UserInfo userInfo) {
        showProgressDialog();
        FaceLoginRequest request = new FaceLoginRequest();
        request.setDeviceCode(DeviceApp.getApp().getDevice().getDeviceCode());
        request.setTimestamp(System.currentTimeMillis() / 1000 + "");
        request.setUsername(userInfo.getUsername());
        //[ket] + [deviceCode] + [timestamp] + [username]
        String verifyCode = VerifyUtil.getVerifyCode(request.getDeviceCode(), request.getTimestamp(), request.getUsername());
        request.setVerifyCode(verifyCode);
        ApiManager.getSysApi().faceLogin(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleTaskObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse response) {
                        if (response == null) {
                            if (!isDetached() && getContext() != null) {
                                ToastUtil.show(DeviceApp.getApp(), "登录失败");
//                                getActivity().runOnUiThread(() -> {
//                                    recognizeResult.setText("登录失败");
//                                    recognizeResult.setVisibility(View.VISIBLE);
//                                });
                            }
                            enableRecog.compareAndSet(false, true);
                            RunningLog.debug("登录失败，登录结果数据格式转换出错");
                            return;
                        }
                        if (response.isSuccess() && response.getObj() != null) {
                            LoginResponse.Result loginResult = response.getObj();
                            if (loginListener != null) {
                                loginListener.onLoginSuccess(new LoginUser(loginResult));
                            }
                            RunningLog.debug("登录成功");
                            dismiss();
                        } else {
                            RunningLog.debug("登录失败 " + response.getMsg());
                            if (!isDetached() && getContext() != null) {
                                ToastUtil.show(DeviceApp.getApp(), "登录失败 " + response.getMsg());
//                                getActivity().runOnUiThread(() -> {
//                                    recognizeResult.setText("登录失败 " + response.getMsg());
//                                    recognizeResult.setVisibility(View.VISIBLE);
//                                });
                            }
                            enableRecog.compareAndSet(false, true);
                        }
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.debug("登录失败：" + e.getMessage());
                        if (!isDetached() && getContext() != null) {
                            ToastUtil.show(DeviceApp.getApp(), "登录失败：" + e.getMessage());
//                            getActivity().runOnUiThread(() -> {
//                                recognizeResult.setText("登录失败：" + e.getMessage());
//                                recognizeResult.setVisibility(View.VISIBLE);
//                            });
                        }
                        enableRecog.compareAndSet(false, true);
                        hideProgressDialog();
                    }
                });

    }

    ProgressDialog progressDialog;

    private void showProgressDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getContext());
                }
                progressDialog.setTitle("登录");
                progressDialog.setMessage("正在登录，请稍等片刻...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }

    private void hideProgressDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
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
