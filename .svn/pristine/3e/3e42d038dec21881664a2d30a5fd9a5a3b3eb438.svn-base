package com.sunmnet.mediaroom.brand.utils;


import com.sunmnet.mediaroom.brand.socket.protocol.v4.BrandProtocolHandlerFactoryV4;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.socket.protocol.version3.ProtocolHandlerFactoryV3;

public class ProtocolManager {

    private static ProtocolHandlerFactory v3;
    private static ProtocolHandlerFactory v4;

    public static ProtocolHandlerFactory<String> getFactoryV3() {
        if (v3 == null) {
            v3 = new ProtocolHandlerFactoryV3();
        }
        return v3;
    }

    public static ProtocolHandlerFactory<SocketMessage> getFactoryV4() {
        if (v4 == null) {
            v4 = new BrandProtocolHandlerFactoryV4();
        }
        return v4;
    }

}
