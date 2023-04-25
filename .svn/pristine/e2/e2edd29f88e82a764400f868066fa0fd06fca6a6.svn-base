package com.sunmnet.mediaroom.tabsp.controll.service;

import java.util.Map;

/**
 * 画面加载器
 */
public interface UIDispatcher<T> extends UILifeCycle {
    /**
     * 分发界面
     */
    public void dispatch(T t);

    /**
     * 分发界面
     *
     * @param type 类型
     * @param t    可用实例
     */
    public void dispatch(String type, T t);

    /**
     * 分发界面
     *
     * @param type 类型
     */
    public void dispatch(String type);

    /**
     * 分发界面
     *
     * @param type   类型
     * @param parent 父容器
     */
    public void dispatch(String type, String parent);

    /**
     * 分发界面
     *
     * @param t
     * @param e
     */
    public <E> void dispatch(T t, E e);

    /**
     * @param map
     */
    void setDataMap(Map<String, Object> map);

    /**
     * 获取布局
     */
    public int getLayout();
}
