package com.sunmnet.mediaroom.tabsp.bean;

import android.graphics.Color;

import com.sunmnet.mediaroom.tabsp.interfaces.ICommonItem;


public abstract class AbstractItem<T> implements ICommonItem {
    protected T t;

    public T getEntity() {
        return t;
    }

    public AbstractItem(Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground) {
        this.selectIcon = selectIcon;
        this.unselectIcon = unselectIcon;
        this.selectBackgound = selectBackgound;
        this.unselectBackground = unselectBackground;
    }

    public AbstractItem(Object selectIcon, Object unselectIcon, Object selectBackgound, Object unselectBackground, Object selectTextColor, Object unselectTextColor) {
        this.selectIcon = selectIcon;
        this.unselectIcon = unselectIcon;
        this.selectBackgound = selectBackgound;
        this.unselectBackground = unselectBackground;
        this.selectTextColor = selectTextColor;
        this.unselectTextColor = unselectTextColor;
    }

    Object selectIcon, unselectIcon, selectBackgound, unselectBackground, selectTextColor = Color.WHITE, unselectTextColor = Color.WHITE;

    public Object getSelectIcon() {
        return selectIcon;
    }

    public void setSelectIcon(Object selectIcon) {
        this.selectIcon = selectIcon;
    }

    public Object getUnselectIcon() {
        return unselectIcon;
    }

    public void setUnselectIcon(Object unselectIcon) {
        this.unselectIcon = unselectIcon;
    }

    public Object getSelectBackgound() {
        return selectBackgound;
    }

    public void setSelectBackgound(Object selectBackgound) {
        this.selectBackgound = selectBackgound;
    }

    public Object getUnselectBackground() {
        return unselectBackground;
    }

    public void setUnselectBackground(Object unselectBackground) {
        this.unselectBackground = unselectBackground;
    }

    public Object getSelectTextColor() {
        return selectTextColor;
    }

    public void setSelectTextColor(Object selectTextColor) {
        this.selectTextColor = selectTextColor;
    }

    public Object getUnselectTextColor() {
        return unselectTextColor;
    }

    public void setUnselectTextColor(Object unselectTextColor) {
        this.unselectTextColor = unselectTextColor;
    }

    @Override
    public String getName() {
        return null;
    }
}
