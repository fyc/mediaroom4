package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.device.version4.protocol.SyncConfigProtocol;
import com.sunmnet.mediaroom.tabsp.eventbus.events.LockEvent;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 核心发送同步配置信息
 */
public class TabspSyncConfigProtocol extends SyncConfigProtocol {

    @Override
    public synchronized SocketResult<String> handle(IoSession session, SocketMessage message) {
        SocketResult<String> handle = super.handle(session, message);
        RunningLog.run("重新加载设备配置");
        LockEvent event = new LockEvent(EventType.ACTIVITY_RELOAD);
        EventBus.getDefault().post(event);
        return handle;
    }
}
