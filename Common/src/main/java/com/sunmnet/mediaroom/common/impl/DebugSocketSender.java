package com.sunmnet.mediaroom.common.impl;

import org.apache.mina.core.session.IoSession;

import java.util.List;

public class DebugSocketSender extends AbstractSocketSender {

    public DebugSocketSender() {
    }

    public DebugSocketSender(List<IoSession> sessionList) {
        super(sessionList);
    }

    @Override
    public void sendMessage(IoSession session, Object msg) {
        session.write(msg);
    }

}
