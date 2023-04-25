package com.sunmnet.mediaroom.tabsp.desktop.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.custom.EditLayout;
import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.common.tools.AndroidUtils;
import com.sunmnet.mediaroom.common.tools.JsonUtils;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.desktop.DesktopApplication;
import com.sunmnet.mediaroom.tabsp.desktop.R;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkBean;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkInterface;
import com.sunmnet.mediaroom.tabsp.desktop.bean.WifiBean;
import com.sunmnet.mediaroom.tabsp.desktop.bean.events.Event;
import com.sunmnet.mediaroom.tabsp.desktop.databinding.WlanDataBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WlanFragment extends AbstractNetworkFragment {
    WlanDataBinding wlanDataBinding;
    WifiBean wifiBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载本地的一些配置
        initialize(new Runnable() {
            @Override
            public void run() {
                String json=AndroidUtils.getProperty(BaseApplication.getInstance(), DesktopApplication.DB_NAME,NETWORK_KEY);
                try {
                    WifiBean cacheBean= (WifiBean) JsonUtils.jsonToBean(json,WifiBean.class);
                    if (cacheBean.getNetworkName().equals("wlan")){
                        wifiBean=cacheBean;
                    }else{
                        wifiBean=getNetworkConfig();
                    }
                }catch (Exception e){
                    wifiBean=getNetworkConfig();
                }finally {
                    EventBus.getDefault().postSticky(wifiBean);
                }
            }
        });
    }
    private WifiBean getNetworkConfig(){
        WifiBean wifiBean=new WifiBean();
        loadNetwork(AndroidNetworkUtil.NetworkType.WIFI,wifiBean);
        return wifiBean;
    }
    @Override
    protected View getContentView(LayoutInflater inflater) {
        wlanDataBinding= DataBindingUtil.inflate(inflater,R.layout.wlan_setting_layout, null, false);
        return wlanDataBinding.getRoot();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(WifiBean data){
        this.wlanDataBinding.setWifi(data);
    }

    @Override
    public void run() {
        /*AndroidNetworkUtil.disableEthernet();
        AndroidNetworkUtil.enableWifi();
        setNetworkConfig(wifiBean);*/
        AndroidUtils.saveProperty(getActivity(), DesktopApplication.DB_NAME,NETWORK_KEY,JsonUtils.objectToJson(wifiBean));
    }
}
