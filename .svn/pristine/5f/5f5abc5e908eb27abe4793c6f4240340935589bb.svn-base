package com.sunmnet.mediaroom.tabsp.connector.network;

import com.sunmnet.mediaroom.common.interfaces.ISender;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.socket.core.client.SimpleSocketClient;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketSessionListener;
import com.sunmnet.mediaroom.tabsp.connector.service.IConnector;
import com.sunmnet.mediaroom.tabsp.connector.service.ITransformer;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 新版通信协议客户端
 */
public class NetowrkClient implements SocketSessionListener, ISender, IConnector, ITransformer {
    private static final String sourceName = "核心";
    private static final String destNae = "智能面板";
    SimpleSocketClient client = null;
    IoSession ioSession;

    public NetowrkClient(String host, int port) {
        this.client = new SimpleSocketClient();
        try {
            this.client.connect(host, port);
        } catch (IOException e) {
            RunningLog.error(e);
        }
    }

    @Override
    public void send(String msg) {
        sendMsg(msg);
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
    public void onSessionCreated(IoSession session) {
        this.ioSession = session;
        EventBus.getDefault().post(this);
    }

    @Override
    public void onSessionOpened(IoSession session) {
        this.ioSession = session;
    }

    @Override
    public void onSessionClosed(IoSession session) {
        this.ioSession = null;
    }

    @Override
    public void onMessageReceived(IoSession session, Object message) {
        RunningLog.commu(sourceName, destNae, message.toString());

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
    }

    @Override
    public void onMessageSent(IoSession session, Object message) {
        RunningLog.run(message + " 发送成功!!");
    }

    @Override
    public void onSessionExceptionCaught(IoSession session, Throwable throwable) {
        this.ioSession = null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String msg = sw.toString();
        pw.close();
        RunningLog.error(msg);
    }

    @Override
    public void close() {
        this.client.disconnect(ioSession);
    }

    @Override
    public void release() {

    }

    @Override
    public boolean sendMsg(String message) {
        if (this.ioSession != null) {
            WriteFuture writeFuture = this.ioSession.write(message);
            return writeFuture.isWritten();
        }
        return false;
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        if (this.ioSession != null) {
            WriteFuture writeFuture = this.ioSession.write(message);
            return writeFuture.isWritten();
        }
        return false;
    }
}
