package com.sunmnet.mediaroom.common.socket.protocol.version4;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LogFileProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        String msg = message.getMsg();
        if (msg == null) {
            msg = "";
        } else {
            msg = msg.trim();
        }
        if (msg.equalsIgnoreCase("list")) {
            List<File> list = RunningLog.getLogFileList();
            String logDir = RunningLog.getLogDir();
            StringBuilder builder = new StringBuilder();
            for (File f : list) {
                String path = f.getAbsolutePath();
                if (path.startsWith(logDir))
                    path = path.substring(logDir.length());
                builder.append(path).append("\n");
            }
            session.write(builder.toString());
            return SocketResult.success();
        }

        if (msg.equalsIgnoreCase("help")) {
            session.write("--说明--\r\n" +
                    "[CommuTag]+list：返回所有日志文件路径列表;\r\n" +
                    "[CommuTag]+[文件路径]：发送对应的日志文件到客户端,文件路径需以'/'开头，请使用list返回的数据;\r\n" +
                    "[CommuTag]+clean：清理历史日志文件，可指定清理的日志文件，多个文件以空格隔开;");
            return SocketResult.success();
        }

        if (msg.startsWith("/")) {
            File file = new File(RunningLog.getLogDir(), msg);
            if (file.exists()) {
                session.write(file.getAbsolutePath());
            } else {
                session.write("文件不存在：" + file.getAbsolutePath());
            }
            return SocketResult.success();
        }

        if (msg.startsWith("clean")) {
            String args = msg.trim().substring(5);
            List<File> list = new ArrayList<>();
            if (args.trim().length() == 0) {
                for (File f : RunningLog.getLogFileList()) {
                    if (f.getName().endsWith(".zip")) {
                        list.add(f);
                    }
                }
            } else {
                String[] strings = args.trim().split(" ");
                String dir = RunningLog.getLogDir();
                for (String s : strings) {
                    File file = new File(dir, s);
                    list.add(file);
                }
            }
            for (File f : list) {
                if (f.exists())
                    f.delete();
            }
            session.write("历史日志清理完毕");
            return SocketResult.success();
        }
        String filePath = RunningLog.getLogFile(msg);
        File file = new File(filePath);
        session.write(file);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.DEBUG_LOG_EXPORT;
    }
}
