package com.sunmnet.mediaroom.common.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.Map;

public class AndroidNetworkUtil {
    /**
     * 网络类型
     */
    public enum NetworkType {
        /**
         * 有线网络
         */
        ETHERNET,
        /**
         * wifi
         */
        WIFI,
        /**
         * 漫游
         */
        DATA
    }

    /**
     * 启动wifi
     */
    public static boolean enableWifi() {
        String command = "svc wifi enable";
        return execute(command);
    }

    /**
     * 关闭wifi
     */
    public static boolean disableWifi() {
        String command = "svc wifi disable";
        return execute(command);
    }
    /**
     * 检查网络是否连接成功
     *
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    public static boolean enableEthernet() {
        String command = "ifconfig eth0 up";
        return execute(command);
    }

    public static boolean disableEthernet() {
        String command = "ifconfig eth0 down";
        ShellUtils.CommandResult result = ShellUtils.execCommand(command, false);
        return result.result == 0;
    }

    private static boolean execute(String command) {
        ShellUtils.CommandResult result = ShellUtils.execCommand(command, true);
        return result.result == 0;
    }

    /**
     * 检查网络状态
     *
     * @param type 网络类型枚举
     */
    public static boolean checkNetwork(NetworkType type) {
        boolean isUp = false;
        String network = null;
        switch (type) {
            case ETHERNET:
                network = "eth0";
                break;
            case WIFI:
                network = "wlan0";
                break;
            default:
                return false;
        }
        String command = "netcfg | grep " + network;
        ShellUtils.CommandResult result = ShellUtils.execCommand(command);
        if (!TextUtils.isEmpty(result.successMsg)) {
            String[] strings = result.successMsg.split("\r\n");
            for (int i = 0; i < strings.length; i++) {
                String str = strings[i];
                if (str.contains(network) && (str.contains("up"))) {
                    isUp = true;
                    break;
                }
            }
        }
        return isUp;
    }

    public static String getGateway() {
        String command = "ip route list table 0 |grep \"proto static\"";
        ShellUtils.CommandResult result = ShellUtils.execCommand(command);
        if (result.result == 0) {
            String res = null, findstr = "default via ";
            res = result.successMsg.replace(findstr, "");
            int index = res.indexOf(" ");
            res = res.substring(0, index);
            return res;
        } else {
            return "127.0.0.1";
        }
    }

    public static String getConfig(String network) {
        String command = "ifconfig " + network;
        ShellUtils.CommandResult result = ShellUtils.execCommand(command);
        if (result.result == 0) {
            return result.successMsg;
        } else
            return network + ": ip 0.0.0.0 mask 255.255.255.0 flags [up broadcast running multicast]";
    }

    public static String getProp(String module) {
        String command = "getprop | grep " + module;
        ShellUtils.CommandResult result = ShellUtils.execCommand(command);
        if (result.result == 0) {
            return result.successMsg;
        } else return "";
    }

    public static String setNetworkConfig(String network, String ip, String gateway, String netmask, String dns1, String dns2) {

        StringBuilder builder = new StringBuilder();
        if (isLegalIP(ip) && isLegalIP(gateway) && isLegalIP(netmask)) {
            String ipCommand ="ip addr add "+ip+"/24 dev eth"+network; //"ifconfig " + network + " " + ip + " netmask " + netmask + " up";
            builder.append(ipCommand);
            /*ShellUtils.CommandResult result = ShellUtils.execCommand(ipCommand,false);
            if (result.result != 0) {
                return result.errorMsg;
            }*/
            builder.append(" & ");
            ipCommand = "route add default gw " + gateway + " dev " + network;
            builder.append(ipCommand);
/*
            result = ShellUtils.execCommand(ipCommand,false);
            if (result.result != 0) {
                return result.errorMsg;
            }*/
            String dnsCommand = null;
            if (dns1 != null) {
                dnsCommand = "setprop net.dns1 " + dns1;
                builder.append(" & ");
                builder.append(dnsCommand);
                /*result = ShellUtils.execCommand(dnsCommand,false);
                if (result.result != 0) {
                    return result.errorMsg;
                }*/
            }
            if (dns2 != null) {
                dnsCommand = "setprop net.dns2 " + dns1;
                builder.append(" & ");
                builder.append(dnsCommand);
               /* result = ShellUtils.execCommand(dnsCommand,false);
                if (result.result != 0) {
                    return result.errorMsg;
                }*/
            }
            ShellUtils.CommandResult result = ShellUtils.execCommand(builder.toString(), ShellUtils.isDeviceRooted());
            if (result.result != 0) {
                return result.errorMsg;
            }
        } else return null;

        return null;
    }

    public static boolean isLegalIP(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." +
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
                    "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }
}
