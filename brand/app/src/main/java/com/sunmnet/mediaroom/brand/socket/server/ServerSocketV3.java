package com.sunmnet.mediaroom.brand.socket.server;

import com.sunmnet.mediaroom.socket.core.protocol.codec.SimpleProtocolCodecFactory;
import com.sunmnet.mediaroom.socket.core.protocol.codec.TextDecoder;
import com.sunmnet.mediaroom.socket.core.protocol.codec.TextEncoder;
import com.sunmnet.mediaroom.socket.core.server.AbstractSocketServer;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

/**
 * 智校3.0平台
 */
public class ServerSocketV3 extends AbstractSocketServer {


    public ServerSocketV3(int port) {
        super();
        this.port = port;
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
