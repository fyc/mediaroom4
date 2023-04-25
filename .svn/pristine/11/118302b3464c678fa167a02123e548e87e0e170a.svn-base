package com.sunmnet.mediaroom.common;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sunmnet.mediaroom.common.interfaces.UIInterface;

public class BaseDialogFragment extends DialogFragment {
    UIInterface uiInterface;
    protected DialogInterface.OnDismissListener onCancelListener;
    boolean mDismissed;

    public void setOnCancelListener(DialogInterface.OnDismissListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(isCancelable());
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.half_black_alert_dialog);
        if (getActivity() instanceof UIInterface) {
            uiInterface = (UIInterface) getActivity();
        }
    }

    public boolean isCancelable() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.uiInterface = null;
    }

    protected void setOnClick(View view, View.OnClickListener listener) {
        if (view != null) view.setOnClickListener(listener);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mDismissed = true;
        if (this.onCancelListener != null) {
            this.onCancelListener.onDismiss(null);
        }
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        int show = super.show(transaction, tag);
        this.mDismissed = false;
        return show;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        this.mDismissed = false;
    }

    @Override
    public void showNow(FragmentManager manager, String tag) {
        super.showNow(manager, tag);
        this.mDismissed = false;
    }


    public boolean isDismissed() {
        return mDismissed;
    }
}
