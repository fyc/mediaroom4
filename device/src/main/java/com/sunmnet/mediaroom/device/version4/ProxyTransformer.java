package com.sunmnet.mediaroom.device.version4;

import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.enums.SocketServiceType;
import com.sunmnet.mediaroom.common.interfaces.SocketService;
import com.sunmnet.mediaroom.common.tools.SocketServiceFactory;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.util.JsonUtils;

import java.net.InetSocketAddress;

/**
 * 代理数据转发器。
 */
public class ProxyTransformer implements ITransformer {

    private int source;
    private SocketService service;

    public ProxyTransformer(String host, int port) {
        this(host, port, TerminalMessage.TABSP);
    }

    public ProxyTransformer(String host, int port, @TerminalMessage.Source int source) {
        service = SocketServiceFactory.getInstance().getService(SocketServiceType.CORE.getKey());
        if (service != null) {
            service.setSocketAddress(new InetSocketAddress(host, port));
            if (service.isActive()) {
                service.restart();
            } else {
                service.start();
            }
        }
        this.source = source;
    }

    @Override
    public boolean isReady() {
        return this.service != null && this.service.isConnected();
    }

    @Override
    public void release() {
        if (service != null) {
            service.stop();
        }
    }

    @Override
    public boolean sendMsg(String message) {
        if (isReady()) {
            service.sendMessage(message);
            return true;
        }
        return false;
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        TerminalMessage terminalMessage = new TerminalMessage();
        terminalMessage.setKey(topic);
        terminalMessage.setSource(source);
        terminalMessage.setMsg(message);
        return sendMsg(JsonUtils.objectToJson(terminalMessage));
    }

}
