package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketClient;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.session.IoSession;

import java.util.List;

public class DefaultSocketSender extends AbstractSocketSender {

    public DefaultSocketSender() {
        super(null);
    }

    public DefaultSocketSender(SocketClient client) {
        super(client.getConnectedSessions());
    }

    public DefaultSocketSender(List<IoSession> sessionList) {
        super(sessionList);
    }

    @Override
    public void sendMessage(IoSession session, Object msg) {
        if (msg instanceof String || msg instanceof ByteArrayWrapper) {
            session.write(msg);
        } else {
            session.write(JacksonUtil.objToJsonStr(msg));
        }
    }
}
