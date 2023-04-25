package com.sunmnet.mediaroom.brand.socket.protocol.v4;

import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.bean.config.TimeSwitchConfig;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.brand.utils.TimeSwitchHelper;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.brand.data.sharepref.BrandConfig;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;


public class TimeSwitchProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        if (message != null && !TextUtils.isEmpty(message.getMsg())) {
            RunningLog.debug("设置定时开关机:" + message);
            TimeSwitchConfig config = JacksonUtil.jsonStrToBean(message.getMsg(), TimeSwitchConfig.class);
            BrandConfig.getInstance().saveTimeSwitchConfig(DeviceApp.getApp(), config);
            TimeSwitchHelper helper = new TimeSwitchHelper(config);
            helper.setTimeSwitchConfig();
        } else {
            RunningLog.debug("无定时开关机配置数据");
            TimeSwitchHelper helper = new TimeSwitchHelper(DeviceApp.getApp());
            helper.setTimeSwitchConfig();
        }
        return SocketResult.success();
    }

    @Override
    public String getTagName() {
        return CommuTag.BRAND_TIME_SWITCH;
    }

}
