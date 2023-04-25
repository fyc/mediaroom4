package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;
import com.sunmnet.mediaroom.util.bean.sp.ControlSceneDto;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 面板-请求（或控制）教室（控制）场景
 */
public class ClassSceneProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        List<ControlSceneDto> scenes = JacksonUtil.jsonStrToList(message.getMsg(), ControlSceneDto.class);
        EventBus.getDefault().post(scenes);
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.TABSP_CLASS_SCENE;
    }

}
