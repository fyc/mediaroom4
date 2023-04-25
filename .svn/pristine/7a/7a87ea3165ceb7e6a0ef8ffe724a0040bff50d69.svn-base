package com.sunmnet.mediaroom.tabsp.socket.protocol;

import com.sunmnet.mediaroom.device.version4.protocol.DeviceProtocolHandlerFactory;

public class TabspProtocolHandlerFactory extends DeviceProtocolHandlerFactory {

    @Override
    protected void init() {
        super.init();
        initProtocol(new TabspCustomConfigProtocol());
        initProtocol(new ClassSceneProtocol());
        initProtocol(new SyncTimeProtocol());
        initProtocol(new OnClassProtocol());
        initProtocol(new TabspLoginProtocol());
        initProtocol(new TabspRegisterProtocol());
        initProtocol(new TabspStateProtocol());
        initProtocol(new TabspVersionProtocol());
        initProtocol(new UpgradeProtocol());
        initProtocol(new TabspSyncConfigProtocol());
        // 注册处理登录二维码结果协议
        initProtocol(new TabspLoginQrCodeOperateProtocol());
        // 超级用户协议
        initProtocol(new SuperUserProtocol());
    }
}
