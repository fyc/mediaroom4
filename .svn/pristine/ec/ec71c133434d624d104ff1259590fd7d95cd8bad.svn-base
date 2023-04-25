package com.sunmnet.mediaroom.brand.interfaces.play;

import com.sunmnet.mediaroom.brand.bean.play.PublishingRule;

import java.util.Date;

public interface IPlayable<T extends PublishingRule> {

    enum PlayState{
        INITIALIZED,//初始化
        PREPARING,//加载资源中
        PREPARED,//加载资源完毕
        PREPLAY,//播放前的准备工作中
        PLAYING,//播放中
        LOADING,//资源下载中
        LOADED,//资源下载完成
        STOP,//停止
    }

    /**停止播放*/
    void stopPlay();

    /**开始播放*/
    void startPlay();

    /**删除播放相关资源*/
    void delete();

    /**是否过期*/
    boolean isAlive();

    /**是否准备好播放*/
    boolean readyPlay();

    /**是否正在播放*/
    boolean isPlaying();

    /**播放ID。不同类型的ID可能会重复*/
    String getPlayId();

    /**播放类型  通知/节目*/
    int getType();

    /**是否插播*/
    boolean isInterCut();

    /**获取播放规则*/
    T getRule();

    /**是否在当前播放时间内**/
    boolean isWithinPlayTime();

    /**播放状态**/
    PlayState getState();

    /**规则播放状态**/
    int getRulePlayStatus();

    /** 播放的开始时间 */
    Date getPlayStartTime();
    /** 播放的结束时间 */
    Date getPlayEndTime();
}
