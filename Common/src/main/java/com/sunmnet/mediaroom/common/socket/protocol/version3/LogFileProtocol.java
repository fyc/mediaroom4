package com.sunmnet.mediaroom.common.socket.protocol.version3;

import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.util.tag.CommuTag;

import org.apache.mina.core.session.IoSession;

import java.io.File;

public class LogFileProtocol implements ProtocolHandler<String, String> {

    @Override
    public SocketResult<String> handle(IoSession session, String message) {
        String filePath = RunningLog.getLogFile(message);
        File file = new File(filePath);
        session.write(file);
        return null;
    }

    @Override
    public String getTagName() {
        return CommuTag.DEBUG_LOG_EXPORT;
    }
}
