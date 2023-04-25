package com.sunmnet.mediaroom.device.version4;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.impl.AbstractClientService;
import com.sunmnet.mediaroom.common.impl.DefaultSocketSender;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.device.version4.protocol.DeviceProtocolHandlerFactory;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketClient;
import com.sunmnet.mediaroom.util.JsonUtils;

import org.apache.mina.core.session.IoSession;

import java.util.List;

/**
 * 核心客户端Service
 */
public class CoreSocketClientService extends AbstractClientService {

    protected int source;

    public CoreSocketClientService() {
        source = TerminalMessage.TABSP;
    }

    public CoreSocketClientService(@TerminalMessage.Source int source) {
        this.source = source;
    }

    @Override
    protected SocketClient initSocketClient() {
        return new CoreSocketClient();
    }

    @Override
    protected SocketSender initSocketSender(List<IoSession> list) {
        return new DefaultSocketSender(list);
    }

    @Override
    protected ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory() {
        return new DeviceProtocolHandlerFactory();
    }

    @Override
    protected void logSend(String msg) {
        RunningLog.commu("本地", "核心", msg);
    }

    @Override
    protected void logReceive(String msg) {
        RunningLog.commu("核心", "本地", msg);
    }

    @Override
    public void onSessionOpened(IoSession session) {
        super.onSessionOpened(session);
        ThreadUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    sendMsg(CommuTag.TABSP_ONLINE, "");
                    Thread.sleep(300);
                    sendMsg(CommuTag.TABSP_CONFIGURATION, "SP");
                    Thread.sleep(300);
                    sendMsg(CommuTag.TABSP_GET_STATE, "");
                    Thread.sleep(300);
                    sendMsg(CommuTag.DEVICE_VGA_CHANGE, "");
                } catch (Exception e) {
                    RunningLog.error(e);
                }
            }
        });
    }

    public void sendMsg(String topic, String message) {
        TerminalMessage terminalMessage = new TerminalMessage();
        terminalMessage.setKey(topic);
        terminalMessage.setSource(source);
        terminalMessage.setMsg(message);
        sendMessage(JsonUtils.objectToJson(terminalMessage));
    }
}