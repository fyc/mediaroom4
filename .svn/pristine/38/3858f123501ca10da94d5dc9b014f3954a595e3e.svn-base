package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.content.pm.PackageInfo;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.UpgradeInfo;
import com.sunmnet.mediaroom.brand.bean.message.UpgradeMessage;
import com.sunmnet.mediaroom.brand.bean.request.UpgradeResultRequest;
import com.sunmnet.mediaroom.brand.bean.response.ObjectResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.interfaces.DownloadListener;
import com.sunmnet.mediaroom.brand.common.network.DownloadManager;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.brand.data.sharepref.UpgradePref;
import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.FileResourceUtil;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.MD5Util;

import org.apache.mina.core.session.IoSession;

import java.io.File;

import io.reactivex.Observable;


public class UpgradeProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public String getTagName() {
        return CommuTag.SYS_UPGRATE;
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.info("接收到升级apk信息:" + message);
        ToastUtil.show(DeviceApp.getInstance(), "接收到升级apk消息");
        if (message == null || TextUtils.isEmpty(message.getMsg()))
            return SocketResult.fail();
        UpgradeMessage upgradeMessage = JacksonUtil.jsonStrToBean(message.getMsg(), UpgradeMessage.class);
        if (upgradeMessage == null || upgradeMessage.getRequestAction() == null || upgradeMessage.getMd5() == null) {
            RunningLog.info("升级数据格式出錯");
            ToastUtil.show(DeviceApp.getInstance(), "升级数据格式出錯");
            return SocketResult.fail();
        }
        String upgradeUrl = UrlUtil.getFullHttpUrl(DeviceApp.getApp().getPlServerAddr(), upgradeMessage.getRequestAction());
        String md5 = upgradeMessage.getMd5();//MD5校验:下载的apk文件生成的Md5和md5相等时，才安装
        final String fileName = "temp.apk";

        DownloadManager downloadManager = DownloadManager.getInstance();
        downloadManager.add(upgradeUrl, FileResourceUtil.getTempFolderPath(), fileName, false,
                new DownloadListener() {
                    @Override
                    public void onFinished() {
                        File file = new File(FileResourceUtil.getTempFolderPath(), fileName);
                        if (!file.exists()) {
                            RunningLog.error("APK安装包写入本地出错：" + fileName);
                            ToastUtil.show(DeviceApp.getInstance(), "APK安装包写入本地出错：" + fileName);
                            return;
                        }
                        RunningLog.run("APK安装包下载完成");
                        ToastUtil.show(DeviceApp.getInstance(), "APK安装包下载完成");
                        checkFileMd5(file, md5, message.getUuid());
                    }

                    @Override
                    public void onProgress(float progress) {
                    }

                    @Override
                    public void onPause() {
                    }

                    @Override
                    public void onCancel() {
                        RunningLog.info("APK安装包下载取消：" + fileName);
                        ToastUtil.show(DeviceApp.getInstance(), "APK安装包下载取消：" + fileName);
                    }

                    @Override
                    public void onFail() {
                        RunningLog.error("APK安装包下载失败：" + fileName);
                        ToastUtil.show(DeviceApp.getInstance(), "APK安装包下载失败：" + fileName);
                    }
                });
        downloadManager.download(upgradeUrl);
        return null;
    }

    private void checkFileMd5(final File file, final String md5, String id) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    String fileMD5 = MD5Util.getFileMD5String(file);
                    if (fileMD5 != null && md5 != null && TextUtils.equals(fileMD5.toLowerCase(), md5.toLowerCase())) {
                        installApk(file, id);
                    } else {
                        RunningLog.error("下载文件MD5校验出错：" + file.getName());
                        ToastUtil.show(DeviceApp.getInstance(), "下载文件MD5校验出错：" + file.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void installApk(File file, String upgradeId) {
        //升级电子班牌Apk
        UpgradeInfo upgradeInfo = UpgradePref.getInstance().getUpgradeInfo(DeviceApp.getApp());
        if (upgradeInfo == null) {
            upgradeInfo = new UpgradeInfo();
        }
        upgradeInfo.setUpgradeSuccess(false);
        upgradeInfo.setRespondedResult(false);
        PackageInfo packageInfo = AppUtil.getPackageInfoByContext(DeviceApp.getApp());
        if (packageInfo != null) {
            upgradeInfo.setLastVersionCode(packageInfo.versionCode);
            upgradeInfo.setLastVersionName(packageInfo.versionName);
        }
        upgradeInfo.setUpgradeId(upgradeId);
        UpgradePref.getInstance().saveUpgradeInfo(DeviceApp.getApp(), upgradeInfo);
        RunningLog.run("开始升级, 文件路径:" + file.getAbsolutePath());
        ToastUtil.show(DeviceApp.getInstance(), "开始执行升级命令");
        boolean success = BrandDevice.getInstance().installApk(file.getAbsolutePath());
        if (!success) {
            RunningLog.run("APK安装包安装失败");
            ToastUtil.show(DeviceApp.getInstance(), "APK安装包安装失败");
            if (DeviceApp.getApp().isRegistered()) {
                RunningLog.run("发送升级结果给平台");
                UpgradeResultRequest request = new UpgradeResultRequest();
                request.setClassroomCode(DeviceApp.getApp().getClassroomCode());
                if (upgradeInfo.isUpgradeSuccess()) {
                    request.setResult("00");
                } else {
                    request.setResult("01");
                }
                request.setUuid(upgradeInfo.getUpgradeId());
                if (packageInfo != null) {
                    request.setVersion(packageInfo.versionName);
                }
                Observable<ObjectResponse> observable = ApiManager.getSysApi().respondUpgradeResult(request);
                observable.subscribe(new SingleTaskObserver<ObjectResponse>() {
                    @Override
                    public void onNext(ObjectResponse response) {
                        if (response == null) {
                            RunningLog.run("发送升级结果返回数据解析错误");
                            return;
                        }
                        RunningLog.run("发送升级结果:" + response.getSuccess() + "  " + response.getMsg());
                        if (response.isSuccess()) {
                            UpgradeInfo upgradeInfo1 = UpgradePref.getInstance().getUpgradeInfo(DeviceApp.getApp());
                            upgradeInfo1.setRespondedResult(true);
                            UpgradePref.getInstance().saveUpgradeInfo(DeviceApp.getApp(), upgradeInfo1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RunningLog.run("发送升级结果失败: " + e.getMessage());
                    }
                });
            }
        } else {
            RunningLog.run("升级命令执行成功");
            ToastUtil.show(DeviceApp.getInstance(), "升级命令执行成功");
        }
    }
}
