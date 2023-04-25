package com.sunmnet.mediaroom.tabsp.record.impl.reach;

import java.net.Socket;

/**
 * Created by dengzl on 2019/7/30.
 */
public interface ReachEventListener {
    /**
     *录播连接事件
     * */
    public void onClientConnected(Socket socket);
    /**
     * 录播心跳
     * */
    public void onClientHeatbeat(ReachClient client);
    /**
     * 录播登录
     * */
    public void onClientLogin(ReachClient client);
    public void onRecording();
    public void onRecordPausing();
    public void onRecordFinished();
}
