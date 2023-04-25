package com.sunmnet.mediaroom.brand.bean;

import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 节目布局信息
 */
public class ProgramLayoutBean implements Serializable {


    /**
     * controls : []
     * width : 1920
     * height : 1080
     * background : /picture/background.png
     * id : 1
     * name : 学校
     */

    private int width;
    private int height;
    private String background;
    private String id;
    private String name;
    private String controls;

    private List<Map<String, Object>>  controlList;

    /**
     *  本地资源文件存放路径
     */
    private String resourcePath;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControls() {
        return controls;
    }

    public void setControls(String controls) {
        this.controls = controls;
    }

    public List<Map<String, Object>> getControlList() {
        if (controlList == null && controls != null)
            controlList = JacksonUtil.jsonStrToListMap_SO(controls);
        return controlList;
    }
}
