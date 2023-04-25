package com.sunmnet.mediaroom.brand.interfaces.control;

import com.sunmnet.mediaroom.brand.bean.control.base.ControlProperty;

/**
 * 所有控件的基本属性
 */

public interface IBaseControl<P extends ControlProperty> {


    /**
     * @param controlId 组件ID
     */
    void setControlId(String controlId);


    /**
     * @param controlType 组件类型
     */
    void setControlType(String controlType);


    /**
     * @param xAxis 组件X坐标
     */
    void setXAxis(int xAxis);


    /**
     * @param yAxis 组件Y坐标
     */
    void setYAxis(int yAxis);


    /**
     * @param zIndex 组件图层顺序数
     */
    void setZIndex(int zIndex);


    /**
     * @param width 组件宽度
     */
    void setControlWidth(int width);


    /**
     * @param height 组件高度
     */
    void setControlHeight(int height);

    /**
     *
     * @param dataMap 组件配置属性
     */
//    void setControlDataMap(Map<String, Object> dataMap);

    /**
     * @return 组件ID
     */
    String getControlId();


    /**
     * @return 组件类型
     */
    String getControlType();


    /**
     * @return 组件X坐标
     */
    int getXAxis();


    /**
     * @return 组件Y坐标
     */
    int getYAxis();


    /**
     * @return 组件图层顺序数
     */
    int getZIndex();


    /**
     * @return 组件宽度
     */
    int getControlWidth();


    /**
     * @return 组件高度
     */
    int getControlHeight();

    /**
     *
     * @return 控件配置属性
     */
//    Map<String, Object> getControlDataMap();


    /**
     * 刷新控件数据
     */
    void refreshControlData();


    /**
     * 资源文件路径前缀
     */
    String getResourcePath();

    /**
     * 设置资源文件路径前缀
     */
    void setResourcePath(String resourcePath);

    /**
     * @param suffixPath 资源文件的后缀路径
     * @return 资源文件的完整路径
     */
    String getResourceFilePath(String suffixPath);

    /**
     * 在组件显示时做的操作
     */
    void onControlShow();

    /**
     * 在组件隐藏时做的操作
     */
    void onControlHide();

    /**
     * @param property 组件配置属性
     */
    void setProperty(P property);

    /**
     * @return 组件配置属性
     */
    P getProperty();

}
