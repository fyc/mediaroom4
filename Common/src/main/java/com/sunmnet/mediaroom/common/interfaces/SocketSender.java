package com.sunmnet.mediaroom.common.interfaces;


import org.apache.mina.core.session.IoSession;

import java.io.File;

public interface SocketSender {

    public void sendMessage(Object msg);

    public void sendMessage(IoSession session, Object msg);

    public void sendFile(File file);

    public void sendFile(IoSession session, File file);

    public void sendFile(String filePath);

    public void sendFile(IoSession session, String filePath);

    public void sendBytes(byte[] bytes);

    public void sendBytes(IoSession session, byte[] bytes);


}
