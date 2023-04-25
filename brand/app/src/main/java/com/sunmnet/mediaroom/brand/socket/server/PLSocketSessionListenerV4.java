package com.sunmnet.mediaroom.brand.socket.server;

import com.sunmnet.mediaroom.common.bean.KafkaMessage;
import com.sunmnet.mediaroom.common.bean.KafkaMessageResponse;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.brand.utils.ProtocolManager;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.socket.core.protocol.codec.AbstractSocketSessionListener;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class PLSocketSessionListenerV4 extends AbstractSocketSessionListener {

    @Override
    public void onMessageReceived(IoSession ioSession, String message) {
        RunningLog.commu("平台", "本机", message);
        KafkaMessage socketMessage = JacksonUtil.jsonStrToBean(message, KafkaMessage.class);
        if (socketMessage != null) {
            ProtocolHandler<SocketMessage, ?> protocol = ProtocolManager.getFactoryV4().getProtocolHandler(socketMessage.getKey());
            if (protocol != null) {
                SocketResult result = protocol.handle(ioSession, socketMessage);
                if (result != null) {
                    if (result.isResponse()) {
                        KafkaMessageResponse response = new KafkaMessageResponse(socketMessage);
                        response.setStatus(result.isSuccess() ? 0 : 1);
                        if (result.getMsg() instanceof String) {
                            response.setMsg((String) result.getMsg());
                        } else {
                            response.setMsg(JacksonUtil.objToJsonStr(result.getMsg()));
                        }
                        response.setErrorMsg(result.getErrorMsg());
                        ioSession.write(JacksonUtil.objToJsonStr(response));
                    }
                }
            } else {
                RunningLog.run("无法识别的消息类型");
            }
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
