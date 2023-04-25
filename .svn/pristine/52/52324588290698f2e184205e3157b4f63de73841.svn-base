package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.device.bean.TabspRegisterInfo;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 面板注册
 */
public class TabspRegisterProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        TabspRegisterInfo registerInfo = JacksonUtil.jsonStrToBean(message.getMsg(), TabspRegisterInfo.class);
        EventBus.getDefault().post(registerInfo);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_REGISTER;
    }

}
