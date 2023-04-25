package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;
import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;

public class BaseFrameControl<P extends ControlProperty> extends FrameLayout implements IBaseControl<P> {


    protected String controlId;
    protected String controlType;
    protected int xAxis;
    protected int yAxis;
    protected int zIndex;
    protected int width;
    protected int height;

//    protected Map<String, Object> controlDataMap;
//
//    protected Map<String, Object> attrDataMap;

    protected String resourcePath;

    protected P property;


    public BaseFrameControl(Context context) {
        super(context, null);
        init();
    }

    public BaseFrameControl(Context context, MarginLayoutParams layoutParams) {
        super(context, null);
        setLayoutParams(layoutParams);
        init();
    }

    public BaseFrameControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init(){

    }

    @Override
    public String getControlId() {
        return controlId;
    }

    @Override
    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    @Override
    public String getControlType() {
        return controlType;
    }

    @Override
    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    @Override
    public int getXAxis() {
        return xAxis;
    }

    @Override
    public void setXAxis(int xAxis) {
        this.xAxis = xAxis;
        if (getLayoutParams() != null && getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
            layoutParams.leftMargin = xAxis;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getYAxis() {
        return yAxis;
    }

    @Override
    public void setYAxis(int yAxis) {
        this.yAxis = yAxis;
        if (getLayoutParams() != null && getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
            layoutParams.topMargin = yAxis;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public int getControlWidth() {
        return width;
    }

    @Override
    public int getControlHeight() {
        return height;
    }

//    @Override
//    public Map<String, Object> getControlDataMap() {
//        return controlDataMap;
//    }

    @Override
    public void refreshControlData() {

    }

    @Override
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public String getResourceFilePath(String suffixPath) {
        if (suffixPath != null
                && (suffixPath.startsWith("android.resource://")
                || suffixPath.startsWith("res://")
                || suffixPath.startsWith("file://")
                || suffixPath.startsWith("http://")
                || suffixPath.startsWith("https://")))
            return suffixPath;
        String path = getResourcePath();
        if (path == null || suffixPath == null)
            return "";
        if (path.endsWith("/") && suffixPath.startsWith("/"))
            path += suffixPath.substring(1);
        else
            path += suffixPath;
        return path;
    }

    @Override
    public void onControlShow() {

    }

    @Override
    public void onControlHide() {

    }

    @Override
    public void setProperty(P property) {
        this.property = property;
        ControlBaseHelper.setControlStyle(this, property.getStyle());
    }

    @Override
    public P getProperty() {
        return property;
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        controlDataMap = dataMap;
//        if (dataMap != null)
//            attrDataMap = TypeUtil.castToMap_SO(dataMap.get("attr"));
//        ControlBaseHelper.setControlStyleAttrs(this, controlDataMap);
//    }

    @Override
    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public void setControlWidth(int width) {
        this.width = width;
        if (getLayoutParams() != null) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = width;
            setLayoutParams(layoutParams);
        }
    }


    @Override
    public void setControlHeight(int height) {
        this.height = height;
        if (getLayoutParams() != null) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = height;
            setLayoutParams(layoutParams);
        }
    }

}
