package com.sunmnet.mediaroom.tabsp.bean;

import java.util.HashMap;
import java.util.Map;

public class MenuEntity {
    public MenuEntity(String menuName, String link, String type, String parent) {
        this.menuName = menuName;
        this.link = link;
        this.type = type;
        this.parent = parent;
    }

    public MenuEntity(String link, String type, String parent) {
        this.menuName = menuName;
        this.link = link;
        this.type = type;
        this.parent = parent;
    }

    /**
     * 菜单名称
     */
    String menuName;
    /**
     * 与UIDispatcher绑定的路径
     */
    String link;
    /**
     * 类型
     */
    String type;
    /**
     * 父菜单
     */
    String parent;

    Map<String, Object> data;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    public void removeData(String key){
        if (data != null) {
            data.remove(key);
        }
    }
}
