package com.sunmnet.mediaroom.device.version4.protocol;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.device.bean.protocol.DeviceStateJson;
import com.sunmnet.mediaroom.device.events.StateChangeEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class DeviceStateProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        List<DeviceStateJson> ps = JacksonUtil.jsonStrToList(message.getMsg(), DeviceStateJson.class);
        StateChangeEvent stateChangeEvent = new StateChangeEvent();
        stateChangeEvent.deviceStateJsons = ps;
        EventBus.getDefault().post(stateChangeEvent);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.DEVICE_STATE;
    }

}
