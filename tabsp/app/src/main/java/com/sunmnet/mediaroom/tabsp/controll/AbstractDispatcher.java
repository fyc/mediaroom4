package com.sunmnet.mediaroom.tabsp.controll;

import android.view.View;

import java.util.List;

import com.sunmnet.mediaroom.common.events.Event;
import com.sunmnet.mediaroom.common.events.EventType;
import com.sunmnet.mediaroom.tabsp.R;
import com.sunmnet.mediaroom.tabsp.bean.MenuOperator;
import com.sunmnet.mediaroom.tabsp.common.Constants;
import com.sunmnet.mediaroom.tabsp.controll.service.Dispatcher;

public abstract class AbstractDispatcher implements Dispatcher {
    @Override
    public String getPageByType(String type) {

        return null;
    }

    @Override
    public List<MenuOperator> getMenus() {
        return null;
    }

    @Override
    public <T> List<String> setCompareMenu(List<T> menus) {
        return null;
    }
    /**
     * 版本三中可能会存在在菜单栏最下面有上下课按钮
     * */
    @Override
    public void apply(String type, View view) {
        if (Constants.ROUTERPATH_CONTROLLER_MENU.equals(type)) {
            View other = view.findViewById(R.id.other);
            if (other != null) other.setVisibility(View.GONE);
        }
    }
}
