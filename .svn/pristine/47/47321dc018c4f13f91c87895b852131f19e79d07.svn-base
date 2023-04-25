package com.sunmnet.mediaroom.brand.socket;

import com.sunmnet.mediaroom.brand.socket.protocol.v4.BrandProtocolHandlerFactoryV4;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.impl.AbstractDebugServerService;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;

public class BrandDebugServerService extends AbstractDebugServerService {

    public BrandDebugServerService() {
    }

    public BrandDebugServerService(int port) {
        super(port);
    }

    @Override
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return new BrandProtocolHandlerFactoryV4();
    }
}
