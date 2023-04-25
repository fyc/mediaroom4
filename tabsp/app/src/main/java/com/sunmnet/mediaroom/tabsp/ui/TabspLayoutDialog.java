package com.sunmnet.mediaroom.tabsp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.BaseDialogFragment;
import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.common.tools.DateUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.utils.PackageTool;

/**
 * 网络异常提示弹框
 * */
public class TabspLayoutDialog extends BaseDialogFragment {
    int layout;
    TextView texitView;
    // 最多点击次数
    private static final int MAX_CLICK_TIMES = 3;
    // 点击次数
    private int click_times = 0;
    //倒计时
    private int secondCountdown = 60;
    // 点击退出倒计时
    private CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            click_times = 0;
        }
    };

    //重启倒计时
    private final CountDownTimer exitDownTimer = new CountDownTimer(secondCountdown* 1000L, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            texitView.setText(millisUntilFinished/1000+"");
        }

        @Override
        public void onFinish() {
            texitView.setText("");
            CommonUtil.restartAppSystem(texitView.getContext());
        }
    };

    public void setLayout(int layouId) {
        this.layout = layouId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (this.layout == 0) {
            TextView textView = new TextView(container.getContext());
            textView.setText(container.getContext().getString(R.string.tipes_unknow_tipes));
        } else {
            view = inflater.inflate(this.layout, null, false);
            //网络开小差了,正在努力重连中！强制退出
            texitView = view.findViewById(R.id.tv_exit);
            texitView.setOnClickListener(v -> {
                countDownTimer.cancel();
                click_times++;
                countDownTimer.start();
                if (click_times == MAX_CLICK_TIMES) {
                    click_times = 0;
                    countDownTimer.cancel();
                    PackageTool.getInstance(v.getContext()).startExistsApp(PackageTool.LAUNCHER_PACKAGE_NAME);
                    ShellUtils.execCommand("am force-stop com.sunmnet.mediaroom.tabsp");
                }
            });
            //为了应对中控核心升级后，面板卡死问题，故晚上断网之后重启app
            if(DateUtil.isNight()){
                exitDownTimer.start();
            }
        }
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        countDownTimer.cancel();
    }

}
