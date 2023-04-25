package com.sunmnet.mediaroom.brand.bean.control.other;

import com.sunmnet.mediaroom.brand.bean.control.base.BaseControlStyle;

public class ShapeControlStyle extends BaseControlStyle {


    private String borderColor;
    private int borderSize;
    private String fillColor;

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }
}
