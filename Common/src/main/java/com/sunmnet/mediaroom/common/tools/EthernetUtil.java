package com.sunmnet.mediaroom.common.tools;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EthernetUtil {

    /**
     * 设置以太网动态获取IP
     */
    public static boolean setDynamicIp(Context context) {
        try {
            Class<?> ethernetManagerCls = Class.forName("android.net.EthernetManager");
            //获取EthernetManager实例
            Object ethManager = context.getSystemService("ethernet");
            //创建IpConfiguration
            Class<?> ipConfigurationCls = Class.forName("android.net.IpConfiguration");
            Object ipConfiguration = ipConfigurationCls.newInstance();
            //获取ipAssignment、proxySettings的枚举值
            Map<String, Object> ipConfigurationEnum = getIpConfigurationEnum(ipConfigurationCls);
            //设置ipAssignment
            Field ipAssignment = ipConfigurationCls.getField("ipAssignment");
            ipAssignment.set(ipConfiguration, ipConfigurationEnum.get("IpAssignment.DHCP"));
            //设置proxySettings
            Field proxySettings = ipConfigurationCls.getField("proxySettings");
            proxySettings.set(ipConfiguration, ipConfigurationEnum.get("ProxySettings.NONE"));
            //获取EthernetManager的setConfiguration()
            Method setConfigurationMethod = ethernetManagerCls.getDeclaredMethod("setConfiguration", ipConfiguration.getClass());
            //设置动态IP
            setConfigurationMethod.invoke(ethManager, ipConfiguration);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置以太网静态IP地址
     *
     * @param address ip地址
     * @param mask    子网掩码
     * @param gate    网关
     * @param dns     dns
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean setEthernetStaticIp(Context context, String address, String mask, String gate, String dns) {
        try {
            Class<?> ethernetManagerCls = Class.forName("android.net.EthernetManager");
            //获取EthernetManager实例
            @SuppressLint("WrongConstant")
            Object ethManager = context.getSystemService("ethernet");
            //创建StaticIpConfiguration
            Object staticIpConfiguration = newStaticIpConfiguration(address, gate, mask, dns);
            //创建IpConfiguration
            Object ipConfiguration = newIpConfiguration(staticIpConfiguration);
            Method setEthernetEnabled=ethernetManagerCls.getDeclaredMethod("setEthernetEnabled", boolean.class);
            //首先停止网络
            setEthernetEnabled.invoke(ethManager,false);
            //获取EthernetManager的setConfiguration()
            Method setConfigurationMethod = ethernetManagerCls.getDeclaredMethod("setConfiguration", ipConfiguration.getClass());
            //保存静态ip设置
            saveIpSettings(context, address, mask, gate, dns);
            //设置静态IP
            setConfigurationMethod.invoke(ethManager, ipConfiguration);
            setEthernetEnabled.invoke(ethManager,true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取StaticIpConfiguration实例
     */
    private static Object newStaticIpConfiguration(String address, String gate, String mask, String dns) throws Exception {
        Class<?> staticIpConfigurationCls = Class.forName("android.net.StaticIpConfiguration");
        //实例化StaticIpConfiguration
        Object staticIpConfiguration = staticIpConfigurationCls.newInstance();
        Field ipAddress = staticIpConfigurationCls.getField("ipAddress");
        Field gateway = staticIpConfigurationCls.getField("gateway");
        Field domains = staticIpConfigurationCls.getField("domains");
        Field dnsServers = staticIpConfigurationCls.getField("dnsServers");
        //设置ipAddress
        ipAddress.set(staticIpConfiguration, newLinkAddress(address, mask));
        //设置网关
        gateway.set(staticIpConfiguration, InetAddress.getByName(gate));
        //设置掩码
        domains.set(staticIpConfiguration, mask);
        //设置dns
        try {
            ArrayList<InetAddress> dnsList = (ArrayList<InetAddress>) dnsServers.get(staticIpConfiguration);
            String[] ipStr = dns.split("\\.");
            byte[] ipBuf = new byte[4];
            for (int i = 0; i < 4; i++) {
                ipBuf[i] = (byte) (Integer.parseInt(ipStr[i]) & 0xff);
            }
            InetAddress dnsServer = InetAddress.getByAddress(ipBuf);
            dnsList.add(dnsServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staticIpConfiguration;
    }

    /**
     * 获取LinkAddress实例
     */
    private static Object newLinkAddress(String address, String mask) throws Exception {
        Class<?> linkAddressCls = Class.forName("android.net.LinkAddress");
        Constructor<?> linkAddressConstructor = linkAddressCls.getDeclaredConstructor(InetAddress.class, int.class);
        return linkAddressConstructor.newInstance(InetAddress.getByName(address), getPrefixLength(mask));
    }

    /**
     * 获取IpConfiguration实例
     */
    private static Object newIpConfiguration(Object staticIpConfiguration) throws Exception {
        Class<?> ipConfigurationCls = Class.forName("android.net.IpConfiguration");
        Object ipConfiguration = ipConfigurationCls.newInstance();
        //设置StaticIpConfiguration
        Field staticIpConfigurationField = ipConfigurationCls.getField("staticIpConfiguration");
        staticIpConfigurationField.set(ipConfiguration, staticIpConfiguration);
        //获取ipAssignment、proxySettings的枚举值
        Map<String, Object> ipConfigurationEnum = getIpConfigurationEnum(ipConfigurationCls);
        //设置ipAssignment
        Field ipAssignment = ipConfigurationCls.getField("ipAssignment");
        ipAssignment.set(ipConfiguration, ipConfigurationEnum.get("IpAssignment.STATIC"));
        //设置proxySettings
        Field proxySettings = ipConfigurationCls.getField("proxySettings");
        proxySettings.set(ipConfiguration, ipConfigurationEnum.get("ProxySettings.STATIC"));
        return ipConfiguration;
    }

    /**
     * 获取IpConfiguration的枚举值
     */
    private static Map<String, Object> getIpConfigurationEnum(Class<?> ipConfigurationCls) {
        Map<String, Object> enumMap = new HashMap<>();
        Class<?>[] enumClass = ipConfigurationCls.getDeclaredClasses();
        for (Class<?> enumC : enumClass) {
            Object[] enumConstants = enumC.getEnumConstants();
            if (enumConstants == null) continue;
            for (Object enu : enumConstants) {
                enumMap.put(enumC.getSimpleName() + "." + enu.toString(), enu);
            }
        }
        return enumMap;
    }

    /**
     * 保存静态ip设置
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void saveIpSettings(Context context, String address, String mask, String gate, String dns) {
        ContentResolver contentResolver = context.getContentResolver();
        Settings.System.putString(contentResolver, "ethernet_static_ip", address);
        Settings.System.putString(contentResolver, "ethernet_static_mask", mask);
        Settings.Global.putString(contentResolver,"ethernet_static_mask",mask);
        Settings.System.putString(contentResolver, "ethernet_static_gateway", gate);
        Settings.System.putString(contentResolver, "ethernet_static_dns1", dns);
    }
    /*
    private static void saveIp(Context mcontext,String ipAddr){
        ContentResolver contentResolver = mcontext.getContentResolver();
        String ipAddr = mIpAddressView.getText().toString();
        String gateway = mIPgateway.getText().toString();
        String netMask =ipnetmask.getText().toString();
        String dns1 = mdns1.getText().toString();
        String dns2 = mdns2.getText().toString();
        int network_prefix_length = 24;// Integer.parseInt(ipnetmask.getText().toString());
        if (!TextUtils.isEmpty(ipAddr)) { // not empty
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_IP, ipAddr);
        } else {
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_IP, null);
        }
        if (!TextUtils.isEmpty(gateway)) { // not empty
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_GATEWAY,
                    gateway);
        } else {
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_GATEWAY,
                    null);
        }
        if (!TextUtils.isEmpty(netMask)) { // not empty
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_NETMASK,
                    netMask);
        } else {
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_NETMASK,
                    null);
        }
        if (!TextUtils.isEmpty(dns1)) { // not empty
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_DNS1, dns1);
        } else {
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_DNS1, null);
        }
        if (!TextUtils.isEmpty(dns2)) { // not empty
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_DNS2, dns2);
        } else {
            Settings.System.putString(contentResolver, Settings.System.ETHERNET_STATIC_DNS2, null);
        }
    }
*/
    /**
     * 获取长度
     */
    private static int getPrefixLength(String mask) {
        String[] strs = mask.split("\\.");
        int count = 0;
        for (String str : strs) {
            if (str.equals("255")) {
                ++count;
            }
        }
        return count * 8;
    }
}
