package com.sunmnet.mediaroom.tabsp.connector.version3;

import android.app.Service;

import com.sunmnet.mediaroom.socket.common.socket.output.SocketOutputable;

/**
 * 3.0通信协议的android服务，空实现
 * */
public abstract class AbstractControllService3 extends Service implements SocketOutputable {
    @Override
    public void serverAccept(int i, String s) {

    }

    @Override
    public void serverAcceptCaught(int i, Throwable throwable) {

    }

    @Override
    public void serverBeforeCreate(int i, String s) {

    }

    @Override
    public void serverAfterCreate(int i, String s) {

    }

    @Override
    public void serverBeforeClose(int i, String s) {

    }

    @Override
    public void serverAfterClose(int i, String s) {

    }

    @Override
    public void serverInnerMsg(int i, String s, Object o, boolean b) {

    }

    @Override
    public void serverSocketCreateCaught(int i, Throwable throwable) {

    }

    @Override
    public void serverHandlerCloseCaught(String s, Throwable throwable) {

    }

    @Override
    public void serverHandlerSendCaught(String s, byte[] bytes, Throwable throwable) {

    }

    @Override
    public void serverReceiveCaught(int i, String s, Throwable throwable) {

    }

    @Override
    public void serverHandlerRunCaught(int i, String s, Throwable throwable) {

    }

    @Override
    public void serverFileTransferCaught(int i, String s, Throwable throwable) {

    }

    @Override
    public void clientCreateCaught(String s, int i, Throwable throwable) {

    }

    @Override
    public void clientBeforeClose(String s, int i) {

    }

    @Override
    public void clientAfterClose(String s, int i) {

    }

    @Override
    public void clientBreakReconnect(String s, int i) {

    }

    @Override
    public void clientServerNoExistReconnect(String s, int i) {

    }

    @Override
    public void clientCancelReconnect(String s, int i) {

    }

    @Override
    public void clientInnerMsg(String s, int i, String s1, Object o, boolean b) {

    }

    @Override
    public void clientInputCaught(String s, int i) {

    }

    @Override
    public void clientReceiveCaught(String s, int i, Throwable throwable) {

    }

    @Override
    public void clientConnectCaught(String s, int i, Throwable throwable) {

    }

    @Override
    public void clientSendCaught(String s, int i, byte[] bytes, Throwable throwable) {

    }

    @Override
    public void clientKeepAlive(String s, int i) {

    }

    @Override
    public void clientKeepAliveCaught(String s, int i, Throwable throwable) {

    }

    @Override
    public void clientFileTransferCaught(String s, int i, Throwable throwable) {

    }
}
