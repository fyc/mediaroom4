package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.tabsp.bean.LoginQrCodeOperateResult;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

/**
 * Create by WangJincheng on 2021/4/25
 * 处理登录二维码结果协议
 */

public class TabspLoginQrCodeOperateProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public String getTagName() {
        return CommuTag.TABSP_QR_LOGIN;
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        EventBus.getDefault().post(new LoginQrCodeOperateResult(message));
        return SocketResult.success();
    }
}
