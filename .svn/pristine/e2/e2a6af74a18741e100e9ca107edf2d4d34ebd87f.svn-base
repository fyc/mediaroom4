package com.sunmnet.mediaroom.device.version4.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.device.bean.VagChangeEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;


public class DeviceVgaChangeProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        VagChangeEvent event = new VagChangeEvent();
        event.setVgaMode(message.getMsg());
        EventBus.getDefault().postSticky(event);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.DEVICE_VGA_CHANGE;
    }

}
