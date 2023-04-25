package com.sunmnet.mediaroom.tabsp.record.impl.hik.impl;

import com.sun.jna.Native;

/**
 * Created by dengzl on 2018/6/14.
 */

public enum HCNetSDKJNAInstance {
    CLASS;
    private static HCNetSDKByJNA netSdk = null;
    /**
     * get the instance of HCNetSDK
     * @return the instance of HCNetSDK
     */
    public static HCNetSDKByJNA getInstance()
    {
        if (null == netSdk)
        {
            synchronized (HCNetSDKByJNA.class)
            {
                netSdk = (HCNetSDKByJNA) Native.loadLibrary("hcnetsdk",
                    HCNetSDKByJNA.class);
            }
        }
        return netSdk;
    }
    public static void load(){
        loadLib("hpr");
        loadLib("gnustl_shared");
        loadLib("opensslwrap");
        loadLib("HCCore");
        loadLib("HCCoreDevCfg");
        loadLib("HCPreview");
        loadLib("HCPlayBack");
        loadLib("HCGeneralCfgMgr");
        loadLib("HCVoiceTalk");
        loadLib("HCIndustry");
        loadLib("HCDisplay");
        loadLib("HCAlarm");
        loadLib("SystemTransform");
        loadLib("PlayCtrl");
        loadLib("PlayCtrl_L");
        loadLib("hcnetsdk");
    }
    private static void loadLib(String libName)
    {
        try{
            System.loadLibrary(libName);
        }catch (UnsatisfiedLinkError e){
            e.printStackTrace();
        }
    }
}
