package com.sunmnet.mediaroom.common.socket.protocol.version3;

import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.util.JsonUtils;
import com.sunmnet.mediaroom.util.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class ExecuteProtocol implements ProtocolHandler<String, String> {

    @Override
    public SocketResult<String> handle(IoSession session, String message) {
        ShellUtils.CommandResult result = ShellUtils.execCommand(message, ShellUtils.hasRoot);
        session.write(JsonUtils.objectToJson(result));
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.SYS_SHELL;
    }

}
