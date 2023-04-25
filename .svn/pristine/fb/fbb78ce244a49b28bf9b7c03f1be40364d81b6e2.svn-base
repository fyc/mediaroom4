package com.sunmnet.mediaroom.brand.socket.protocol.v4;


import android.content.pm.PackageInfo;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.AppUtil;
import com.sunmnet.mediaroom.devicebean.tag.CommuTag;

import org.apache.mina.core.session.IoSession;

public class VersionProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public String getTagName() {
        return CommuTag.BRAND_VERSION;
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        PackageInfo packageInfo = AppUtil.getPackageInfoByContext(DeviceApp.getApp());
        if (packageInfo != null) {
            return SocketResult.success(packageInfo.versionName);
        }
        return SocketResult.fail();
    }

}
