package com.sunmnet.mediaroom.common.socket.protocol.version4;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProtocolHandlerFactoryV4 implements ProtocolHandlerFactory<SocketMessage> {

    private Map<String, ProtocolHandler<SocketMessage, ?>> handlers = new HashMap<>();

    public ProtocolHandlerFactoryV4() {
        init();
    }

    protected void init() {
        initProtocol(new RebootProtocol());
        initProtocol(new LogFileProtocol());
        initProtocol(new CaptureProtocol());
        initProtocol(new ShellProtocol());
    }

    @Override
    public void initProtocol(List<ProtocolHandler<SocketMessage, ?>> protocols) {
        for (ProtocolHandler<SocketMessage, ?> protocol : protocols) {
            handlers.put(protocol.getTagName(), protocol);
        }
    }

    @Override
    public void initProtocol(Map<String, ProtocolHandler<SocketMessage, ?>> protocols) {
        handlers.putAll(protocols);
    }

    @Override
    public void initProtocol(ProtocolHandler<SocketMessage, ?> protocol) {
        handlers.put(protocol.getTagName(), protocol);
    }


    @Override
    public ProtocolHandler<SocketMessage, ?> getProtocolHandler(String tagName) {
        return handlers.get(tagName);
    }
}
