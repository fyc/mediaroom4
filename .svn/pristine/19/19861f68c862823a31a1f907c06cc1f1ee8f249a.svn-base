package com.sunmnet.mediaroom.tabsp.connector.version4;

import com.sunmnet.mediaroom.common.interfaces.ISender;
import com.sunmnet.mediaroom.socket.core.server.AbstractSocketServer;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

public class Version4Server extends AbstractSocketServer implements ISender {
    KeepAliveFilter keepAliveFilter;
    ProtocolCodecFilter protocolCodecFilter;

    public Version4Server(KeepAliveFilter filter, ProtocolCodecFilter protocolCodecFilter) {
        this.keepAliveFilter = filter;
        this.protocolCodecFilter = protocolCodecFilter;
    }

    @Override
    public void send(String msg) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void start() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isWorking() {
        return false;
    }

    @Override
    protected KeepAliveFilter getKeepAliveFilter() {
        return null;
    }

    @Override
    protected ProtocolCodecFilter getProtocolCodecFilter() {
        return null;
    }
}
