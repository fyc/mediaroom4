package com.sunmnet.mediaroom.brand.data.file;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.LruCache;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.interfaces.DownloadListener;
import com.sunmnet.mediaroom.brand.common.network.DownloadManager;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.brand.bean.ProgramLayoutBean;
import com.sunmnet.mediaroom.brand.impl.play.Program;
import com.sunmnet.mediaroom.brand.interfaces.play.ResourceDownloadListener;
import com.sunmnet.mediaroom.brand.bean.play.ProgramInfo;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.brand.utils.UnZipUtil;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.util.FileUtils;
import com.sunmnet.mediaroom.util.MD5Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ProgramResource {


    private static final int MESSAGE_DOWNLOAD_SUCCESS = 0x1;
    private static final int MESSAGE_DOWNLOAD_FAIL = 0x2;


    private String programId;
    private String MD5;
    private String downloadPath;
    private String downloadFileName;
    private ProgramLayoutBean programLayoutBean;

    private static LruCache<String, ProgramLayoutBean> layoutBeanCache = new LruCache<>(20);

    private static Handler mHandler;
    private static final HashMap<String, Boolean> extractingArray = new HashMap<>();

    public ProgramResource(ProgramInfo programInfo) {
        this.programId = programInfo.getProgramId();
        this.MD5 = programInfo.getResFileMd5();
        this.downloadPath = programInfo.getResFilePath();
        this.downloadFileName = programInfo.getResFileName();
    }

    public boolean isDownloaded() {
        boolean downloaded = false;
        File md5File = new File(FileResourceUtil.getProgramResourcePath(programId, MD5), FileConstant.PROGRAM_RESOURCE_MD5_FILE_NAME);
        File layoutFile = new File(FileResourceUtil.getProgramResourcePath(programId, MD5), FileConstant.PROGRAM_LAYOUT_FILE_NAME);
        if (md5File.exists() && layoutFile.exists()) {
            String md5 = FileUtils.readFile(md5File, "utf-8").toString();
            if (TextUtils.equals(md5, this.MD5)) {
                downloaded = true;
            }
        }
        return downloaded;
    }

    public void startDownload(final ResourceDownloadListener listener) {
        if (isDownloading())
            return;
        RunningLog.run("开始下载节目资源文件：" + downloadFileName);
        DownloadManager downloadManager = DownloadManager.getInstance();
        final String downloadUrl = UrlUtil.getFullHttpUrl(DeviceApp.getApp().getPlServerAddr(), downloadPath);
        RunningLog.debug("下载地址：" + downloadUrl);
        downloadManager.add(downloadUrl, FileResourceUtil.getTempFolderPath(), downloadFileName, false,
                new DownloadListener() {
                    @Override
                    public void onFinished() {
                        unZipFile(new File(FileResourceUtil.getTempFolderPath(), downloadFileName), listener);
                    }

                    @Override
                    public void onProgress(float progress) {
                    }

                    @Override
                    public void onPause() {
                        if (listener != null)
                            listener.onFail(programId);
                    }

                    @Override
                    public void onCancel() {
                        if (listener != null)
                            listener.onFail(programId);
                    }

                    @Override
                    public void onFail() {
                        if (listener != null)
                            listener.onFail(programId);
                    }
                });
        downloadManager.download(downloadUrl);
    }

    public boolean isDownloading() {
        synchronized (extractingArray) {
            return DownloadManager.getInstance().isDownloading(UrlUtil.getFullHttpUrl(DeviceApp.getApp().getPlServerAddr(), downloadPath)) || (extractingArray.get(programId) != null && extractingArray.get(programId));
        }
    }

    private void unZipFile(final File file, final ResourceDownloadListener listener) {
        if (file == null) {
            afterDownloadFail(listener);
            return;
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (extractingArray) {
                        extractingArray.put(programId, true);
                    }
                    RunningLog.run("开始解压节目资源文件：" + downloadFileName);
                    String fileMD5 = MD5Util.getFileMD5String(file);
                    if (fileMD5 != null && MD5 != null && TextUtils.equals(fileMD5.toLowerCase(), MD5.toLowerCase())) {
                        //开始解压
                        UnZipUtil.upZipFile(file, FileResourceUtil.getProgramFolderPath() + MD5 + "/");
                        //记录MD5值
                        String md5FilePath = FileResourceUtil.getProgramResourcePath(programId, MD5) +
                                FileConstant.PROGRAM_RESOURCE_MD5_FILE_NAME;
                        FileUtils.writeFile(md5FilePath, MD5);

                        //发送下载解压成功信息
                        getHandler().obtainMessage(MESSAGE_DOWNLOAD_SUCCESS,
                                new ProgramResult<>(ProgramResource.this, listener))
                                .sendToTarget();
                    } else {
                        RunningLog.error("下载文件MD5校验出错：" + file.getName() + ",文件MD5：" + fileMD5 + ",");
                        getHandler().obtainMessage(MESSAGE_DOWNLOAD_FAIL,
                                new ProgramResult<>(ProgramResource.this, listener))
                                .sendToTarget();
                    }
                    file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                    RunningLog.error(e);
                    getHandler().obtainMessage(MESSAGE_DOWNLOAD_FAIL,
                            new ProgramResult<>(ProgramResource.this, listener))
                            .sendToTarget();
                } finally {
                    synchronized (extractingArray) {
                        extractingArray.remove(programId);
                    }
                }
            }
        };
        thread.start();
    }

    private void afterDownloadSuccess(ResourceDownloadListener listener) {
        if (listener != null)
            listener.onSuccess(programId);
    }

    private void afterDownloadFail(ResourceDownloadListener listener) {
        if (listener != null)
            listener.onFail(programId);
    }


    public ProgramLayoutBean getProgramLayoutBean() {
        if (programLayoutBean == null && isDownloaded()) {
            programLayoutBean = layoutBeanCache.get(MD5);
            if (programLayoutBean == null) {
                File layoutFile = new File(FileResourceUtil.getProgramResourcePath(programId, MD5), FileConstant.PROGRAM_LAYOUT_FILE_NAME);
                String jsonStr = FileUtils.readFile(layoutFile, "utf-8", "").toString();
                programLayoutBean = JacksonUtil.jsonStrToBean(jsonStr, ProgramLayoutBean.class);
                if (programLayoutBean != null)
                    programLayoutBean.getControlList();
                layoutBeanCache.put(MD5, programLayoutBean);
            }
        }
        return programLayoutBean;
    }

    private Handler getHandler() {
        synchronized (Program.class) {
            if (mHandler == null)
                mHandler = new InternalHandler();
            return mHandler;
        }
    }

    private static class InternalHandler extends Handler {

        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            ProgramResult<?> result = (ProgramResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_DOWNLOAD_SUCCESS:
                    result.resource.afterDownloadSuccess((ResourceDownloadListener) result.mData);
                    break;
                case MESSAGE_DOWNLOAD_FAIL:
                    result.resource.afterDownloadFail((ResourceDownloadListener) result.mData);
                    break;
            }
        }
    }

    private static class ProgramResult<Data> {
        final ProgramResource resource;
        final Data mData;

        ProgramResult(ProgramResource program, Data data) {
            resource = program;
            mData = data;
        }
    }
}
