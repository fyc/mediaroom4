package com.sunmnet.mediaroom.device.bean;

public class DeviceScene<T> {
    public DeviceScene(T t, String name) {
        this.sceneName = name;
        this.property = t;
    }

    public T getScene() {
        return property;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id;

    public void setProperty(T sceneDto) {
        this.property = sceneDto;
    }

    T property;

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    String sceneName;
}
