package com.sunmnet.mediaroom.common.socket.protocol.version4;

import com.sunmnet.mediaroom.common.bean.DeviceInfo;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.config.DeviceConfig;
import com.sunmnet.mediaroom.common.impl.AbstractCallback;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;

import java.io.File;


public class CaptureProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        DeviceInfo device = DeviceConfig.getInstance().getDeviceInfo();
        if (device != null) {
            AppUtil.capture(device, new CaptureCallback(session));
        }
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.DEBUG_SNAPSHOT_IMEDIALY;
    }

    static class CaptureCallback extends AbstractCallback<String> {

        IoSession ioSession;

        CaptureCallback(IoSession ioSession) {
            this.ioSession = ioSession;
        }

        @Override
        protected void onCallbackSuccess(String s) {
            File file = new File(s);
            if (file.exists()) {
                ioSession.write(file);
            }
        }

        @Override
        public void onFail(String callbackMsg) {
            RunningLog.warn("截图异常....");
        }
    }

}
