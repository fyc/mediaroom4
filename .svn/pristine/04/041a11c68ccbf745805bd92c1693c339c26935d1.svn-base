package com.sunmnet.mediaroom.device.version4;

import com.sunmnet.mediaroom.socket.core.client.SimpleSocketClient;
import com.sunmnet.mediaroom.socket.core.protocol.heartbeat.ActiveKeepAliveMessageFactory;

import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class CoreSocketClient extends SimpleSocketClient {

    KeepAliveFilter filter;

    protected KeepAliveFilter getKeepAliveFilter() {
        if (filter==null){
            filter = new KeepAliveFilter(new ActiveKeepAliveMessageFactory());
            filter.setRequestTimeout(5);
            filter.setRequestInterval(25);
        }
        return filter;
    }

    public void setRequestTimeoutHandler(KeepAliveRequestTimeoutHandler timeoutHandler) {
        getKeepAliveFilter().setRequestTimeoutHandler(timeoutHandler);
    }
}
