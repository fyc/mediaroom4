package com.sunmnet.mediaroom.brand.fragment.template.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.TemplateActivity2;
import com.sunmnet.mediaroom.brand.bean.user.LoginUser;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;

public class LoginResultFragmentDialog extends DialogFragment {

    private Handler mHandler = new Handler();


    public static LoginResultFragmentDialog newInstance(LoginUser loginUser) {
        Bundle args = new Bundle();
        LoginResultFragmentDialog fragment = new LoginResultFragmentDialog();
        args.putSerializable("user", loginUser);
        fragment.setArguments(args);
        return fragment;
    }

    LoginUser loginUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginUser = (LoginUser) getArguments().getSerializable("user");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        return inflater.inflate(R.layout.dialog_template_login_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView textView = (TextView) view.findViewById(R.id.tv_user);
        textView.setText(loginUser.getNameCn());
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayUtil.hideNavigationBar(getDialog().getWindow());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getContext(), TemplateActivity2.class);
                intent.putExtra("user", loginUser);
                startActivity(intent);
                dismiss();
            }
        }, 2000);
    }
}
