package com.sunmnet.mediaroom.device.version4.protocol;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.User;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.controll.Controller;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 核心发送同步配置信息
 */
public class SyncConfigProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public synchronized SocketResult<String> handle(IoSession session, SocketMessage message) {
        Event<User, EventType> syncEvent = new Event<>();
        syncEvent.setEventType(EventType.EVENT_LOCK);
        EventBus.getDefault().post(syncEvent);
        RunningLog.run("收到同步配置消息...");
        String msg = message.getMsg();
        if (TextUtils.isEmpty(msg)) {
            RunningLog.run("获取所有配置信息");
            Controller.getInstance().query(CommuTag.TABSP_CONFIGURATION, "SP");
            Controller.getInstance().query(CommuTag.TABSP_CONFIGURATION, "DEVICE");
            Controller.getInstance().query(CommuTag.TABSP_CONFIGURATION, "COURSE");
        } else {
            RunningLog.run("获取配置信息: " +msg);
            Controller.getInstance().query(CommuTag.TABSP_CONFIGURATION, msg);
        }
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_SYNCHRONIZE_CONFIG;
    }

}
