package com.bozee.managerapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sunmnet.mediaroom.wirelessprojection.R;


class GeneralDialog extends Dialog {
    final String TAG=GeneralDialog.class.getName();
    private TextView mDialogTitleTv;
    private TextView mDialogMsgTv;
    private TextView mIsMsgRequestConnectTv;

    private Context mContext;
    private OnBtnClickListener mListener;

    GeneralDialog(Context mContext) {
        super(mContext, R.style.CustomerDialogTheme);
        this.mContext = mContext;
        this.init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogLayout = inflater.inflate(R.layout.dialog_layout, null);
        mDialogTitleTv = dialogLayout.findViewById(R.id.tv_dialog_title);
        mDialogMsgTv = dialogLayout.findViewById(R.id.tv_dialog_msg);
        mIsMsgRequestConnectTv = dialogLayout.findViewById(R.id.tv_is_msg_request_connect);
        TextView mDialogNegativeTv = dialogLayout.findViewById(R.id.btn_dialog_negative);
        TextView mDialogPositiveTv = dialogLayout.findViewById(R.id.btn_dialog_positive);


        this.setContentView(dialogLayout);
        if (null != mDialogPositiveTv && null != mDialogNegativeTv) {
            mDialogPositiveTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.ok();
                    }
                    dismiss();
                }
            });

            mDialogNegativeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.cancel();
                    }
                    dismiss();
                }
            });
        }

    }

    void setClickListener(OnBtnClickListener mListener) {
        this.mListener = mListener;
    }



    @SuppressLint("SetTextI18n")
    void setMsg(String msg) {
        if (this.mDialogMsgTv == null) {
            Log.i(TAG,"set msg");
        } else {
            this.mDialogMsgTv.setText(msg + " ");
        }
    }

    void setTitle(String msg) {
        if (mDialogTitleTv == null) {
            Log.i(TAG,"set title");
        } else {
            mDialogTitleTv.setText(msg);
        }
    }

    void setRequestMsgVisible(int i) {
        if (this.mIsMsgRequestConnectTv == null) {
            Log.i(TAG,"re no");
        } else {
            this.mIsMsgRequestConnectTv.setVisibility(i);
        }
    }

    public interface OnBtnClickListener {
        void ok();

        void cancel();
    }
}

