package com.sunmnet.mediaroom.brand.socket.client;

import com.sunmnet.mediaroom.brand.utils.ProtocolManager;
import com.sunmnet.mediaroom.common.bean.KafkaMessage;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.protocol.codec.AbstractSocketSessionListener;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.session.IoSession;

public class CoreSocketSessionListenerV4 extends AbstractSocketSessionListener {

    @Override
    public void onMessageReceived(IoSession session, String message) {
        RunningLog.commu("核心", "本机", message);
        KafkaMessage socketMessage = JacksonUtil.jsonStrToBean(message, KafkaMessage.class);
        if (socketMessage != null) {
            ProtocolHandler<SocketMessage, ?> protocol = ProtocolManager.getFactoryV4().getProtocolHandler(socketMessage.getKey());
            if (protocol != null) {
                protocol.handle(session, socketMessage);
            }
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
