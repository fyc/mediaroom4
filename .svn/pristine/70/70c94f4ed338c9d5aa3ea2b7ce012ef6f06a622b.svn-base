package com.sunmnet.mediaroom.common.impl;

import com.litesuits.common.assist.SilentInstaller;
import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.VerifySign;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class DownloadAndApkInstallListener extends DefaultDownloadListener {
    @Override
    public void onDownloadSuccess(File file) {
        /*EventBus.getDefault().post("文件下载成功,保存路径：" + file.getAbsolutePath());
        EventBus.getDefault().post("开始安装apk....");*/
        VerifySign sign=new VerifySign(BaseApplication.getInstance(),file);
        Event<String, EventType> event=new Event<>();
        if (sign.check()){

            SilentInstaller installer=new SilentInstaller();
            int installResult=installer.installSilent(BaseApplication.getInstance(),file.getAbsolutePath());
            if (installResult==SilentInstaller.INSTALL_SUCCEEDED){
                event.setEventType(EventType.UPGRADE_SUCCESS);
                event.setMessage("升级成功!!");
            }else{
                event.setEventType(EventType.UPGRADE_FAILED);
                event.setMessage("安装失败!!错误码："+installResult);
            }
        }else{
            event.setEventType(EventType.UPGRADE_FAILED);
            event.setMessage("文件签名异常，请从正规渠道获取安装文件!!");
        }

        EventBus.getDefault().post(event);
        file.delete();
    }
}
