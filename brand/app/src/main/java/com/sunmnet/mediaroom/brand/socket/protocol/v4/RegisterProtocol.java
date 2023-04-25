package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.event.RefreshControlEvent;
import com.sunmnet.mediaroom.brand.bean.message.RegisterMessage;
import com.sunmnet.mediaroom.brand.bean.response.LongResponse;
import com.sunmnet.mediaroom.brand.bean.response.StringResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.brand.utils.FaceHelper;
import com.sunmnet.mediaroom.brand.utils.SyncTimeHelper;
import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.config.DeviceConfig;
import com.sunmnet.mediaroom.common.enums.TabspVersion;
import com.sunmnet.mediaroom.common.enums.TimeEventType;
import com.sunmnet.mediaroom.common.events.TimeEvent;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;


public class RegisterProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        /**
         * {"plIp":"192.168.21.120","plPort":80,"coreIp":"1.1.1.1","corePort":6003,"classroomCode":"T1-
         0102","classroomName":"T1-0102"}
         */
        RunningLog.debug("接收到注册班牌信息:" + message);
        if (message == null || TextUtils.isEmpty(message.getMsg())) {
            return SocketResult.fail();
        }
        RegisterMessage registerMessage = JacksonUtil.jsonStrToBean(message.getMsg(), RegisterMessage.class);
        if (registerMessage == null) {
            return SocketResult.fail();
        }
        RunningLog.run("正在注册班牌");
        DeviceInfo device = DeviceApp.getApp().getDevice();
        device.setClassroomCode(registerMessage.getClassroomCode());
        device.setClassroomName(registerMessage.getClassroomName());
        device.setCoreIp(registerMessage.getCoreIp());
        device.setCorePort(registerMessage.getCorePort());
        device.setPlatformIp(registerMessage.getPlatformIp());
        device.setPlatformPort(registerMessage.getPlatformPort());
        device.setDeviceCode(registerMessage.getDeviceCode());
        DeviceConfig.getInstance().saveDeviceInfo(DeviceApp.getApp(), device);
        RunningLog.run("注册完毕");
        ToastUtil.show(DeviceApp.getApp(), "班牌注册完毕");
        syncTime();
        if (!TextUtils.isEmpty(registerMessage.getCoreIp())) {
            Controller.init(DeviceApp.getApp()).setControllCenterInfo(TabspVersion.VERSION_4, registerMessage.getCoreIp(), registerMessage.getCorePort(), TerminalMessage.BRAND);
        }

        String base64 = FaceHelper.getInstance().getHostInfoBase64();
        if (!TextUtils.isEmpty(base64)) {
            HashMap<String, String> body = new HashMap<>();
            body.put("deviceCode", device.getDeviceCode());
            body.put("serialCode", base64);
            ApiManager.getDeviceApi().uploadSerialCode(body)
                    .subscribe(new SingleTaskObserver<StringResponse>() {
                        @Override
                        public void onNext(StringResponse stringResponse) {
                            RunningLog.run("上传人脸机器码成功");
                        }

                        @Override
                        public void onError(Throwable e) {
                            RunningLog.run("上传人脸机器码错误：" + e.getMessage());
                        }
                    });
        }
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_REGISTER;
    }

    private void syncTime() {
        if (DeviceApp.getApp().isRegistered()) {
            RunningLog.run("正在同步平台时间");
            ApiManager.getSysApi().getSystemTime()
                    .subscribe(new SingleTaskObserver<LongResponse>() {
                        @Override
                        public void onNext(LongResponse response) {
                            if (response == null) {
                                RunningLog.debug("获取平台时间数据解析出错：null");
                                return;
                            }
                            if (!response.isSuccess() || response.getObj() == null) {
                                RunningLog.debug("获取平台时间失败，success: " + response.isSuccess() + ".obj:" + response.getObj());
                                return;
                            }
                            Long timeStamp = response.getObj();
                            SyncTimeHelper helper = new SyncTimeHelper();
                            boolean updateResult = false;
                            try {
                                updateResult = helper.syncTime(timeStamp);
                            } catch (Exception e) {
                                e.printStackTrace();
                                RunningLog.error("修改系统时间失败：" + e.getMessage());
                            }
                            if (!updateResult) {
                                //refresh
                                EventBus.getDefault().post(new RefreshControlEvent());
                            } else {
                                //reload
                                EventBus.getDefault().post(new TimeEvent(TimeEventType.RESET));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            RunningLog.debug("获取平台时间失败：" + e.getMessage());
                        }
                    });
        }
    }

}
