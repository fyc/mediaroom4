package com.sunmnet.mediaroom.device.version4.protocol;

import com.sunmnet.mediaroom.common.socket.protocol.version4.ProtocolHandlerFactoryV4;

public class DeviceProtocolHandlerFactory extends ProtocolHandlerFactoryV4 {

    @Override
    protected void init() {
        super.init();
        initProtocol(new ConfigResponseProtocol());
        initProtocol(new DeviceStateProtocol());
        initProtocol(new DeviceVgaChangeProtocol());
        initProtocol(new DeviceVolumeProtocol());
        initProtocol(new SyncConfigProtocol());
    }

}
