package com.sunmnet.mediaroom.common.tools;

import android.support.annotation.Nullable;

import com.sunmnet.mediaroom.common.interfaces.SocketService;
import com.sunmnet.mediaroom.common.interfaces.ISocketServiceFactory;

import java.util.HashMap;

/**
 * SocketService享元工厂
 */
public class SocketServiceFactory implements ISocketServiceFactory {

    private static SocketServiceFactory instance;

    private ISocketServiceFactory factory;

    private HashMap<String, SocketService> map = new HashMap<>();

    private SocketServiceFactory() {
    }

    public static SocketServiceFactory getInstance() {
        if (instance == null) {
            instance = new SocketServiceFactory();
        }
        return instance;
    }

    public void initFactory(ISocketServiceFactory factory) {
        this.factory = factory;
    }

    @Override
    @Nullable
    public SocketService getService(String key) {
        SocketService socketService = map.get(key);
        if (socketService != null)
            return socketService;
        if (factory != null) {
            SocketService service = factory.getService(key);
            map.put(key, service);
            return service;
        }
        return null;
    }

    public void startService(String key) {
        SocketService service = getService(key);
        if (service != null) {
            service.start();
        }
    }
}
