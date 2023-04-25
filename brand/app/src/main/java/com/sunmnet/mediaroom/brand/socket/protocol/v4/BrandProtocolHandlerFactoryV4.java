package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import com.sunmnet.mediaroom.common.socket.protocol.version4.ProtocolHandlerFactoryV4;

public class BrandProtocolHandlerFactoryV4 extends ProtocolHandlerFactoryV4 {

    public BrandProtocolHandlerFactoryV4() {
        super();
        initProtocol(new RuleReceiveProtocol());
        initProtocol(new VersionProtocol());
        initProtocol(new RegisterProtocol());
        initProtocol(new CourseUpdateProtocol());
        initProtocol(new TimeSwitchProtocol());
        initProtocol(new UpgradeProtocol());
        initProtocol(new CustomConfigProtocol());
        initProtocol(new FaceSyncProtocol());
        initProtocol(new FaceAuthProtocol());
        initProtocol(new DebugProtocol());
        initProtocol(new ShutdownProtocol());
    }
}
