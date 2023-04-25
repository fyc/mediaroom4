package com.sunmnet.mediaroom.brand.socket;

import com.sunmnet.mediaroom.common.enums.SocketServiceType;
import com.sunmnet.mediaroom.common.impl.LogServerService;
import com.sunmnet.mediaroom.common.interfaces.ISocketServiceFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketService;

public class BrandSocketServiceFactory implements ISocketServiceFactory {

    @Override
    public SocketService getService(String key) {
        if (SocketServiceType.LOCAL.getKey().equals(key)) {
            return new BrandServerService(6201);
        } else if (SocketServiceType.LOG.getKey().equals(key)) {
            return new LogServerService(6301);
        } else if (SocketServiceType.CORE.getKey().equals(key)) {
            return new BrandCoreClientService();
        } else if (SocketServiceType.DEBUG.getKey().equals(key)) {
            return new BrandDebugServerService(6302);
        }
        return null;
    }
}
