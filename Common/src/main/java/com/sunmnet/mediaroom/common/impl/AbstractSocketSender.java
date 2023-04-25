package com.sunmnet.mediaroom.common.impl;

import com.sunmnet.mediaroom.common.interfaces.SocketSender;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import java.io.File;
import java.util.List;

public abstract class AbstractSocketSender implements SocketSender {

    protected List<IoSession> sessionList;

    public AbstractSocketSender() {
    }

    public AbstractSocketSender(List<IoSession> sessionList) {
        this.sessionList = sessionList;
    }

    public List<IoSession> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<IoSession> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    public void sendMessage(Object msg) {
        if (sessionList != null) {
            synchronized (sessionList) {
                for (IoSession ioSession : sessionList) {
                    sendMessage(ioSession, msg);
                }
            }
        }
    }

    @Override
    public void sendFile(File file) {
        if (sessionList != null) {
            synchronized (sessionList) {
                for (IoSession session : sessionList) {
                    sendFile(session, file);
                }
            }
        }
    }

    @Override
    public void sendFile(IoSession session, File file) {
        session.write(file);
    }

    @Override
    public void sendFile(String filePath) {
        if (sessionList != null) {
            synchronized (sessionList) {
                for (IoSession ioSession : sessionList) {
                    sendFile(ioSession, filePath);
                }
            }
        }
    }

    @Override
    public void sendFile(IoSession session, String filePath) {
        session.write(new File(filePath));
    }

    @Override
    public void sendBytes(byte[] bytes) {
        if (sessionList != null) {
            synchronized (sessionList) {
                for (IoSession ioSession : sessionList) {
                    sendBytes(ioSession, bytes);
                }
            }
        }
    }

    @Override
    public void sendBytes(IoSession session, byte[] bytes) {
        IoBuffer ioBuffer = IoBuffer.wrap(bytes);
        session.write(ioBuffer);
    }
}
