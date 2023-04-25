package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import com.sunmnet.mediaroom.brand.impl.device.BrandDevice;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class ShutdownProtocol implements ProtocolHandler<SocketMessage, String> {

    public ShutdownProtocol() {
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        BrandDevice.getInstance().shutdown();
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_SHUTDOWN;
    }
}
