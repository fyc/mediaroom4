package com.sunmnet.mediaroom.brand.fragment.template.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.request.LoginRequest;
import com.sunmnet.mediaroom.brand.bean.response.LoginResponse;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.brand.utils.FaceHelper;
import com.sunmnet.mediaroom.common.tools.ToastUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginFragmentDialog extends DialogFragment implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPassword;
    private Button btn_login;
    private View btn_back;
    private View btn_switch;

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private LoginListener loginListener;

    public interface LoginListener {
        public void onLoginSuccess(LoginUser loginUser);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_template_login, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mUsername = view.findViewById(R.id.edt_user);
        mPassword = view.findViewById(R.id.edt_password);

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_switch = view.findViewById(R.id.btn_switch);
        btn_switch.setOnClickListener(this);
        btn_switch.setVisibility(FaceHelper.getInstance().isInited() ? View.VISIBLE : View.GONE);

        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fullscreenDialogStyle);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        setCancelable(false);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayUtil.hideNavigationBar(getDialog().getWindow());
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (!checkInputData()) {
                    return;
                }
                showProgressDialog();
                login();
                break;
            case R.id.btn_switch:
                dismiss();
                FaceLoginDialogFragment fragmentDialog = new FaceLoginDialogFragment();
                fragmentDialog.setLoginListener(loginListener);
                fragmentDialog.show(getFragmentManager(), "login");
                break;
            case R.id.btn_back:
                dismiss();
                break;
        }
    }

    private boolean checkInputData() {
        if (TextUtils.isEmpty(mUsername.getText())) {
            ToastUtil.show(getContext(), "请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(mPassword.getText())) {
            ToastUtil.show(getContext(), "请输入密码");
            return false;
        }
        return true;
    }

    private void login() {
        LoginRequest loginRequest = new LoginRequest(mUsername.getText().toString().trim(), mPassword.getText().toString().trim());
        Observable<LoginResponse> login = ApiManager.getSysApi().login(loginRequest);
        if (login == null)
            return;
        login.observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleTaskObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse != null && loginResponse.isSuccess() && loginResponse.getObj() != null) {
                    LoginResponse.Result loginResult = loginResponse.getObj();
                    if (loginListener != null) {
                        loginListener.onLoginSuccess(new LoginUser(loginResult));
                    }
                    dismiss();
                } else {
                    if (!isDetached() && getContext() != null) {
                        ToastUtil.show(getContext(), "登录失败" + (loginResponse != null && !TextUtils.isEmpty(loginResponse.getMsg()) ? "：" + loginResponse.getMsg() : ""));
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                hideProgressDialog();
                if (!isDetached() && getContext() != null) {
                    ToastUtil.show(getContext(), "登录失败：" + e.toString());
                }
            }
        });
    }

    ProgressDialog progressDialog;

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setTitle("登录");
        progressDialog.setMessage("正在登录，请稍等片刻...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
