package com.sunmnet.mediaroom.tabsp.record.interfaces;


public interface IRecord {
    public static enum RecordStatus{
        /**已连接*/
        CONNECTED(7),
        /**
         * 未连接
         * */
        DISCONNECTED(8),
        /**
         * 空闲中
         * */
        IDLE(3),//空闲
        /**
         * 未使用
         * */
        SHIELD(1),
        /**
         * 暂停中
         * */
        PAUSE(0),
        /**
         * 录制中
         * */
        RECODING(2),
        /**
         * 异常
         * */
        ERROR(4),
        /**
         * 已保存
         * */
        SAVED(5),
        /**
         * 休眠中
         * */
        SLEEP(6);
        ;
        RecordStatus(int val){
            this.value=val;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        int value;
    }
    public interface IRecordStatus {

        public RecordStatus getRecordStatus();
        public void setRecordStatus(RecordStatus status);
        public String getRecordingTime();
        public String getUUId();
    }
    public class Status implements IRecordStatus{
        public Status(RecordStatus status)
        {
            this.status=status;
        }
        RecordStatus status;
        String uuid,recordingTime;
        int time=-1;
        public Status(RecordStatus status,String uuid){
            this(status);
            this.uuid=uuid;
        }
        public Status(RecordStatus status,String uuid,String recordingTime)
        {
            this(status,uuid);
            this.recordingTime=recordingTime;
        }
        public Status(RecordStatus status,String uuid,int time)
        {
            this(status,uuid);
            this.time=time;
        }
        @Override
        public RecordStatus getRecordStatus() {
            return this.status;
        }

        @Override
        public void setRecordStatus(RecordStatus status) {
            this.status=status;
        }

        @Override
        public String getRecordingTime() {
            if (recordingTime==null&&time>0){
                //this.recordingTime= LocalStringUtils.secToTime(time);
            }
            return recordingTime;
        }

        @Override
        public String getUUId() {
            return uuid;
        }
    }
    public interface RecordListener{
        /**
         * 录播暂停中...
         * */
        public <T> void onRecordPaused(T t);
        /**
         * 录播进行中...
         * */
        public <T> void onRecording(T t);
        /**
         * 录播空闲
         * */
        public <T> void onRecordIdle(T t);
        /**
         * 录播结束..
         * */
        public <T> void onRecordSaved(T t);
    }
    public void setRecordListener(RecordListener listener);
    /**
     * 获取录播状态
     * */
    public <T> T getRecordState();
    /**
     * 获取上一次操作发生的错误
     * */
    public <T> T getLastError();
    /**
     * 获取直播流播放路径
     * */
    public String getPlayUrl();
    /**
     * 刷新状态
     * */
    public void refresh();
    public boolean start(String uuid);
    /**
     *暂停录播
     * */
    public boolean pause(String uuid);
    /**继续录播*/
    public boolean goon(String uuid);
    /**
     * 停止录播
     * */
    public boolean stop(String uuid);
    /**
     * 释放资源
     * */
    public void release();
}
