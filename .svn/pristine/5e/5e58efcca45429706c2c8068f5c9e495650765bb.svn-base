package com.sunmnet.mediaroom.common.interfaces;

import com.sunmnet.mediaroom.common.bean.SocketResult;

import org.apache.mina.core.session.IoSession;

public interface ProtocolHandler<M, T> {

    String getTagName();

    SocketResult<T> handle(IoSession session, M message);
}
