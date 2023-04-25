package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.socket.server.DebugSocketServer;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketServer;

import org.apache.mina.core.session.IoSession;

import java.util.List;

public abstract class AbstractDebugServerService extends AbstractServerService {

    public AbstractDebugServerService() {
    }

    public AbstractDebugServerService(int port) {
        super(port);
    }

    @Override
    protected SocketServer initSocketServer() {
        return new DebugSocketServer();
    }

    @Override
    protected SocketSender initSocketSender(List<IoSession> list) {
        return new DebugSocketSender(list);
    }

    @Override
    protected void logSend(String msg) {
        RunningLog.commu("DEBUG服务端", "DEBUG客户端", msg);
    }

    @Override
    protected void logReceive(String msg) {
        RunningLog.commu("DEBUG客户端", "DEBUG服务端", msg);
    }
}
