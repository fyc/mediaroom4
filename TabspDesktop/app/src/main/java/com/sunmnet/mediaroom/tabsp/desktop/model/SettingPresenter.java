package com.sunmnet.mediaroom.tabsp.desktop.model;


import android.text.TextUtils;

import com.sunmnet.mediaroom.common.interfaces.IDataBindings;
import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.desktop.bean.NetworkBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SettingPresenter implements Runnable {
    IDataBindings bindings;

    public SettingPresenter(IDataBindings bindings) {
        this.bindings = bindings;
    }
    public void loadData() {
        ThreadUtils.execute(this);
    }

    @Override
    public void run() {
        String command = "netcfg";
        ShellUtils.CommandResult result = ShellUtils.execCommand(command);
        RunningLog.run(result.result == 0 ? result.successMsg : result.errorMsg);
        List<NetworkBean> networkBeans = new ArrayList<>();
        NetworkBean bean=new NetworkBean();
        bean.setNetworkName("eth0");
        if (AndroidNetworkUtil.checkNetwork(AndroidNetworkUtil.NetworkType.ETHERNET)){
            bean.setState("0");
        }else bean.setState("1");
        networkBeans.add(bean);

        bean=new NetworkBean();
        bean.setNetworkName("wlan0");
        if (AndroidNetworkUtil.checkNetwork(AndroidNetworkUtil.NetworkType.WIFI)){
            bean.setState("0");
        }else bean.setState("1");
        networkBeans.add(bean);
        handleState(networkBeans);
    }

    private void handleState(List<NetworkBean> beans) {
        Map<String,NetworkBean> maps=new LinkedHashMap<>();
        for (NetworkBean bean:beans) {
            //shell 获取对应的数据
            String result=AndroidNetworkUtil.getConfig(bean.getNetworkName());
            int index=result.indexOf("ip");
            result=result.substring(index+2+1,result.length());
            index=result.indexOf(" ");
            String ip=result.substring(0,index);
            result=result.substring(index+1,result.length());
            index=result.indexOf("mask");
            result=result.substring(index+4+1,result.length());
            index=result.indexOf(" ");
            String mask=result.substring(0,index);
            bean.setLocalIP(ip);
            bean.setNetmask(mask);
            String dns1="net.dns1]:";
            dns1=AndroidNetworkUtil.getProp(dns1);
            index=dns1.indexOf(":");
            if (index>=0){
                dns1=dns1.substring(index+1,dns1.length());
                dns1=dns1.replace("[","");
                dns1=dns1.replace("]","");
                bean.setDns1(dns1);
            }
            dns1="net.dns2]:";
            dns1=AndroidNetworkUtil.getProp(dns1);
            index=dns1.indexOf(":");
            if (index>=0){
                dns1=dns1.substring(index+1,dns1.length());
                dns1=dns1.replace("[","");
                dns1=dns1.replace("]","");
                bean.setDns2(dns1);
            }
            maps.put(bean.getNetworkName(),bean);
        }
        this.bindings.binding(maps);
    }
}
