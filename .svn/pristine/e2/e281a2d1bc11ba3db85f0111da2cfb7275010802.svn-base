package com.sunmnet.mediaroom.tabsp.desktop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sunmnet.mediaroom.common.BaseActivity;
import com.sunmnet.mediaroom.common.impl.DownloadAndApkInstallListener;
import com.sunmnet.mediaroom.tabsp.desktop.model.MainActivityPresenter;
import com.sunmnet.mediaroom.tabsp.desktop.utils.AppUtil;
import com.sunmnet.mediaroom.tabsp.desktop.view.DownloadDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    MainActivityPresenter presenter;
    @BindView(R.id.settings)
    Button settings;
    @BindView(R.id.showtext)
    TextView showText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        presenter = new MainActivityPresenter();
        presenter.init();
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            showText.setText("程序未启动，"+counting+"秒后自动启动程序");
            if (counting<=0){
                boolean isInstalled= AppUtil.checkAppInstalled(getApplicationContext(),"com.sunmnet.mediaroom.tabsp");
                if (!isInstalled){
                    //打开一个dialog,用于设置app路径下载
                    DownloadDialogFragment downloadDialogFragment=new DownloadDialogFragment();
                    Bundle bundle= new Bundle();
                    bundle.putString(DownloadDialogFragment.DOWNLOAD_PATH_KEY,"https://shouji.ssl.qihucdn.com/200114/0ff9933783b338eb29e42b235d2e1402/");
                    bundle.putString(DownloadDialogFragment.DOWNLOAD_FILE_KEY,"com.qihoo.appstore_300090015.apk");
                    downloadDialogFragment.setArguments(bundle);
                    downloadDialogFragment.setDownloadListener(new DownloadAndApkInstallListener());
                    downloadDialogFragment.show(getFragmentManager(),"下载窗口");
                }else{
                    /*Intent intent = new Intent(Intent.ACTION_MAIN);
                    ComponentName componentName = new ComponentName("com.sunmnet.mediaroom", "kuyu.com.xxxx.xxx.login.WelcomeActivity");
                    intent.setComponent(componentName);
                    startActivity(intent);*/
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.sunmnet.mediaroom.tabsp");
                    startActivity(LaunchIntent);
                }
                return;
            }else counting--;
            showText.postDelayed(this,1000);
        }
    };
    int counting=20;
    @OnClick({R.id.settings})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.settings:
                showText.removeCallbacks(runnable);
                Intent intent=new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        counting=20;
        showText.postDelayed(runnable,1000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        showText.removeCallbacks(runnable);
    }

}
