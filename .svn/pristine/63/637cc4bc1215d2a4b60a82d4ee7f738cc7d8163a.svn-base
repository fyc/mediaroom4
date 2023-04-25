package com.sunmnet.mediaroom.tabsp.record.impl;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.R;
import com.sunmnet.mediaroom.tabsp.record.RecordMaker;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordResult;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecord;
import com.sunmnet.mediaroom.tabsp.record.interfaces.IRecordPresentation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.UUID;


public abstract class AbstractPrenstation implements View.OnClickListener, IRecord.RecordListener, IRecordPresentation {
    protected IRecord record;
    protected AbstractPlayer player;
    protected FrameLayout start, stop, pause;
    protected FrameLayout screen;
    protected TextView recordingTime;
    protected IRecord.IRecordStatus status;
    protected View contentView;

    protected int getLayout() {
        return R.layout.record_hik_layout;
    }

    @CallSuper
    protected void init() {
        try {
            record = RecordMaker.getRecord();
            record.setRecordListener(this);
            player = RecordMaker.getRecordPlayer();
        } catch (Exception e) {
            RunningLog.error(e);
        }
    }

    @Override
    @CallSuper
    public void release() {
        if (record != null) {
            record.release();
        }
        if (player != null) {
            player.release();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void toast(String message) {
        if (this.start != null) {
            Toast.makeText(start.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public <T extends View> T getView(View view, @IdRes int resId) {
        return (T) view.findViewById(resId);
    }

    public void registerClick(View view) {
        if (view != null) view.setOnClickListener(this);
    }

    /**
     * 初始化录播控制面板上的按钮
     */
    protected <T extends View> void initControlPanel(T inflateView) {
        start = getView(inflateView, R.id.record_start);
        registerClick(start);
        pause = getView(inflateView, R.id.record_pause);
        registerClick(pause);
        stop = getView(inflateView, R.id.record_stop);
        registerClick(stop);
        screen = getView(inflateView, R.id.play_container);
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
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onControlResult(RecordResult result) {
        if (!result.isSuccess()) {
            toast(result.getMessage());
        }
        this.stop.getChildAt(1).setVisibility(View.GONE);
        this.start.getChildAt(1).setVisibility(View.GONE);
        this.pause.getChildAt(1).setVisibility(View.GONE);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                record.refresh();
            }
        });
    }

    private void setView(FrameLayout opt, int drawable, boolean enable) {
        if (opt == null) return;
        opt.setEnabled(enable);
        ImageView imageView = (ImageView) opt.getChildAt(0);
        if (imageView != null) {
            imageView.setImageResource(drawable);
        }
        View progress = opt.getChildAt(1);
        if (progress != null) progress.setVisibility(View.GONE);
    }

    @Override
    public <T> void onRecordPaused(T t) {
        this.status = (IRecord.IRecordStatus) t;
        setView(start, R.drawable.player_start, true);
        setView(pause, R.drawable.player_pause_disable, false);
        setView(stop, R.drawable.player_stop, true);
    }

    @Override
    public <T> void onRecording(T t) {
        if (this.status == null || this.status.getRecordStatus() != IRecord.RecordStatus.RECODING) {
            setView(start, R.drawable.player_start_disable, false);
            setView(pause, R.drawable.player_pause, true);
            setView(stop, R.drawable.player_stop, true);
        }
        this.status = (IRecord.IRecordStatus) t;
        if (recordingTime != null) {
            recordingTime.setText(status.getRecordingTime());
        }
    }

    @Override
    public <T> void onRecordIdle(T t) {
        this.status = (IRecord.IRecordStatus) t;
        setView(start, R.drawable.player_start, true);
        setView(pause, R.drawable.player_pause_disable, false);
        setView(stop, R.drawable.player_stop_disable, false);
        if (recordingTime != null) {
            recordingTime.setText("");
        }
    }

    @Override
    public <T> void onRecordSaved(T t) {
        onRecordIdle(t);
    }

    private void start() {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                boolean isSucces = record.start(UUID.randomUUID().toString());

                onOptSuccess(isSucces, "开始");
            }
        });
    }

    private void stop() {
        this.stop.getChildAt(1).setVisibility(View.VISIBLE);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = record.stop(status.getUUId());
                onOptSuccess(isSuccess, "结束");
            }
        });
    }

    private void onOptSuccess(boolean isSuccess, final String optName) {
        if (isSuccess) {
            this.screen.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(screen.getContext(), "录播" + optName + "!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            this.screen.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(screen.getContext(), "录播" + optName + "操作失败！！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void pause() {
        this.pause.getChildAt(1).setVisibility(View.VISIBLE);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = record.pause(status.getUUId());
                onOptSuccess(isSuccess, "暂停");
            }
        });
    }

    private void goon() {
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                boolean isSuccess = record.goon(status.getUUId());
                onOptSuccess(isSuccess, "继续");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.record_pause) {
            pause();
        } else if (view.getId() == R.id.record_start) {
            if (this.status != null) {
                //只能在这里做开始、暂停
                switch (this.status.getRecordStatus()) {
                    case PAUSE:
                        this.start.getChildAt(1).setVisibility(View.VISIBLE);
                        goon();
                        break;
                    case IDLE:
                        this.start.getChildAt(1).setVisibility(View.VISIBLE);
                        start();
                        //开始
                        break;
                }
            }
        } else if (view.getId() == R.id.record_stop) {
            stop();
        }
    }
}
