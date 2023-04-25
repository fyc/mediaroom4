package com.sunmnet.mediaroom.tabsp.desktop.view;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.litesuits.common.assist.SilentInstaller;
import com.sunmnet.mediaroom.common.BaseDialogFragment;
import com.sunmnet.mediaroom.common.tools.OkHttpUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.desktop.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;


public class DownloadDialogFragment extends BaseDialogFragment {
    public static final String DOWNLOAD_PATH_KEY = "PATH";
    public static final String DOWNLOAD_FILE_KEY = "FILENAME";
    @BindView(R.id.downloadfilePath)
    EditText path;
    @BindView(R.id.downloadFileName)
    EditText fileName;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progressText)
    TextView progressText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    OkHttpUtil.DownloadListener downloadListener;
    public void setDownloadListener(OkHttpUtil.DownloadListener downloadListener){
        this.downloadListener=downloadListener;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_layout, null, false);
        ButterKnife.bind(this, view);
        String downloadPath = getArguments().getString(DOWNLOAD_PATH_KEY);
        String fileName = getArguments().getString(DOWNLOAD_FILE_KEY);
        path.setText(downloadPath);
        this.fileName.setText(fileName);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgress(Integer progress) {
        if (progress < 0) {
            this.progress.setProgress(0);
            this.progress.setVisibility(View.GONE);
        } else {
            this.progress.setProgress(progress);
            this.progressText.setText("下载进度:"+progress+"%");
            this.progress.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgress(String message) {
        this.progressText.setText(this.progressText.getText().toString() + "\n" + message);
    }

    @OnClick({R.id.opt_download,R.id.close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.opt_download:
                //开始下载文件
                ThreadUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        String downloadPath = path.getText().toString();
                        if (downloadPath != null && (downloadPath.indexOf("http://") == -1 && downloadPath.indexOf("https://") == -1)) {
                            downloadPath = "http://" + downloadPath;
                        }
                        String cachePath = Environment.getExternalStoragePublicDirectory("") + "/downloadCache/";
                        String savingFile = fileName.getText().toString();
                        downloadPath += savingFile;
                        final String ccPath=downloadPath;
                        if (TextUtils.isEmpty(savingFile)) savingFile = "download.apk";
                        OkHttpUtil.downloadFileByGet(downloadPath, null, null, cachePath, savingFile, downloadListener);
                    }
                });
                break;
            case R.id.close:
                dismiss();
                break;
        }
    }
}
