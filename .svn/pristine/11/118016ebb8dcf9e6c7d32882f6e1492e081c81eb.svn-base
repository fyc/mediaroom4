package com.sunmnet.mediaroom.common.socket.protocol.version3;

import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProtocolHandlerFactoryV3 implements ProtocolHandlerFactory<String> {

    private Map<String, ProtocolHandler<String, ?>> handlers = new HashMap<>();

    public ProtocolHandlerFactoryV3() {
        initProtocol(new RebootProtocol());
        initProtocol(new LogFileProtocol());
        initProtocol(new LogFileProtocol2());
        initProtocol(new CaptureProtocol());
        initProtocol(new CaptureByteProtocol());
        initProtocol(new ExecuteProtocol());
    }

    public void initProtocol(List<ProtocolHandler<String, ?>> protocols) {
        for (ProtocolHandler protocol : protocols) {
            handlers.put(protocol.getTagName(), protocol);
        }
    }

    public void initProtocol(Map<String, ProtocolHandler<String, ?>> protocols) {
        handlers.putAll(protocols);
    }

    public void initProtocol(ProtocolHandler<String, ?> protocol) {
        handlers.put(protocol.getTagName(), protocol);
    }

    public ProtocolHandler<String, ?> getProtocolHandler(String tagName) {
        return handlers.get(tagName);
    }

}
