package com.sunmnet.mediaroom.tabsp.record.impl.ava;


import android.view.View;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.R;
import com.sunmnet.mediaroom.tabsp.record.impl.IjkPresentation;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecord;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AvaRecordPrensetation extends IjkPresentation {
    protected int getLayout() {
        return R.layout.record_ava_layout;
    }

    private FrameLayout sleep;
    private View controlPanel;

    /**
     * 初始化录播控制面板上的按钮
     */
    protected void initControlPanel(View inflateView) {
        start = getView(inflateView, R.id.record_start);
        registerClick(start);
        pause = getView(inflateView, R.id.record_pause);
        registerClick(pause);
        stop = getView(inflateView, R.id.record_stop);
        registerClick(stop);

        sleep = getView(inflateView, R.id.record_wakeup);
        registerClick(sleep);
        screen = getView(inflateView, R.id.play_container);
        controlPanel = getView(inflateView, R.id.capturing_part);
        recordingTime = getView(inflateView, R.id.recording_time);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onStatusChanged(IRecord.IRecordStatus status) {
        switch (status.getRecordStatus()) {
            case RECODING:
                onRecording(status);
                break;
            case IDLE:
                onRecordIdle(status);
                break;
            case PAUSE:
                onRecordPaused(status);
                break;
            case SAVED:
                onRecordSaved(status);
                break;
            case SLEEP:
                onSleep();
                break;
        }
    }

    private void onSleep() {
        controlPanel.setVisibility(View.GONE);
        sleep.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isInit() {
        return isInit;
    }

    private void doWakeUp() {
        sleep.getChildAt(1).setVisibility(View.VISIBLE);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                boolean wakeUp = ((AvaRecord) record).wakeup();
                if (wakeUp) {
                    int count = 30;
                    boolean finish = false;
                    while (count > 0) {
                        IRecord.IRecordStatus status = record.getRecordState();
                        if (status != null) {
                            //未唤醒
                            switch (status.getRecordStatus()) {
                                case IDLE:
                                case PAUSE:
                                case CONNECTED:
                                case RECODING:
                                case SAVED:
                                    //唤醒成功
                                    finish = true;
                                    break;
                            }
                        }
                        if (finish) {
                            sleep.post(new Runnable() {
                                @Override
                                public void run() {
                                    controlPanel.setVisibility(View.VISIBLE);
                                    sleep.setVisibility(View.GONE);
                                }
                            });
                            break;
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        RunningLog.run("等待唤醒中....");
                        count--;
                    }
                    record.refresh();
                } else {
                    sleep.post(new Runnable() {
                        @Override
                        public void run() {
                            toast("唤醒失败!!");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.record_wakeup) {
            doWakeUp();
        } else super.onClick(view);
    }
}
