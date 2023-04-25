package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.interfaces.DownloadListener;
import com.sunmnet.mediaroom.brand.common.network.DownloadManager;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.brand.bean.config.CustomConfig;
import com.sunmnet.mediaroom.brand.bean.event.RefreshConfigEvent;
import com.sunmnet.mediaroom.brand.bean.message.CustomConfigMessage;
import com.sunmnet.mediaroom.brand.data.file.FileConstant;
import com.sunmnet.mediaroom.brand.data.sharepref.BrandConfig;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.brand.utils.UnZipUtil;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.FileUtils;
import com.sunmnet.mediaroom.util.MD5Util;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;


public class CustomConfigProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public String getTagName() {
        return CommuTag.BRAND_CONFIG;
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.run("更新班牌自定义配置: " + message);
        if (message != null && !TextUtils.isEmpty(message.getMsg())) {
            CustomConfigMessage configMessage = JacksonUtil.jsonStrToBean(message.getMsg(), CustomConfigMessage.class);
            if (configMessage == null || TextUtils.isEmpty(configMessage.getUrl())) {
                return SocketResult.fail();
            }

            String url = UrlUtil.getFullHttpUrl(DeviceApp.getApp().getPlServerAddr(), configMessage.getUrl());
            RunningLog.run("下载班牌自定义配置文件，URL：" + url);
            DownloadManager.getInstance().add(url, FileResourceUtil.getTempFolderPath(), "config.zip", false, new DownloadListener() {
                @Override
                public void onFinished() {
                    RunningLog.run("自定义配置文件下载完成");
                    File file = new File(FileResourceUtil.getTempFolderPath(), "config.zip");
                    unZipFile(file, configMessage.getMd5());
                }

                @Override
                public void onProgress(float progress) {

                }

                @Override
                public void onPause() {

                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onFail() {
                    RunningLog.run("下载班牌自定义配置文件失败");
                }
            });
            DownloadManager.getInstance().download(url);
        }
        return SocketResult.success();
    }

    private void unZipFile(File file, String md5) {
        if (file == null) {
            return;
        }
        try {
            RunningLog.run("开始解压配置文件");
            String fileMD5 = MD5Util.getFileMD5String(file);
            if (md5 != null && TextUtils.equals(fileMD5.toLowerCase(), md5.toLowerCase())) {
                //开始解压
                UnZipUtil.upZipFile(file, FileResourceUtil.getConfigFolderPath());
                RunningLog.run("配置文件下载解压完毕");
                File configFile = new File(FileResourceUtil.getCustomConfigFolderPath(), FileConstant.BRAND_CUSTOM_CONFIG_FILE_NAME);
                if (configFile.exists()) {
                    String s = FileUtils.readFile(configFile, "UTF-8").toString();
                    CustomConfig customConfig = JacksonUtil.jsonStrToBean(s, CustomConfig.class);
                    String realPath = new File(FileResourceUtil.getCustomConfigFolderPath(), customConfig.getHomeScreen()).getPath();
                    customConfig.setHomeScreen(realPath);
                    BrandConfig.getInstance().saveCustomConfig(DeviceApp.getApp(), customConfig);
                }
                EventBus.getDefault().post(new RefreshConfigEvent());
            } else {
                RunningLog.error("下载文件MD5校验出错：" + file.getName() + ",文件MD5：" + fileMD5 + ",");
            }
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
            RunningLog.error(e);
        }
    }

}
