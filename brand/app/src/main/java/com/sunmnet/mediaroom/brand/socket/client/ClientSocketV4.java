package com.sunmnet.mediaroom.brand.socket.client;

import com.sunmnet.mediaroom.socket.core.client.SimpleSocketClient;
import com.sunmnet.mediaroom.socket.core.protocol.codec.SimpleProtocolCodecFactory;
import com.sunmnet.mediaroom.socket.core.protocol.codec.TextDecoder;
import com.sunmnet.mediaroom.socket.core.protocol.codec.TextEncoder;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

/**
 * 智校4.0平台
 */
public class ClientSocketV4 extends SimpleSocketClient {


    public ClientSocketV4() {
        super();
        getSessionConfig().setBothIdleTime(5);
    }

    @Override
    protected KeepAliveFilter getKeepAliveFilter() {
        return null;
    }

    @Override
    protected ProtocolCodecFilter getProtocolCodecFilter() {
        return new ProtocolCodecFilter(new SimpleProtocolCodecFactory(new TextEncoder(), new TextDecoder()));
    }
}
