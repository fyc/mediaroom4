package com.sunmnet.mediaroom.brand.bean.control.base;

import java.io.Serializable;

public class ControlProperty<S extends BaseControlStyle, A> implements Serializable {

    protected String id;
    protected String type;
    protected String name;
    protected S style;
    protected A attr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public S getStyle() {
        return style;
    }

    public void setStyle(S style) {
        this.style = style;
    }

    public A getAttr() {
        return attr;
    }

    public void setAttr(A attr) {
        this.attr = attr;
    }
}
