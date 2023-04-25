package com.sunmnet.mediaroom.tabsp.record.impl;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;
import com.sunmnet.mediaroom.tabsp.record.bean.RecordEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 录播中主动继续查询
 */
public class AbstractCirculateRecord extends AbstractRecord implements Runnable, Callable<Void> {
    public AbstractCirculateRecord(RecordEntity entity) {
        super(entity);
    }

    Future<Void> future;

    @Override
    public void refresh() {
        RunningLog.run("AbstractCirculateRecord.refresh()");
        synchronized (this) {
            if (future == null || future.isDone() || future.isCancelled()) {
                future = ThreadUtils.execute((Callable<Void>) this);
                RunningLog.run("开启录播状态刷新线程");
            } else {
                RunningLog.run("录播状态刷新线程正在运行");
            }
        }
    }

    @Override
    public void run() {
        int count = 0;
        do {
            count++;
            this.recordStatus = getRecordState();
            EventBus.getDefault().postSticky(this.recordStatus);
            try {
                //前5次每隔200ms查询一次
                if (count <= 5) {
                    Thread.sleep(200);
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                RunningLog.error("录播中，线程异常！");
                RunningLog.error(e);
            }
        } while (this.recordStatus.getRecordStatus() == RecordStatus.RECODING || count < 5);
    }


    @Override
    public Void call() throws Exception {
        run();
        synchronized (this) {
            future = null;
        }
        RunningLog.run("录播状态刷新线程运行结束");
        return null;
    }
}
