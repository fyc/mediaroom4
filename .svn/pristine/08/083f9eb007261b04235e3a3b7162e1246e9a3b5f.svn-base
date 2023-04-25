package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.face.SyncFaceInfo;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.network.EasyCallback;
import com.sunmnet.mediaroom.brand.common.network.EasyOkHttp;
import com.sunmnet.mediaroom.brand.utils.FaceSyncOperateHelper;
import com.sunmnet.mediaroom.brand.utils.UrlUtil;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;

import java.util.UUID;


public class FaceSyncProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.info("更新人脸信息: " + message);
        ToastUtil.show(DeviceApp.getInstance(), "收到更新人脸信息指令");
        if (!DeviceApp.getApp().isRegistered()) {
            RunningLog.run("班牌未注册，不处理更新人脸信息");
            ToastUtil.show(DeviceApp.getInstance(), "班牌未注册，不处理更新人脸信息");
            return SocketResult.fail();
        }
        if (message == null || TextUtils.isEmpty(message.getMsg()))
            return SocketResult.fail();
        String url = UrlUtil.getFullHttpUrl(DeviceApp.getApp().getPlServerAddr(), message.getMsg());
        RunningLog.debug("人脸信息请求url: " + url);
        RunningLog.run("获取人脸信息");
        ToastUtil.show(DeviceApp.getInstance(), "获取人脸信息");
        EasyOkHttp easyOkHttp = EasyOkHttp.create(DeviceApp.getApp());
        easyOkHttp.url(url).callback(new EasyCallback() {
            @Override
            protected void onSuccess(String result) {
                SyncFaceInfo info = JacksonUtil.jsonStrToBean(result, SyncFaceInfo.class);
                if (info == null) {
                    RunningLog.error("获取人脸信息错误：" + result);
                    ToastUtil.show(DeviceApp.getInstance(), "获取人脸信息错误：" + result);
                    return;
                }
                FaceSyncOperateHelper operateHelper = new FaceSyncOperateHelper();
                //已同步人脸信息的修改时间
                long remoteModify = operateHelper.getRemoteModify();
                //正在使用的人脸信息的修改时间
                long currentModify = operateHelper.getCurrentModify();
                long modify = info.getModifyTime();
                SyncFaceInfo useSyncInfo;
                if (modify > remoteModify) {
                    //如果之前同步的人脸信息修改时间比较早
                    RunningLog.run("人脸信息有更新，准备更新人脸信息");
                    ToastUtil.show(DeviceApp.getInstance(), "人脸信息有更新，准备更新人脸信息");
                    operateHelper.saveFaceSyncInfo(message.getUuid(), result, modify);
                    useSyncInfo = info;
                } else if (remoteModify > currentModify) {
                    //之前同步人脸信息较新或一样，但正在使用的人脸信息较旧，则使用目前的进行更新
                    RunningLog.run("本地人脸信息还未完成更新，继续更新人脸信息");
                    ToastUtil.show(DeviceApp.getInstance(), "本地人脸信息还未完成更新，继续更新人脸信息");
                    useSyncInfo = operateHelper.getFaceSyncInfo();
                    if (useSyncInfo == null)
                        return;
                } else {
                    RunningLog.run("人脸信息无改动，不进行更新");
                    ToastUtil.show(DeviceApp.getInstance(), "人脸信息无改动，不进行更新");
                    return;
                }
                operateHelper.syncFace(useSyncInfo);
            }

            @Override
            protected void onFail(String errorMsg) {
                RunningLog.error("获取人脸信息错误：" + errorMsg);
                ToastUtil.show(DeviceApp.getInstance(), "获取人脸信息错误：" + errorMsg);
            }
        }).get();
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_SYNC_FACE;
    }

}
