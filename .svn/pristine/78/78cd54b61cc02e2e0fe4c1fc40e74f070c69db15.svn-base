package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_login.TabspLoginCheckDto;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 面板登录校验
 */
public class TabspLoginProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        TabspLoginCheckDto checkDto = JacksonUtil.jsonStrToBean(message.getMsg(), TabspLoginCheckDto.class);
        EventBus.getDefault().post(checkDto);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_LOGIN;
    }

}
