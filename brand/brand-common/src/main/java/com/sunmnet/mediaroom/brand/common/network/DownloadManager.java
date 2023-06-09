package com.sunmnet.mediaroom.brand.common.network;

import android.os.Environment;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.common.bean.DownloadFileInfo;
import com.sunmnet.mediaroom.brand.common.interfaces.DownloadListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载管理器，断点续传
 */
public class DownloadManager {

    private String DEFAULT_FILE_DIR;//默认下载目录
    private Map<String, DownloadTask> mDownloadTasks;//文件下载任务索引，String为url,用来唯一区别并操作下载的文件
    private static DownloadManager mInstance;

    /**
     * 下载文件
     */
    public void download(String... urls) {
        //单任务开启下载或多任务开启下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).startDownload();
            }
        }
    }


    // 获取下载文件的名称
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 暂停
     */
    public void pause(String... urls) {
        //单任务暂停或多任务暂停下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).pauseDownload();
            }
        }
    }

    /**
     * 取消下载
     */
    public void cancel(String... urls) {
        //单任务取消或多任务取消下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).cancelDownload();
            }
        }
    }

    /**
     * 添加下载任务
     */
    public void add(String url, boolean useRange, DownloadListener l) {
        add(url, null, null, useRange, l);
    }

    /**
     * 添加下载任务
     */
    public void add(String url, String filePath, boolean useRange, DownloadListener l) {
        add(url, filePath, null, useRange, l);
    }

    /**
     * 添加下载任务
     */
    public void add(String url, String filePath, String fileName, boolean useRange, DownloadListener l) {
        if (TextUtils.isEmpty(filePath)) {//没有指定下载目录,使用默认目录
            filePath = getDefaultDirectory();
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        if(!mDownloadTasks.containsKey(url))
            mDownloadTasks.put(url, new DownloadTask(new DownloadFileInfo(url, filePath, fileName), l, useRange));
    }

    /**
     * 默认下载目录
     * @return
     */
    private String getDefaultDirectory() {
        if (TextUtils.isEmpty(DEFAULT_FILE_DIR)) {
            DEFAULT_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "icheny" + File.separator;
        }
        return DEFAULT_FILE_DIR;
    }

    public static DownloadManager getInstance() {//管理器初始化
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    public DownloadManager() {
        mDownloadTasks = new HashMap<>();
    }

    
    public boolean isDownloading(String... urls) {
        //这里传一个url就是判断一个下载任务
        //多个url数组适合下载管理器判断是否作操作全部下载或全部取消下载
        boolean result = false;
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                result = mDownloadTasks.get(url).isDownloading();
            }
        }
        return result;
    }

    public void remove(String... urls) {
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                if (mDownloadTasks.get(url).isDownloading())
                    mDownloadTasks.get(url).cancelDownload();
                mDownloadTasks.remove(url);
            }
        }
    }
}
