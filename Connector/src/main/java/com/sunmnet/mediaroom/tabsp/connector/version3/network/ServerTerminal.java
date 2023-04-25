package com.sunmnet.mediaroom.tabsp.connector.version3.network;

import com.sunmnet.mediaroom.common.interfaces.ISender;
import com.sunmnet.mediaroom.socket.common.socket.AbstractSocketServer;
import com.sunmnet.mediaroom.socket.common.socket.ServerHandlerThread;
import com.sunmnet.mediaroom.socket.common.socket.output.SocketOutputable;
import com.sunmnet.mediaroom.tabsp.connector.service.IServerReceiver;


public class ServerTerminal extends AbstractSocketServer implements ISender {

    IServerReceiver receiver;

    public ServerTerminal(int port, IServerReceiver serverReceiver) {
        super(Integer.valueOf(port), (SocketOutputable) null, false, (String) null, (String) null, (String) null, 20480000, 10240000);
        this.receiver = serverReceiver;
    }

    public ServerTerminal(int port, boolean splitFlag, IServerReceiver serverReceiver) {
        super(Integer.valueOf(port), (SocketOutputable) null, splitFlag, (String) null, (String) null, (String) null, 20480000, 10240000);
        this.receiver = serverReceiver;
    }

    @Override
    protected void receive(String str, ServerHandlerThread serverHandlerThread) {
        if (str.startsWith("c7")) {
            int startIndex = str.indexOf("c7") + 1;
            handleRegister(str.substring(startIndex));
        }
    }

    private void handleRegister(String str) {
        /*try {
            String[] strings = str.split(";");
            RegisterInfo registerInfo = new RegisterInfo();
            String[] ips = strings[0].split(":");
            registerInfo.setPlatform(ips[0]);
            registerInfo.setPort(ips[1]);
            ips = strings[1].split(":");
            registerInfo.setControllCenter(ips[0]);
            registerInfo.setControllPort(ips[1]);
            Event<RegisterInfo, EventType> events = new Event<>();
            events.setMessage(registerInfo);
            events.setEventType(EventType.SystemEvent_REGISTER);
            EventBus.getDefault().post(events);
        } catch (Exception e) {
            RunningLog.error("注册失败", e);
        }*/
    }

    @Override
    public void send(String msg) {
        super.send(msg);
    }

    @Override
    public void stop() {
        this.close();
    }

    @Override
    public void restart() {
        this.close();
        this.start();
    }

    @Override
    public void start() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isWorking() {
        return false;
    }
}
