package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.interfaces.SocketService;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketServer;
import com.sunmnet.mediaroom.socket.core.protocol.codec.AbstractSocketSessionListener;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.session.IoSession;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

public abstract class AbstractServerService extends AbstractSocketSessionListener implements SocketService {

    protected SocketServer server;

    protected ProtocolHandlerFactory<SocketMessage> protocolHandlerFactory;
    protected SocketSender socketSender;
    protected int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setSocketAddress(SocketAddress socketAddress) {
        if (socketAddress instanceof InetSocketAddress) {
            setPort(((InetSocketAddress) socketAddress).getPort());
        }
    }

    public AbstractServerService(int port) {
        this.port = port;
        init();
    }

    public AbstractServerService() {
        init();
    }

    protected void init() {
        server = initSocketServer();
        server.setPort(port);
        socketSender = initSocketSender(server.getOpenedSessions());
        server.setSocketSessionListener(this);
        protocolHandlerFactory = initProtocolHandlerFactory();
    }

    protected abstract SocketServer initSocketServer();

    protected abstract SocketSender initSocketSender(List<IoSession> list);

    protected abstract ProtocolHandlerFactory<SocketMessage> initProtocolHandlerFactory();

    protected void logSend(String msg) {
        RunningLog.commu("LOCAL", "REMOTE", msg);
    }

    protected void logReceive(String msg) {
        RunningLog.commu("REMOTE", "LOCAL", msg);
    }

    @Override
    public void onMessageReceived(IoSession session, String message) {
        logReceive(message);
        if (protocolHandlerFactory != null) {
            TerminalMessage socketMessage = JacksonUtil.jsonStrToBean(message, TerminalMessage.class);
            if (socketMessage != null) {
                ProtocolHandler<SocketMessage, ?> protocol = protocolHandlerFactory.getProtocolHandler(socketMessage.getKey());
                if (protocol != null) {
                    SocketResult result = protocol.handle(session, socketMessage);
                    if (result != null && socketSender != null) {
                        if (result.isResponse()) {
                            if (result.getMsg() instanceof String) {
                                socketSender.sendMessage(session, result.getMsg());
                            } else {
                                socketSender.sendMessage(session, JacksonUtil.objToJsonStr(result.getMsg()));
                            }
                        }
                    }
                } else {
                    RunningLog.run("无法识别的消息类型");
                }
            }
        }
    }

    @Override
    public void onMessageReceived(IoSession session, ByteArrayWrapper data) {

    }

    @Override
    public void onMessageReceived(IoSession session, String fileName, ByteArrayWrapper fileData) {

    }

    @Override
    public void onSessionCreated(IoSession session) {

    }

    @Override
    public void onSessionOpened(IoSession session) {
        RunningLog.run("连接成功：" + session.toString());
    }

    @Override
    public void onSessionClosed(IoSession session) {
        RunningLog.run("[" + session.toString() + "] session关闭");
    }

    @Override
    public void onMessageSent(IoSession session, Object message) {
    }

    @Override
    public void onSessionExceptionCaught(IoSession session, Throwable throwable) {
        //连接中断后发生的
        RunningLog.error("[" + session.toString() + "] \n" + throwable.getMessage());
    }


    @Override
    public void stop() {
        if (server != null) {
            server.stopServer();
            server.dispose();
        }
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void start() {
        if (server.isDisposed()) {
            server.initAcceptor();
        }
        try {
            RunningLog.run("开始创建Socket服务, 端口 " + port);
            server.setPort(port);
            server.startServer();
        } catch (IOException e) {
            RunningLog.error("无法创建Socket服务");
            RunningLog.error(e);
        }
    }

    @Override
    public boolean isActive() {
        return server != null && server.isActive() && !server.isDisposed();
    }

    @Override
    public boolean isConnected() {
        return server != null && server.getOpenedSessions().size() > 0;
    }

    @Override
    public void sendMessage(Object msg) {
        if (msg instanceof String) {
            logSend(msg.toString());
        } else if (msg instanceof ByteArrayWrapper) {
            logSend("ByteArray length: " + ((ByteArrayWrapper) msg).getData().length);
        } else {
            logSend("Object : " + msg.toString());
        }
        socketSender.sendMessage(msg);
    }

    @Override
    public void sendMessage(IoSession session, Object msg) {
        if (msg instanceof String) {
            logSend(msg.toString());
        } else if (msg instanceof ByteArrayWrapper) {
            logSend("ByteArray length: " + ((ByteArrayWrapper) msg).getData().length);
        } else {
            logSend("Object : " + msg.toString());
        }
        socketSender.sendMessage(session, msg);
    }

    @Override
    public void sendFile(File file) {
        if (socketSender != null) {
            socketSender.sendFile(file);
        }
    }

    @Override
    public void sendFile(IoSession session, File file) {
        if (socketSender != null) {
            socketSender.sendFile(session, file);
        }
    }

    @Override
    public void sendFile(String filePath) {
        if (socketSender != null) {
            socketSender.sendFile(filePath);
        }
    }

    @Override
    public void sendFile(IoSession session, String filePath) {
        if (socketSender != null) {
            socketSender.sendFile(session, filePath);
        }
    }

    @Override
    public void sendBytes(byte[] bytes) {
        if (socketSender != null) {
            socketSender.sendMessage(bytes);
        }
    }

    @Override
    public void sendBytes(IoSession session, byte[] bytes) {
        if (socketSender != null) {
            socketSender.sendBytes(session, bytes);
        }
    }

}
