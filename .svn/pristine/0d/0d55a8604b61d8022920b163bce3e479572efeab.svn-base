package com.sunmnet.mediaroom.brand.impl.play;

import android.os.Handler;
import android.os.Looper;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.brand.data.file.ProgramResource;
import com.sunmnet.mediaroom.brand.interfaces.play.ResourceDownloadListener;
import com.sunmnet.mediaroom.brand.bean.event.PlayEvent;
import com.sunmnet.mediaroom.brand.bean.play.ProgramInfo;
import com.sunmnet.mediaroom.brand.bean.play.ProgramRule;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;


public class Program extends AbstractPlayable<ProgramRule> {

    private ProgramRule rule;

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    private Map<String, ProgramResource> programResources = new HashMap<>();
    private Map<String, Integer> programsCarousel = new HashMap<>();

    private PlayState prepareState = PlayState.INITIALIZED;

    private ResourceDownloadListener resourceDownloadListener = new ResourceDownloadListener() {
        @Override
        public void onSuccess(String id) {
            RunningLog.run("资源文件下载解压成功，节目ID：" + id);
            //下载完需要播放
            if (playState == PlayState.PREPLAY) {
                boolean allLoaded = true;
                for (ProgramResource resource : programResources.values()) {
                    allLoaded = allLoaded & resource.isDownloaded();
                }
                if (allLoaded) {//节目已全部下载完，开始播放
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
                    programResources.get(id).startDownload(resourceDownloadListener);
                }
            }, 30000);
        }
    };

    public Program(ProgramRule rule) {
        this.rule = rule;
        for (ProgramInfo programInfo : rule.getProgram()) {
            if (programResources.get(programInfo.getProgramId()) == null) {
                programResources.put(programInfo.getProgramId(), new ProgramResource(programInfo));
            }
            programsCarousel.put(programInfo.getProgramId(), programInfo.getCarousel());
        }
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
            if (prepareState != PlayState.PREPARED) {
                prepareState = PlayState.PREPARING;
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        for (ProgramResource resource : programResources.values()) {
                            resource.getProgramLayoutBean();
                        }
                        prepareState = PlayState.PREPARED;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                playState = PlayState.PLAYING;
                            }
                        });
                        EventBus.getDefault().post(new PlayEvent(PlayEvent.START_PLAY, Program.this));
                    }
                };
                thread.start();
            } else {
                playState = PlayState.PLAYING;
                EventBus.getDefault().post(new PlayEvent(PlayEvent.START_PLAY, this));
            }
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
        boolean allDownloaded = true;
        for (ProgramResource resource : programResources.values()) {
            allDownloaded = allDownloaded & resource.isDownloaded();
            if (!resource.isDownloaded()) {
                resource.startDownload(resourceDownloadListener);
                prepareState = PlayState.LOADING;
            }
        }
        return allDownloaded;
    }

    @Override
    public ProgramRule getRule() {
        return rule;
    }

    public Map<String, ProgramResource> getProgramResources() {
        return programResources;
    }

    public int getProgramCarousel(String programId) {
        return programsCarousel.get(programId) == null ? 10 : programsCarousel.get(programId);
    }
}
