package com.sunmnet.mediaroom.brand.common.network;

import android.os.AsyncTask;

import com.sunmnet.mediaroom.brand.common.bean.DownloadFileInfo;
import com.sunmnet.mediaroom.brand.common.interfaces.DownloadListener;
import com.sunmnet.mediaroom.brand.common.utils.DownloadUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

public class DownloadTask extends AsyncTask<Void, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;

    public static final int TYPE_FAILED = 1;

    public static final int TYPE_PAUSED = 2;

    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled = false;
    private boolean isPaused = false;
    private boolean isDownloading = false;

    private int lastProgress;

    private DownloadFileInfo fileInfo;

    private File mTmpFile;//临时占位文件
    private File mCacheFile;//缓存下载情况文件
    private boolean useRange = false;//是否使用断点续传

    /**
     * @param info     下载的文件信息
     * @param listener 回调
     * @param useRange 是否使用断点续传
     */
    public DownloadTask(DownloadFileInfo info, DownloadListener listener, boolean useRange) {
        this.listener = listener;
        this.fileInfo = info;
        this.useRange = useRange;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        InputStream is = null;
        RandomAccessFile tmpAccessFile = null;
        File file = new File(fileInfo.getFilePath(), fileInfo.getFileName());

        long downloadLength = 0;//记录已经下载的文件长度

        mCacheFile = new File(fileInfo.getFilePath(), fileInfo.getFileName() + ".cache");//记录已下载长度的缓存文件

        if (mCacheFile.exists()) {// 如果文件存在,并使用断点续传
            if (useRange)
                downloadLength = TypeUtil.objToLong(FileUtils.readFile(mCacheFile, "uft-8").toString());
            else
                mCacheFile.delete();
        }

        //文件下载地址
        String downloadUrl = fileInfo.getUrl();
        //下载文件的名称
        String fileName = fileInfo.getFileName();
        //下载文件存放的目录
        String directory = fileInfo.getFilePath();

        //得到下载内容的大小
        long contentLength = 0;
        try {
            contentLength = DownloadUtil.getInstance().getContentLength(fileInfo.getUrl());
        } catch (Exception e){
            e.printStackTrace();
        }

        if (contentLength == 0) {
            return TYPE_FAILED;
        }

        // 在本地创建一个与资源同样大小的文件来占位
        mTmpFile = new File(fileInfo.getFilePath(), fileInfo.getFileName() + ".tmp");

        if (!mTmpFile.getParentFile().exists())
            mTmpFile.getParentFile().mkdirs();
        Response response = null;
        try {
            response = DownloadUtil.getInstance().downloadFile(fileInfo.getUrl());
            tmpAccessFile = new RandomAccessFile(mTmpFile, "rw");
            //tmpAccessFile.setLength(contentLength);

            if (response != null) {
                is = response.body().byteStream();
                tmpAccessFile.seek(downloadLength);//跳过已经下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        tmpAccessFile.write(b, 0, len);
                        if (useRange && mCacheFile != null)
                            FileUtils.writeFile(mCacheFile.getAbsolutePath(), (total + downloadLength) + "");
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }

                }
                if (file.exists()) {
                    file.delete();
                }
                boolean success = mTmpFile.renameTo(file);//下载完毕后，重命名目标文件名
                return success ? TYPE_SUCCESS : TYPE_FAILED;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (response != null) {
                    response.body().close();
                }
                if (tmpAccessFile != null) {
                    tmpAccessFile.close();
                }
                if (isCanceled) {
                    cleanFile(file, mCacheFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        isDownloading = false;
        DownloadManager.getInstance().remove(fileInfo.getUrl());
        switch (status) {
            case TYPE_SUCCESS:
                cleanFile(mCacheFile, mTmpFile);
                listener.onFinished();
                break;
            case TYPE_FAILED:
                cleanFile(mCacheFile, mTmpFile);
                listener.onFail();
                break;
            case TYPE_PAUSED:
                if (!useRange)
                    cleanFile(mCacheFile, mTmpFile);
                listener.onPause();
                break;
            case TYPE_CANCELED:
                cleanFile(mCacheFile, mTmpFile);
                listener.onCancel();
                break;
            default:
                break;
        }
    }

    public void startDownload(){
        if (isDownloading)
            return;
        isDownloading = true;
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    /**
     * 删除临时文件
     */
    private void cleanFile(File... files) {
        for (File file : files) {
            if (null != file && file.exists())
                file.delete();
        }
    }

}
