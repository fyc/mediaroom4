package com.sunmnet.mediaroom.tabsp.socket;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.device.version4.CoreSocketClientService;
import com.sunmnet.mediaroom.tabsp.socket.protocol.TabspProtocolHandlerFactory;

public class TabspCoreClientService extends CoreSocketClientService {

    @Override
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return new TabspProtocolHandlerFactory();
    }
}
