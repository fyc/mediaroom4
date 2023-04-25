package com.sunmnet.mediaroom.tabsp.connector.version3;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.IBinder;


import com.sunmnet.mediaroom.common.interfaces.ISender;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.connector.service.IConnector;
import com.sunmnet.mediaroom.tabsp.connector.version3.network.DebugInfoHandler;
import com.sunmnet.mediaroom.tabsp.connector.version3.network.PlatformInfoHandler;
import com.sunmnet.mediaroom.tabsp.connector.version3.network.ServerTerminal;

import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sunmnet.mediaroom.tabsp.connector.ConnectorTag;

/***
 *适配版本3.0核心的服务
 */
public class ServiceForVersion3 extends AbstractControllService3 implements Runnable {

    ExecutorService service = Executors.newFixedThreadPool(3);
    IConnector connector;
    //Connector connector;
    ISender register, logger;
    String connectUri;
    String connectClass;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RunningLog.run("服务开始执行命令");
        String connectUrl = null;
        if (intent!=null){
            connectUrl=intent.getStringExtra(ConnectorTag.CONNECT_LINK);
            connectClass=intent.getStringExtra(ConnectorTag.CONNECT_CLASS);
        }
        if (connectUrl != null) {
            try {
                this.connectUri = connectUrl;
                ThreadUtils.execute(this);
                //service.execute(this);
            } catch (Exception e) {
                RunningLog.error(e);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {
        try {
            if (connector != null) {
                connector.close();
                connector = null;
            }
            String[] infos = this.connectUri.split(":");
            Class clazz=Class.forName(this.connectClass);
            this.connector= (IConnector) clazz.getConstructor(new Class[]{String.class,Integer.class}).newInstance(infos[0], Integer.parseInt(infos[1]));
            /*Constructor[] constructors=clazz.getConstructors();
            switch (constructors.length){
                case 2:
                    constructors[1].newInstance()
                    break;
            }*/
            //connector = new Connector(infos[0], Integer.parseInt(infos[1]), true, "", this, 10000, true);
        } catch (Exception e) {
            RunningLog.warn("设备控制端出现异常！！");
            RunningLog.error(e);
        }
        try {
            if (this.register != null) {
                this.register.stop();
                this.register.start();
            } else {
                this.register = new ServerTerminal(6401,new PlatformInfoHandler());
                this.register.start();
            }
        } catch (Exception e) {
            RunningLog.warn("注册器启动异常!!!");
            RunningLog.error(e);
        }
        try {
            if (this.logger != null) {
                this.logger.stop();
                this.logger.start();
            } else {
                this.logger = new ServerTerminal(6501,new DebugInfoHandler());
                this.logger.start();
            }
            RunningLog.init(logger);
        } catch (Exception e) {
            RunningLog.warn("日志服务器无法启动!!");
            RunningLog.error(e);
        }
    }
}
