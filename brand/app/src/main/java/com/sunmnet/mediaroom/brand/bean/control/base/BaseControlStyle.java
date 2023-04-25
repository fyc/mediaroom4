package com.sunmnet.mediaroom.brand.bean.control.base;

import java.io.Serializable;

public class BaseControlStyle implements Serializable {

    /**
     * 组件的层级，数值越低越底层
     */
    protected int zIndex;

    /**
     * x坐标
     */
    protected int left;

    /**
     * y坐标
     */
    protected int top;

    /**
     * 组件宽度
     */
    protected int width;

    /**
     * 组件高度
     */
    protected int height;

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

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
}
