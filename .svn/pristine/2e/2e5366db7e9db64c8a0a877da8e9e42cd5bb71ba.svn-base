package com.sunmnet.mediaroom.tabsp.socket;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.impl.AbstractDebugServerService;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.tabsp.socket.protocol.TabspProtocolHandlerFactory;

public class TabspDebugServerService extends AbstractDebugServerService {

    public TabspDebugServerService() {
    }

    public TabspDebugServerService(int port) {
        super(port);
    }

    @Override
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return new TabspProtocolHandlerFactory();
    }
}
