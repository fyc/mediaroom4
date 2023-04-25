package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.User;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.events.LoginType;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.device.bean.DeviceState;
import com.sunmnet.mediaroom.device.bean.LoginEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 面板锁定状态查询
 */
public class TabspStateProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        String msg = message.getMsg();
        User user = new User();
        LoginEvent event = new LoginEvent();
        event.setTag("TabspStateProtocol");
        if (DeviceState.OPENED.getStateValue().equals(msg)) {
            user.setUserName("admin");
            user.setLoginType(LoginType.CARD);
            event.setEventType(EventType.ON_CLASS);
        } else if (DeviceState.CLOSED.getStateValue().equals(msg)) {
            user.setUserName("admin");
            user.setLoginType(LoginType.UNAUTHORIZATION);
            event.setEventType(EventType.ON_CLASSOVER);
        } else if (DeviceState.FORBIDDEN.getStateValue().equals(msg)) {
            user.setUserName("admin");
            user.setLoginType(LoginType.FORBIDDEN);
            user.setMessgae("面板已禁用");
            event.setEventType(EventType.ON_LOGIN_FAILURE);
        }
        event.setMessage(user);
        EventBus.getDefault().postSticky(event);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_GET_STATE;
    }

}
