package com.sunmnet.mediaroom.brand.impl.play;

import android.os.Handler;
import android.os.Looper;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.brand.bean.event.PlayEvent;
import com.sunmnet.mediaroom.brand.bean.play.TemplateProgramRule;
import com.sunmnet.mediaroom.brand.data.file.TemplateProgramResource;
import com.sunmnet.mediaroom.brand.interfaces.play.ResourceDownloadListener;

import org.greenrobot.eventbus.EventBus;


public class TemplateProgram extends AbstractPlayable<TemplateProgramRule> {

    private TemplateProgramRule rule;

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private TemplateProgramResource programResource;

    private ResourceDownloadListener resourceDownloadListener = new ResourceDownloadListener() {
        @Override
        public void onSuccess(String id) {
            RunningLog.run("资源文件下载解压成功，节目ID：" + id);
            //下载完需要播放
            if (playState == PlayState.PREPLAY) {
                if (programResource.isDownloaded()) {//节目已全部下载完，开始播放
                    playBeforeCheck();
                }
            }
        }

        @Override
        public void onFail(final String id) {
            //30秒后重新下载
            RunningLog.run("资源文件下载失败，三十秒后尝试重新下载，节目ID：" + id);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    programResource.startDownload(resourceDownloadListener);
                }
            }, 30000);
        }
    };

    public TemplateProgram(TemplateProgramRule rule) {
        this.rule = rule;
        programResource = new TemplateProgramResource(rule.getId(), rule.getResource());
        playState = PlayState.INITIALIZED;
    }

    @Override
    public void startPlay() {
        if (playState != PlayState.PLAYING && playState != PlayState.PREPLAY) {
            playState = PlayState.PREPLAY;
            playBeforeCheck();
        }
    }

    private void playBeforeCheck() {
        if (checkResourceDownload()) {
            //开始播放
            playState = PlayState.PLAYING;
            EventBus.getDefault().post(new PlayEvent(PlayEvent.START_PLAY, this));
        }
    }

    @Override
    public void stopPlay() {
        if (playState == PlayState.PLAYING)
            EventBus.getDefault().post(new PlayEvent(PlayEvent.STOP_PLAY, this));
        playState = PlayState.STOP;
    }

    @Override
    public synchronized boolean readyPlay() {
        return checkResourceDownload();
    }

    private boolean checkResourceDownload() {
        boolean allDownloaded = programResource.isDownloaded();
        if (!programResource.isDownloaded()) {
            programResource.startDownload(resourceDownloadListener);
        }
        return allDownloaded;
    }

    public TemplateProgramResource getProgramResource() {
        return programResource;
    }

    @Override
    public TemplateProgramRule getRule() {
        return rule;
    }

}
