package com.sunmnet.mediaroom.common.socket.protocol.version4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.ShellUtils;

import org.apache.mina.core.session.IoSession;

public class ShellProtocol implements ProtocolHandler<SocketMessage, String> {
    @Override
    public String getTagName() {
        return "shell";
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        String msg = message.getMsg();
        if (TextUtils.isEmpty(msg))
            return null;
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(msg);
        if (commandResult.result == 0) {
            return SocketResult.success(commandResult.successMsg);
        } else {
            return SocketResult.fail(msg + " 命令执行失败", commandResult.errorMsg);
        }
    }
}
