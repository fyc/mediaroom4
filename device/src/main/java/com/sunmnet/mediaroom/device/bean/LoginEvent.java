package com.sunmnet.mediaroom.device.bean;

import com.sunmnet.mediaroom.common.bean.User;
import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;

public class LoginEvent extends Event<User, EventType> {

     String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
