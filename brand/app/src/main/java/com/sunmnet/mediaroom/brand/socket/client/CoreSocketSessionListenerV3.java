package com.sunmnet.mediaroom.brand.socket.client;

import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.brand.utils.ProtocolManager;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.protocol.codec.AbstractSocketSessionListener;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.session.IoSession;

public class CoreSocketSessionListenerV3 extends AbstractSocketSessionListener {

    @Override
    public void onMessageReceived(IoSession session, String message) {
        RunningLog.commu("核心", "本机", message);
        String tagName = message.substring(0, 2);
        ProtocolHandler protocol = ProtocolManager.getFactoryV3().getProtocolHandler(tagName);
        if (protocol != null) {
            message = message.substring(tagName.length());
            protocol.handle(session, message);
        }
    }

    @Override
    public void onMessageReceived(IoSession session, ByteArrayWrapper data) {

    }

    @Override
    public void onMessageReceived(IoSession session, String fileName, ByteArrayWrapper fileData) {

    }

    @Override
    public void onSessionCreated(IoSession session) {

    }

    @Override
    public void onSessionOpened(IoSession session) {

    }

    @Override
    public void onSessionClosed(IoSession session) {

    }

    @Override
    public void onMessageSent(IoSession session, Object message) {

    }

    @Override
    public void onSessionExceptionCaught(IoSession session, Throwable throwable) {

    }
}
