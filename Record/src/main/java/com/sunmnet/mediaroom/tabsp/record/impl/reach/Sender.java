package com.sunmnet.mediaroom.tabsp.record.impl.reach;

import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by dengzl on 2019/7/30.
 */
public class Sender extends AbstractExecutor implements ISender{
    private static class SenderObject{
        OutputStream output;
        byte[] data;
    }
    ArrayBlockingQueue<SenderObject> ques=new ArrayBlockingQueue<>(10);
    @Override
    public void send(OutputStream os, String data) {
        this.send(os, data.getBytes());
    }

    @Override
    public void send(OutputStream os, byte[] data) {
        SenderObject object=new SenderObject();
        object.output=os;
        object.data=data;
        ques.offer(object);
    }

    @Override
    protected boolean work() throws Exception {
        SenderObject object=null;
        while ((object=ques.take())!=null){
            if (object.output!=null){
                object.output.write(object.data);
                object.output.flush();
                //System.out.println("发送结束!!"+new String(object.data));
            }
            if (!isRunning)break;
        }
        return false;
    }

    @Override
    public void stop() {
        super.stop();
        send(null,"");
    }
}
