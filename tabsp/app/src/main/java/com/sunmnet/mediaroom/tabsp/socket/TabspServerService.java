package com.sunmnet.mediaroom.tabsp.socket;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.impl.AbstractServerService;
import com.sunmnet.mediaroom.common.impl.DefaultSocketSender;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketServer;
import com.sunmnet.mediaroom.socket.core.server.SimpleSocketServer;
import com.sunmnet.mediaroom.tabsp.socket.protocol.TabspProtocolHandlerFactory;

import org.apache.mina.core.session.IoSession;

import java.util.List;

/**
 * 面板主服务，主要处理转发自kafka的消息
 */
public class TabspServerService extends AbstractServerService {

    public TabspServerService(int port) {
        super(port);
    }

    public TabspServerService() {
    }

    @Override
    protected SocketServer initSocketServer() {
        return new SimpleSocketServer();
    }

    @Override
    protected SocketSender initSocketSender(List<IoSession> list) {
        return new DefaultSocketSender(list);
    }

    @Override
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return new TabspProtocolHandlerFactory();
    }

    @Override
    protected void logSend(String msg) {
        RunningLog.commu("面板", "平台", msg);
    }

    @Override
    protected void logReceive(String msg) {
        RunningLog.commu("平台", "面板", msg);
    }
}
