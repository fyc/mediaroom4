package com.sunmnet.mediaroom.tabsp.desktop.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.BaseFragment;
import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.desktop.R;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkInterface;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class AbstractNetworkFragment extends BaseFragment implements Runnable {
    public static final String NETWORK_KEY = "NETWORK_KEY";

    protected abstract View getContentView(LayoutInflater inflater);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = getContentView(inflater);
        ButterKnife.bind(this, view);
        return view;
    }

    protected void loadNetwork(AndroidNetworkUtil.NetworkType networkType, NetworkInterface bean)  {
        if (AndroidNetworkUtil.checkNetwork(networkType)) {
            bean.setState("0");
        } else bean.setState("1");
        String result = AndroidNetworkUtil.getConfig(bean.getNetworkName());
        int index = result.indexOf("ip");
        result = result.substring(index + 2 + 1, result.length());
        index = result.indexOf(" ");
        String ip = result.substring(0, index);
        result = result.substring(index + 1, result.length());
        index = result.indexOf("mask");
        result = result.substring(index + 4 + 1, result.length());
        index = result.indexOf(" ");
        String mask = result.substring(0, index);
        bean.setLocalIP(ip);
        bean.setNetmask(mask);
        String dns1 = "net.dns1]:";
        dns1 = AndroidNetworkUtil.getProp(dns1);
        index = dns1.indexOf(":");
        if (index >= 0) {
            dns1 = dns1.substring(index + 1, dns1.length());
            dns1 = dns1.replace("[", "");
            dns1 = dns1.replace("]", "");
            bean.setDns1(dns1);
        }
        dns1 = "net.dns2]:";
        dns1 = AndroidNetworkUtil.getProp(dns1);
        index = dns1.indexOf(":");
        if (index >= 0) {
            dns1 = dns1.substring(index + 1, dns1.length());
            dns1 = dns1.replace("[", "");
            dns1 = dns1.replace("]", "");
            bean.setDns2(dns1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.id_enable})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_enable:
                ThreadUtils.execute(this);
                break;
        }
    }
    protected boolean setNetworkConfig(NetworkInterface networkInterface){
        String result = AndroidNetworkUtil.setNetworkConfig(networkInterface.getNetworkName(), networkInterface.getLocalIP(), networkInterface.getGateway(),
                networkInterface.getNetmask(), networkInterface.getDns1(), networkInterface.getDns2());
        if (result != null) {
            uiInterface.toastMessage("网络配置异常:" + result);
            return false;
        } else{
            RunningLog.run("网络配置完成");
            return true;
        }
    }
}
