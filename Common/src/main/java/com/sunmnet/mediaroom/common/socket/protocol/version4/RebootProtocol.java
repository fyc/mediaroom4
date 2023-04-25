package com.sunmnet.mediaroom.common.socket.protocol.version4;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class RebootProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        ShellUtils.execCommand("reboot", ShellUtils.hasRoot);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.DEBUG_REBOOT;
    }
}
