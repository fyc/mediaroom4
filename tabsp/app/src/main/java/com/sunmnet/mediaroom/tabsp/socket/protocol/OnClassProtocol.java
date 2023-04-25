package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.bean.User;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.events.LoginType;
import com.sunmnet.mediaroom.common.events.UserType;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.device.bean.LoginEvent;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * 上下课,可能是废弃协议
 */
public class OnClassProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        postClassState(message.getMsg());
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_ONCLASS;
    }

    private void postClassState(String state) {
        User onkeyUser = new User();
        onkeyUser.setUserType(UserType.LEVEL_4);
        onkeyUser.setLoginType(LoginType.UNAUTHORIZATION);
        onkeyUser.setMessgae(state);
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setTag("OnClassProtocol");
        loginEvent.setMessage(onkeyUser);
        loginEvent.setEventType(EventType.ON_ONEKEYDOWN);
        EventBus.getDefault().postSticky(loginEvent);
    }

}
