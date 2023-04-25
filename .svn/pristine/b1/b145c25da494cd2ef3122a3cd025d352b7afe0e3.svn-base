package com.sunmnet.mediaroom.common.interfaces;

import java.util.List;
import java.util.Map;

public interface ProtocolHandlerFactory<M> {

    void initProtocol(List<ProtocolHandler<M, ?>> protocols);

    void initProtocol(Map<String, ProtocolHandler<M, ?>> protocols);

    void initProtocol(ProtocolHandler<M, ?> protocol);

    ProtocolHandler<M, ?> getProtocolHandler(String tagName);

}
