package com.sunmnet.mediaroom.tabsp;

import com.sunmnet.mediaroom.common.tools.AndroidNetworkUtil;
import com.sunmnet.mediaroom.tabsp.bean.NetworkInterface;

public class TabspNetworkUtil extends AndroidNetworkUtil {
    public static void loadNetwork(NetworkType networkType, NetworkInterface bean) {
        if (AndroidNetworkUtil.checkNetwork(networkType)) {
            bean.setState("0");
        } else bean.setState("1");
        String result = AndroidNetworkUtil.getConfig(bean.getNetworkName());
        int index = result.indexOf("ip");
        result = result.substring(index + 2 + 1, result.length());
        index = result.indexOf(" ");
        String ip = result.substring(0, index);
        ip = ip.trim();
        result = result.substring(index + 1, result.length());
        index = result.indexOf("mask");
        result = result.substring(index + 4 + 1, result.length());
        index = result.indexOf(" ");
        String mask = result.substring(0, index);
        mask = mask.trim();
        bean.setLocalIP(ip);
        bean.setNetmask(mask);
        String dns1 = "net.dns1]:";
        dns1 = AndroidNetworkUtil.getProp(dns1);
        index = dns1.indexOf(": ");
        if (index >= 0) {
            dns1 = dns1.substring(index + 1, dns1.length());
            dns1 = dns1.replace("[", "");
            dns1 = dns1.replace("]", "");
            dns1 = dns1.trim();
            bean.setDns1(dns1);
        }
        dns1 = "net.dns2]:";
        dns1 = AndroidNetworkUtil.getProp(dns1);
        index = dns1.indexOf(":");
        if (index >= 0) {
            dns1 = dns1.substring(index + 1, dns1.length());
            dns1 = dns1.replace("[", "");
            dns1 = dns1.replace("]", "");
            dns1 = dns1.trim();
            bean.setDns2(dns1);
        }
        bean.setGateway(AndroidNetworkUtil.getGateway());
    }
}
