package com.sunmnet.mediaroom.tabsp.desktop.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.BaseFragment;
import com.sunmnet.mediaroom.common.impl.DownloadAndApkInstallListener;
import com.sunmnet.mediaroom.common.tools.AndroidUtils;
import com.sunmnet.mediaroom.common.tools.JsonUtils;
import com.sunmnet.mediaroom.tabsp.desktop.DesktopApplication;
import com.sunmnet.mediaroom.tabsp.desktop.R;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkInterface;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sunmnet.mediaroom.tabsp.desktop.view.AbstractNetworkFragment.NETWORK_KEY;
import static com.sunmnet.mediaroom.tabsp.desktop.view.DownloadDialogFragment.DOWNLOAD_FILE_KEY;
import static com.sunmnet.mediaroom.tabsp.desktop.view.DownloadDialogFragment.DOWNLOAD_PATH_KEY;

public class VersionFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_version_layout, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.id_update})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.id_update:
                String json = AndroidUtils.getProperty(BaseApplication.getInstance(), DesktopApplication.DB_NAME, NETWORK_KEY);
                try {
                    NetworkInterface cacheBean = (NetworkInterface) JsonUtils.jsonToBean(json, NetworkInterface.class);
                    if (!TextUtils.isEmpty(cacheBean.getPlatformIP()) && !TextUtils.isEmpty(cacheBean.getPlatformPort())) {
                        Bundle bundle=new Bundle();
                        String path="http://"+cacheBean.getPlatformIP()+":"+cacheBean.getPlatformPort()+"/files/";
                        String fileName="tabsp.desktop.apk";
                        bundle.putString(DOWNLOAD_PATH_KEY,path);
                        bundle.putString(DOWNLOAD_FILE_KEY,fileName);
                        final DownloadDialogFragment downloadDialogFragment=new DownloadDialogFragment();
                        downloadDialogFragment.setArguments(bundle);
                        downloadDialogFragment.setDownloadListener(new DownloadAndApkInstallListener());
                        downloadDialogFragment.show(getFragmentManager(),"更新桌面应用");
                    } else if (this.uiInterface != null)
                            this.uiInterface.toastMessage("请先配置平台数据!!");
                } catch (Exception e) {
                    if (this.uiInterface != null) this.uiInterface.toastMessage("请先配置平台地址!!");
                }
                break;
        }
    }
}
