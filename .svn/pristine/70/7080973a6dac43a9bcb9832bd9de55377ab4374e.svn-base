package com.sunmnet.mediaroom.brand.socket.server;

import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.brand.utils.ProtocolManager;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.protocol.codec.AbstractSocketSessionListener;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class PLSocketSessionListenerV3 extends AbstractSocketSessionListener {

    @Override
    public void onMessageReceived(IoSession ioSession, String s) {
        RunningLog.commu("平台", "本机", s);
        String tagName = s.substring(0, 2);
        ProtocolHandler protocol = ProtocolManager.getFactoryV3().getProtocolHandler(tagName);
        if (protocol != null) {
            s = s.substring(tagName.length());
            protocol.handle(ioSession, s);
        }
    }

    @Override
    public void onMessageReceived(IoSession ioSession, ByteArrayWrapper byteArrayWrapper) {

    }

    @Override
    public void onMessageReceived(IoSession ioSession, String s, ByteArrayWrapper byteArrayWrapper) {

    }

    @Override
    public void onSessionCreated(IoSession ioSession) {

    }

    @Override
    public void onSessionOpened(IoSession ioSession) {

    }

    @Override
    public void onSessionClosed(IoSession ioSession) {

    }

    @Override
    public void onMessageSent(IoSession ioSession, Object o) {

    }

    @Override
    public void onSessionExceptionCaught(IoSession ioSession, Throwable throwable) {

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        session.closeNow();
    }
}
