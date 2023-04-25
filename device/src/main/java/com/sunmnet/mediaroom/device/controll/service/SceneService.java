package com.sunmnet.mediaroom.device.controll.service;

import java.util.List;

public interface SceneService<T> extends IRelease {
    public List<T> getScenes();

    public void setScene(T t);

    public void setScene(String name, T t);
}
