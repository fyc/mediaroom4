package com.sunmnet.mediaroom.tabsp.connector.version4;

import com.sunmnet.mediaroom.tabsp.connector.service.ITransformer;

public class Version3Transformer implements ITransformer, Runnable {
    @Override
    public void release() {

    }

    @Override
    public boolean sendMsg(String message) {
        return false;
    }

    @Override
    public boolean sendMsg(String topic, String message) {
        return false;
    }

    @Override
    public void run() {

    }
}
