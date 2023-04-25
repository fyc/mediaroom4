package com.sunmnet.mediaroom.brand.bean.event;

import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;

import java.util.Arrays;
import java.util.List;

public class RefreshControlEvent {

    List<Class<? extends IBaseControl>> refreshClassList;

    public RefreshControlEvent() {
    }

    public RefreshControlEvent(Class<? extends IBaseControl>... controlClass) {
        if (controlClass != null) {
            refreshClassList = Arrays.asList(controlClass);
        }
    }

    public List<Class<? extends IBaseControl>> getRefreshClassList() {
        return refreshClassList;
    }

    public void setRefreshClassList(List<Class<? extends IBaseControl>> refreshClassList) {
        this.refreshClassList = refreshClassList;
    }

    public boolean contains(IBaseControl o) {
        if (refreshClassList == null || refreshClassList.size() == 0) {
            return true;
        } else {
            for (Class<? extends IBaseControl> cls : refreshClassList) {
                if (cls.isInstance(o))
                    return true;
            }
            return false;
        }
    }
}
