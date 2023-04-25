package com.sunmnet.mediaroom.tabsp.desktop.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.common.tools.AndroidUtils;
import com.sunmnet.mediaroom.common.tools.JsonUtils;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.tabsp.desktop.DesktopApplication;
import com.sunmnet.mediaroom.tabsp.desktop.R;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkBean;
import com.sunmnet.mediaroom.tabsp.desktop.bean.WifiBean;
import com.sunmnet.mediaroom.tabsp.desktop.bean.events.Event;
import com.sunmnet.mediaroom.tabsp.desktop.databinding.EthernetDataBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EthernetFragment extends AbstractNetworkFragment {
    NetworkBean networkBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载本地的一些配置
        initialize(new Runnable() {
            @Override
            public void run() {
                String json= AndroidUtils.getProperty(BaseApplication.getInstance(), DesktopApplication.DB_NAME,NETWORK_KEY);
                try {
                    NetworkBean cacheBean= (NetworkBean) JsonUtils.jsonToBean(json,NetworkBean.class);
                    if (cacheBean.getNetworkName().equals("eth0")){
                        networkBean=cacheBean;
                    }else{
                        networkBean=getNetworkConfig();
                    }
                }catch (Exception e){
                    networkBean=getNetworkConfig();
                }finally {
                    EventBus.getDefault().postSticky(networkBean);
                }
            }
        });

    }
    private NetworkBean getNetworkConfig(){
        NetworkBean networkBean=new NetworkBean();
        loadNetwork(AndroidNetworkUtil.NetworkType.ETHERNET,networkBean);
        return networkBean;
    }
    EthernetDataBinding binding;
    @Override
    protected View getContentView(LayoutInflater inflater) {
        binding= DataBindingUtil.inflate(inflater,R.layout.ethernet_layout, null, false);
        return binding.getRoot();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(NetworkBean networkBean){
        binding.setNetwork(networkBean);
    }

    @Override
    public void run() {
        /*AndroidNetworkUtil.disableWifi();
        AndroidNetworkUtil.enableEthernet();
        setNetworkConfig(networkBean);*/
        AndroidUtils.saveProperty(getActivity(), DesktopApplication.DB_NAME,NETWORK_KEY,JsonUtils.objectToJson(networkBean));
    }
}
