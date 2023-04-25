package com.sunmnet.mediaroom.brand.socket;

import com.sunmnet.mediaroom.brand.socket.protocol.v4.BrandProtocolHandlerFactoryV4;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.device.version4.CoreSocketClientService;

public class BrandCoreClientService extends CoreSocketClientService {

    @Override
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return new BrandProtocolHandlerFactoryV4();
    }
}
