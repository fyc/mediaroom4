package com.sunmnet.mediaroom.tabsp.socket.protocol;

import android.content.pm.PackageInfo;

import com.litesuits.common.utils.AppUtil;
import com.sunmnet.mediaroom.common.BaseApplication;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.TerminalMessage;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class TabspVersionProtocol implements ProtocolHandler<SocketMessage, TerminalMessage> {

    @Override
    public SocketResult<TerminalMessage> handle(IoSession session, SocketMessage message) {
        PackageInfo packageInfo = new AppUtil().getPackageInfo(BaseApplication.getInstance());
        RunningLog.run("上发版本:" + packageInfo.versionName);
        TerminalMessage terminalMessage = new TerminalMessage(message, TerminalMessage.TABSP);
        terminalMessage.setMsg(packageInfo.versionName);
        return SocketResult.success(terminalMessage);
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_VERSION;
    }

}
