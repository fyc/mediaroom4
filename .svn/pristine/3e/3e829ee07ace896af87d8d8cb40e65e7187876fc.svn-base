package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.device.bean.FileGetter;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 面板自定义配置
 */
public class TabspCustomConfigProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        FileGetter getter = JacksonUtil.jsonStrToBean(message.getMsg(), FileGetter.class);
        if (getter != null) {
            EventBus.getDefault().post(getter);
        }
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_PLATFORM_CONFIG;
    }

}
