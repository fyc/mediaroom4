package com.sunmnet.mediaroom.tabsp.record.impl.hik;


import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.record.impl.AbstractPlayer;

import java.util.Map;

public class HikPlayer extends AbstractPlayer<HikPlayView> implements HikPlayView.PlayListener {
    protected HikPlayView playView;
    boolean isPlaying = false;
    int userId, playHandle;

    public HikPlayer() {

    }

    @Override
    public void onPlaying() {
        RunningLog.run("hikplayer is playing...");
        isPlaying = true;
    }

    @Override
    public void setPlayView(HikPlayView hikPlayView) {
        this.playView = hikPlayView;
        hikPlayView.setPlayListener(this);
    }

    @Override
    public <E> void setPlayerParam(E e) {
        Map<String, Integer> params = (Map<String, Integer>) e;
        this.userId = params.get("uid");
        this.playHandle = params.get("playHandle");
    }

    @Override
    public void onReady() {
        //连接成功！
        RunningLog.run("与录播主机成功建立连接!!可以播放视频");
        if (this.playView != null) this.playView.start(this.userId, this.playHandle);
    }

    @Override
    public void onPlayError() {
        RunningLog.run("hikplayer play error....");
        //重新加载
        isPlaying = false;
        if (playView != null) {
            playView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onReady();
                }
            }, 5000);
        }
    }

    @Override
    public void onPlayStop() {
        RunningLog.run("hikplayer play stop....");
        isPlaying = false;
    }

    @Override
    public void start() {
        if (!isPlaying && this.playView != null) this.onReady();
    }

    @Override
    public void stop() {
        if (this.playView != null) this.playView.stop();
        RunningLog.run("开始停止播放");
    }

    @Override
    public void pause() {
        if (this.playView != null) this.playView.stop();
        RunningLog.run("开始暂停播放");
    }

    @Override
    public void release() {
        if (this.playView != null) this.playView.stop();
        this.playView = null;
    }
}
