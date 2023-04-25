package com.sunmnet.mediaroom.tabsp.desktop.model;

import com.sunmnet.mediaroom.common.tools.ShellUtils;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

public class MainActivityPresenter implements Runnable {
    public void init(){
        ThreadUtils.execute(this);
    }

    @Override
    public void run() {
        boolean isRoot= ShellUtils.checkRootPermission();
        /*String command="svc wifi disable";
        ShellUtils.execCommand(command,isRoot);*/
    }
}
