package com.sunmnet.mediaroom.tabsp.socket.protocol;

import static com.sunmnet.mediaroom.tabsp.ui.dispatch.LoginDispatcher.DEFAULT_SUPER_ACCOUNT;
import static com.sunmnet.mediaroom.tabsp.ui.dispatch.LoginDispatcher.DEFAULT_SUPER_PASSWORD;
import static com.sunmnet.mediaroom.tabsp.ui.dispatch.LoginDispatcher.SUPER_ACCOUNT_SP_KEY;
import static com.sunmnet.mediaroom.tabsp.ui.dispatch.LoginDispatcher.SUPER_PASSWORD_SP_KEY;
import static com.sunmnet.mediaroom.tabsp.ui.dispatch.LoginDispatcher.SUPER_USER_SP_NAME;

import com.sunmnet.mediaroom.common.bean.SocketMessage;
import com.sunmnet.mediaroom.common.bean.SocketResult;
import com.sunmnet.mediaroom.common.interfaces.ProtocolHandler;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.SharePrefUtil;
import com.sunmnet.mediaroom.devicebean.new2.utils.JsonUtils;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.bean.SuperUserDto;

import org.apache.mina.core.session.IoSession;

/**
 * Create by WangJincheng on 2021-11-05
 * 超级用户指令，用于紧急情况，获取或设置超级用户账号和密码
 */

public class SuperUserProtocol implements ProtocolHandler<SocketMessage, String> {

    @Override
    public String getTagName() {
        return "f0";
    }

    @Override
    public SocketResult<String> handle(IoSession session, SocketMessage message) {
        RunningLog.run(message.getMsg());
        if (message.getMsg() == null || message.getMsg().isEmpty()) {
            String superAccount = SharePrefUtil.getString(TabspApplication.getInstance(), SUPER_USER_SP_NAME, SUPER_ACCOUNT_SP_KEY) != null ?
                    SharePrefUtil.getString(TabspApplication.getInstance(), SUPER_USER_SP_NAME, SUPER_ACCOUNT_SP_KEY) : DEFAULT_SUPER_ACCOUNT;
            String superPassword = SharePrefUtil.getString(TabspApplication.getInstance(), SUPER_USER_SP_NAME, SUPER_PASSWORD_SP_KEY) != null ?
                    SharePrefUtil.getString(TabspApplication.getInstance(), SUPER_USER_SP_NAME, SUPER_PASSWORD_SP_KEY) : DEFAULT_SUPER_PASSWORD;
            SuperUserDto superUserDto = new SuperUserDto(superAccount, superPassword);
            session.write("超级账号:" + JsonUtils.toJson(superUserDto));
            session.write("如需修改。请参照格式:{\"key\":\"f0\",\"msg\":\"{\\\\\"name\\\\\":\\\\\"super\\\\\",\\\\\"password\\\\\":\\\\\"123456\\\\\"}\"}\\n");
        } else {
            try {
                SuperUserDto superUserDto = JsonUtils.fromJson(message.getMsg(), SuperUserDto.class);
                SharePrefUtil.saveValue(TabspApplication.getInstance(), SUPER_USER_SP_NAME, SUPER_ACCOUNT_SP_KEY, superUserDto.getName());
                SharePrefUtil.saveValue(TabspApplication.getInstance(), SUPER_USER_SP_NAME, SUPER_PASSWORD_SP_KEY, superUserDto.getPassword());
                session.write("修改超级账号成功");
            } catch (Exception e) {
                e.printStackTrace();
                RunningLog.error(e.getMessage());
                session.write("格式有误。请参照格式:{\"key\":\"f0\",\"msg\":\"{\\\\\"name\\\\\":\\\\\"super\\\\\",\\\\\"password\\\\\":\\\\\"123456\\\\\"}\"}\\n");
            }
        }
        return SocketResult.success();
    }
}
