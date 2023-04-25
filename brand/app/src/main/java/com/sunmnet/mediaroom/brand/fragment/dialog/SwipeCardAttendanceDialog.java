package com.sunmnet.mediaroom.brand.fragment.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.bean.event.AttendanceEvent;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import org.greenrobot.eventbus.Subscribe;

/**
 * Create by WangJincheng on 2021-07-29
 * 刷卡考勤对话框
 */

public class SwipeCardAttendanceDialog extends AbstractSwipeCardDialogFragment {

    // 处理考勤消息Handler的key
    protected final static int HANDLER_ATTENDANCE_MSG_CODE = 0x01;
    // 处理考勤消息Handler的obj
    protected final static String HANDLER_ATTENDANCE_MSG_OBJ = "attendance_msg";

    protected TextView attendanceHint;
    protected MaterialButton btn_back;

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_ATTENDANCE_MSG_CODE:
                    attendanceHint.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_swipe_card_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attendanceHint = view.findViewById(R.id.tv_attendance_hint);
        btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        resetDialogWh();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(HANDLER_ATTENDANCE_MSG_OBJ);
    }

    @Override
    public void onResume() {
        super.onResume();
        ControlBaseHelper.refreshControlRecursion(getView());
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

    @Subscribe
    public void onAttendanceEvent(AttendanceEvent event) {
        RunningLog.run("收到考勤反馈信息:" + event.getMsg());
        String msg = event.getMsg();
        if (!msg.isEmpty()) {
            mHandler.removeCallbacksAndMessages(HANDLER_ATTENDANCE_MSG_OBJ);
            attendanceHint.setText(msg);
            attendanceHint.setVisibility(View.VISIBLE);
            Message handlerMsg = Message.obtain();
            handlerMsg.what = HANDLER_ATTENDANCE_MSG_CODE;
            handlerMsg.obj = HANDLER_ATTENDANCE_MSG_OBJ;
            mHandler.sendMessageDelayed(handlerMsg, 3000);
        }
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
}
