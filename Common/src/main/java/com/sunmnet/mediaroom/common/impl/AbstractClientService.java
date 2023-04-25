package com.sunmnet.mediaroom.common.impl;

import com.google.gson.JsonObject;
import com.sunmnet.mediaroom.common.bean.CommonStr;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandlerFactory;
import com.sunmnet.mediaroom.common.interfaces.SocketSender;
import com.sunmnet.mediaroom.common.interfaces.SocketService;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketClient;
import com.sunmnet.mediaroom.socket.core.protocol.codec.AbstractSocketSessionListener;
import com.sunmnet.mediaroom.socket.core.protocol.codec.pack.ByteArrayWrapper;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractClientService extends AbstractSocketSessionListener implements SocketService {

    private SocketClient client;
    private IoSession mSession;

    private AtomicBoolean atomicReconnect = new AtomicBoolean(true);
    private volatile boolean beReconnect = true;

    private ProtocolHandlerFactory<SocketMessage> protocolHandlerFactory;
    private SocketSender socketSender;

    private Future<Boolean> future;

    private SocketAddress socketAddress;

    public AbstractClientService() {
        init();
    }

    protected synchronized void setReconnect(boolean reconnect) {
        if (atomicReconnect.compareAndSet(beReconnect, reconnect)) {
            beReconnect = reconnect;
        }
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    @Override
    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public void setSocketAddress(String host, int port) {
        this.socketAddress = new InetSocketAddress(host, port);
    }


    protected void init() {
        client = initSocketClient();
        socketSender = initSocketSender(client.getConnectedSessions());
        client.setSocketSessionListener(this);
        protocolHandlerFactory = initProtocolHandlerFactory();
    }

    protected abstract SocketClient initSocketClient();

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
        RunningLog.run("onSessionOpened thread: " + Thread.currentThread().getName());
        RunningLog.run("session opened：" + session.toString());
        IoSession oldSession = mSession;
        mSession = session;
        if (oldSession != null && oldSession != session && oldSession.isConnected()) {
            RunningLog.run("已存在session:" + oldSession.toString());
            RunningLog.run("关闭session:" + oldSession.toString());
            oldSession.closeNow();
        }
        postNetworkDisconnectEvent(CommonStr.NETWORK_CONNECTED);
    }

    @Override
    public void onSessionClosed(IoSession session) {
        if (session == mSession) {
            postNetworkDisconnectEvent(CommonStr.NETWORK_DISCONNECTED);
            if (beReconnect) {
                RunningLog.run("[" + session.toString() + "] session关闭，开始重连！！");
                connect(true);
            } else {
                RunningLog.run("[" + session.toString() + "] session关闭，不执行重连");
            }
        } else {
            RunningLog.run("[" + session.toString() + "] 非当前session关闭");
        }
    }

    @Override
    public void onMessageSent(IoSession session, Object message) {

    }

    @Override
    public void onSessionExceptionCaught(IoSession session, Throwable throwable) {
        RunningLog.run("onSessionExceptionCaught thread: " + Thread.currentThread().getName());
        //连接中断后发生的
        RunningLog.error("[" + session.toString() + "] \n" + throwable.getMessage());
        if (beReconnect) {
            RunningLog.warn("onSessionExceptionCaught 引发重连！！");
            connect(true);
        } else {
            RunningLog.run("onSessionExceptionCaught 中断连接！！不执行重连");
        }
    }

    private void postNetworkDisconnectEvent(String action) {
        Event<Object, EventType> event = new Event<>();
        event.setEventType(EventType.DIALOG_EVENT);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", action);
        event.setMessage(jsonObject);
        EventBus.getDefault().post(event);
    }

    private synchronized void connect(boolean delay) {
        if (client != null) {
            if (future != null && !future.isDone()) {
                RunningLog.run("连接任务正在运行...");
                return;
            }
            if (delay) {
                future = ThreadUtils.schedule(new ConnectTask(client, socketAddress), 10, TimeUnit.SECONDS);
                ThreadUtils.schedule(new ListenConnectTask(future), 10, TimeUnit.SECONDS);
            } else {
                future = ThreadUtils.execute(new ConnectTask(client, socketAddress));
                ThreadUtils.execute(new ListenConnectTask(future));
            }
        }
    }

    @Override
    public void stop() {
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        setReconnect(false);
        if (client != null) {
            if (this.mSession != null) {
                client.disconnect(mSession);
            }
            client.dispose();
        }
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public void start() {
        if (client.isDisposed()) {
            client.initConnector();
        }
        setReconnect(true);
        RunningLog.run("开始连接到 " + socketAddress.toString());
        connect(false);
    }

    @Override
    public boolean isActive() {
        return client != null && client.isActive() && !client.isDisposed();
    }

    @Override
    public boolean isConnected() {
        return mSession != null && mSession.isConnected();
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
        socketSender.sendMessage(mSession, msg);
    }

    @Override
    public void sendMessage(IoSession session, Object msg) {
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

    class ListenConnectTask implements Runnable {

        private Future<Boolean> mFuture;

        ListenConnectTask(Future<Boolean> mFuture) {
            this.mFuture = mFuture;
        }

        @Override
        public void run() {
            try {
                Boolean res = mFuture.get();
                if (res == null || !res) {
                    RunningLog.run(socketAddress.toString() + " 连接失败");
                    postNetworkDisconnectEvent(CommonStr.NETWORK_DISCONNECTED);
                    if (beReconnect) {
                        RunningLog.run(socketAddress.toString() + " 重连 ");
                        connect(true);
                        return;
                    }
                } else {
                    RunningLog.run(socketAddress.toString() + " 连接成功");
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mSession != null && mSession.isConnected()) {
                RunningLog.run(socketAddress.toString() + "已成功重连");
            } else {
                RunningLog.run(socketAddress.toString() + " 未成功重连");
                if (beReconnect) {
                    RunningLog.run(socketAddress.toString() + " 再次进行重连 ");
                    connect(true);
                }
            }
        }
    }

    static class ConnectTask implements Callable<Boolean> {
        SocketClient client;
        SocketAddress address;

        ConnectTask(SocketClient client, SocketAddress address) {
            this.client = client;
            this.address = address;
        }

        @Override
        public Boolean call() throws Exception {
            RunningLog.run("开始重连  " + address.toString());
            ConnectFuture connect = null;
            try {
                connect = client.connect(address);
                connect.await();
                if (connect.isConnected()) {
                    return true;
                } else {
                    throw new Exception(connect.getException());
                }
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.error("连接出错 " + address.toString());
                RunningLog.error(e);
            }
            if (connect != null && !connect.isDone()) {
                connect.cancel();
            }
            return false;
        }
    }
}
