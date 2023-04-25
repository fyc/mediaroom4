package com.sunmnet.mediaroom.brand.fragment.template.dialog;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.fragment.dialog.FaceAttendanceDialogFragment;

/**
 * 模板节目的人脸识别签到弹窗
 */
public class TemplateFaceAttendanceDialog extends FaceAttendanceDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_template_face_attendance, container, false);
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
    protected void resetDialogWh() {

    }
}
