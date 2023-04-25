package com.sunmnet.mediaroom.tabsp.record.impl.reach;

import java.io.OutputStream;

/**
 * Created by dengzl on 2019/7/30.
 */
public interface ISender {
    public void send(OutputStream os, String data) throws Exception;
    public void send(OutputStream os, byte[] data) throws Exception;
}
