package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class DefaultDownloadListener implements OkHttpUtil.DownloadListener {
    @Override
    public void onDownloading(int progress) {
        EventBus.getDefault().post(progress);
    }

    @Override
    public void onDownloadFailed(Exception e) {
        EventBus.getDefault().post(-1);
        RunningLog.error(e);
        EventBus.getDefault().post("文件下载异常!!");
    }

    @Override
    public void onDownloadFailed(String msg) {
        EventBus.getDefault().post(-1);
        EventBus.getDefault().post(msg);
        EventBus.getDefault().post("任务中止!!");
    }

    @Override
    public void onDownloadSuccess(File file) {
        EventBus.getDefault().post(-1);
        EventBus.getDefault().post("文件下载成功,保存路径：" + file.getAbsolutePath());

    }
}
