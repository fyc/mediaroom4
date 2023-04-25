package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 版本升级
 */
public class UpgradeProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.run("升级事件！！");
        Event<String, EventType> upgradeEvent = new Event<>();
        upgradeEvent.setEventType(EventType.UPGRADE_READY);
        JsonObject object = (JsonObject) new JsonParser().parse(message.getMsg());
        object.addProperty("uuid", message.getUuid());
        upgradeEvent.setMessage(object.toString());
        EventBus.getDefault().post(upgradeEvent);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.SYS_UPGRATE;
    }

}
