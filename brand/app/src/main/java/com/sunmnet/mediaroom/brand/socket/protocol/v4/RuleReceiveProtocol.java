package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.PlayableManager;
import com.sunmnet.mediaroom.brand.bean.play.PublishingRule;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.enums.PlayType;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class RuleReceiveProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.debug("接收到规则信息:\n" + message);
        ToastUtil.show(DeviceApp.getApp(), "班牌接收到规则");
        if (message == null || TextUtils.isEmpty(message.getMsg())) {
            return SocketResult.fail();
        }
        PublishingRule rule = JacksonUtil.jsonStrToBean(message.getMsg(), PublishingRule.class);
        if (rule == null) {
            return SocketResult.fail();
        }
        int type = rule.getType();
        PlayType playType = PlayType.get(type);
        if (playType == null) {
            return SocketResult.fail();
        }
        PlayableManager.getInstance().receiveRule(playType, message.getMsg());
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_PUBLISH;
    }

}
