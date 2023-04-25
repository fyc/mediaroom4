package com.sunmnet.mediaroom.brand.socket;

import com.sunmnet.mediaroom.brand.socket.protocol.v4.BrandProtocolHandlerFactoryV4;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.impl.AbstractServerService;
import com.sunmnet.mediaroom.common.impl.DefaultSocketSender;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketServer;
import com.sunmnet.mediaroom.socket.core.server.SimpleSocketServer;

import org.apache.mina.core.session.IoSession;

import java.util.List;

public class BrandServerService extends AbstractServerService {

    public BrandServerService() {
    }

    public BrandServerService(int port) {
        super(port);
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
        return new BrandProtocolHandlerFactoryV4();
    }

    @Override
    protected void logSend(String msg) {
        RunningLog.commu("班牌", "平台", msg);
    }

    @Override
    protected void logReceive(String msg) {
        RunningLog.commu("平台", "班牌", msg);
    }
}
