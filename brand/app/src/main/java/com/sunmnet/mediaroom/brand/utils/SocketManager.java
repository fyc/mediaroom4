package com.sunmnet.mediaroom.brand.utils;

import com.sunmnet.mediaroom.socket.core.SocketServer;
import com.sunmnet.mediaroom.socket.core.interfaces.SocketClient;

import java.util.HashMap;
import java.util.Map;

public class SocketManager {

    private static Map<String, SocketServer> serverMap = new HashMap<>();

    private static Map<String, SocketClient> clientMap = new HashMap<>();

    public static SocketServer getServer(String key) {
        return serverMap.get(key);
    }

    public static SocketClient getClient(String key) {
        return clientMap.get(key);
    }


    public static void putServer(String key, SocketServer server) {
        serverMap.put(key, server);
    }

    public static void putClient(String key, SocketClient client) {
        clientMap.put(key, client);
    }

}
