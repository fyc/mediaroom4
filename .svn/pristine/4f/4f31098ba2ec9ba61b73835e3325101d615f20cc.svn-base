package com.sunmnet.mediaroom.tabsp.record.impl.hik;

public class HcnetSDKError {
    public static String getError(int errorCode){
        String errorMsg="[错误码:" + errorCode + "]";
        switch (errorCode){
            case 1:
                errorMsg="[错误提示:连接设备失败。设备不在线或者IP地址无法连接或网络原因引起的连接超时]";
                break;
            case 2:
                errorMsg="[权限不足。一般和通道相关，例如有预览通道1权限，无预览通道2权限，即有预览权限但不完全，预览通道2返回此错误。]";
                break;
            case 3:
                errorMsg="[SDK未初始化。]";
                break;
            case 4:
                errorMsg="[通道号错误。设备没有对应的通道号。]";
                break;
            case 5:
                errorMsg="[设备总的连接数超过最大。]";
                break;
            case 6:
                errorMsg="[版本不匹配。SDK和设备的版本不匹配。]";
                break;
            case 7:
                errorMsg="[错误提示:连接设备失败。设备不在线或者IP地址无法连接或网络原因引起的连接超时]";
                break;
        }
        return errorMsg;
    }
}
