package com.sunmnet.mediaroom.tabsp.socket;

import com.sunmnet.mediaroom.common.enums.SocketServiceType;
import com.sunmnet.mediaroom.common.impl.LogServerService;
import com.sunmnet.mediaroom.common.interfaces.ISocketServiceFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketService;

/**
 * SocketService 工厂
 */
public class TabspSocketServiceFactory implements ISocketServiceFactory {

    @Override
    public SocketService getService(String key) {
        if (SocketServiceType.LOCAL.getKey().equals(key)) {
            return new TabspServerService(6401);
        } else if (SocketServiceType.LOG.getKey().equals(key)) {
            return new LogServerService(6501);
        } else if (SocketServiceType.CORE.getKey().equals(key)) {
            return new TabspCoreClientService();
        } else if (SocketServiceType.DEBUG.getKey().equals(key)) {
            return new TabspDebugServerService(6502);
        }
        return null;
    }
}
