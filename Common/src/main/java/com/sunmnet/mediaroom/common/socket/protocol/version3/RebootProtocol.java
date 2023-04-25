package com.sunmnet.mediaroom.common.socket.protocol.version3;

import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.util.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class RebootProtocol implements ProtocolHandler<String, String> {

    @Override
    public SocketResult<String> handle(IoSession session, String message) {
        ShellUtils.execCommand("reboot", ShellUtils.hasRoot);
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.DEBUG_REBOOT;
    }

}
