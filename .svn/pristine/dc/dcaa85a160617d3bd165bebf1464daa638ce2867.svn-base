package com.sunmnet.mediaroom.tabsp.record.impl.reach;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dengzl on 2019/7/30.
 */
public class ReachServer extends AbstractExecutor{
    public ReachServer(ReachEventListener event, int port){
        this.port=port;
        this.event=event;
    }
    int port;
    ReachEventListener event;
    ServerSocket server;
    @Override
    protected boolean work() {
        Socket socket=null;
        try {
            server=new ServerSocket(this.port);
            Socket cacheSocket=null;
            while ((socket=server.accept())!=null){
                System.out.println("客户端连接成功!!");
                closeSocket(cacheSocket);
                event.onClientConnected(socket);
                cacheSocket=socket;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void closeSocket(Socket socket){
        try {
            if (socket!=null)socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() {
        super.stop();
        if (server!=null) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
