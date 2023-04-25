package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 获取时间并同步
 */
public class SyncTimeProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        OtherSetting syncTime = new OtherSetting();
        syncTime.setSyncTime(message.getMsg());
        EventBus.getDefault().postSticky(syncTime);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_GET_TIME;
    }

}
