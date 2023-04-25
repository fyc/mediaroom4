package com.sunmnet.mediaroom.device.version3;

import com.sunmnet.mediaroom.common.bean.User;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.common.events.LoginType;
import com.sunmnet.mediaroom.common.events.UserType;
import com.sunmnet.mediaroom.device.bean.LoginEvent;
import com.sunmnet.mediaroom.device.controll.service.ITransformer;
import com.sunmnet.mediaroom.device.controll.service.LoginService;
import com.sunmnet.mediaroom.util.JsonUtils;
import com.sunmnet.mediaroom.util.MD5Util;
import com.sunmnet.mediaroom.util.bean.cmd.tabsp_login.TabspLoginCheckDto;
import com.sunmnet.mediaroom.util.tag.CommuTag;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Version3LoginServiceImpl implements LoginService {
    ITransformer sender;

    public Version3LoginServiceImpl(ITransformer sender) {
        this.sender = sender;
        EventBus.getDefault().register(this);
    }

    @Override
    public void doLogin(String userName, String pwd) {
        if (userName.equals("sunmnet") && pwd.equals("sunmnet123")) {
            Event<User, EventType> event = new Event<>();
            event.setEventType(EventType.ON_LOGIN);
            User user = new User();
            user.setUserName("维保人员");
            user.setLoginType(LoginType.MAINTENANCE);
            user.setUserType(UserType.LEVEL_1);
            event.setMessage(user);
            EventBus.getDefault().post(event);
        } else {
            String password = MD5Util.getMD5String(pwd);
            TabspLoginCheckDto user = new TabspLoginCheckDto(userName, password, "admin");
            user.setLoginType("2");
            String req = CommuTag.TABSP_LOGIN + JsonUtils.objectToJson(user);
            sender.sendMsg(req);
        }
    }

    @Override
    public void doLogin(String url, String userName, String pwd, String classroomCode) {

    }

    @Subscribe
    public void onLoginResult(TabspLoginCheckDto result) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setTag("Version3LoginServiceImpl");
        if ("1".equals(result.getAllowLogin())) {
            User user = new User();
            user.setUserName(result.getUsername());
            user.setLoginType(LoginType.ACCOUNT);
            user.setUserType(UserType.LEVEL_2);
            loginEvent.setMessage(user);
            loginEvent.setEventType(EventType.ON_LOGIN);
            EventBus.getDefault().postSticky(loginEvent);
        } else {
            User user = new User();
            user.setUserName(result.getUsername());
            user.setLoginType(LoginType.UNAUTHORIZATION);
            user.setUserType(UserType.LEVEL_2);
            loginEvent.setMessage(user);
            loginEvent.setEventType(EventType.ON_LOGIN_FAILURE);
        }
        EventBus.getDefault().postSticky(loginEvent);
    }

    @Override
    public void release() {
        EventBus.getDefault().unregister(this);
    }
}
