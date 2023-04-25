package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.socket.server.DebugSocketServer;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketServer;

import org.apache.mina.core.session.IoSession;

import java.util.List;

/**
 * 实时日志服务，不处理接收数据,只管发送日志
 */
public class LogServerService extends AbstractServerService {

    public LogServerService(int port) {
        super(port);
    }

    public LogServerService() {
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
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return null;
    }

    @Override
    protected void logSend(String msg) {
    }

    @Override
    protected void logReceive(String msg) {
    }
}
