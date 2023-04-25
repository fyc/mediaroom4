package com.sunmnet.mediaroom.tabsp.record.impl.reach;

import com.sunmnet.mediaroom.common.tools.RunningLog;

/**
 * Created by dengzl on 2019/7/30.
 */
public abstract class AbstractExecutor implements IExecute, Runnable {
    boolean isRunning=false;
    protected abstract boolean work() throws Exception;
    protected boolean isRunning(){
        return this.isRunning;
    }
    @Override
    public void start() {
        if(isRunning==false){
            isRunning=true;
            Thread thread=new Thread(this);
            thread.setName("AbstractExecutor");
            thread.start();
        }
    }

    @Override
    public void stop() {
        this.isRunning=false;
    }

    @Override
    public void run() {
        try{
            while (isRunning&&this.work()){
                System.out.println("是否正在运行:"+isRunning);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        RunningLog.run("AbstractExecutor结束任务");
    }
}
