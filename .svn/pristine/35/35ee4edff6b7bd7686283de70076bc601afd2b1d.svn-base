package com.sunmnet.mediaroom.device.version4.protocol;

import android.text.TextUtils;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.device.bean.OtherSetting;
import com.sunmnet.mediaroom.device.bean.message.VolumeMessage;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;
import org.greenrobot.eventbus.EventBus;


public class DeviceVolumeProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        OtherSetting setting = new OtherSetting();
        VolumeMessage volumeMessage = JacksonUtil.jsonStrToBean(message.getMsg(), VolumeMessage.class);
        if (volumeMessage != null && !TextUtils.isEmpty(volumeMessage.getValue())) {
            setting.setVolumn(volumeMessage.getValue());
            setting.setVgaMode(volumeMessage.getMode());
            EventBus.getDefault().postSticky(setting);
        }
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.DEVICE_VOLUME;
    }

}
