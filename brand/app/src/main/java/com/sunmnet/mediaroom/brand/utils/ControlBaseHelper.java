package com.sunmnet.mediaroom.brand.utils;


import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;
import com.sunmnet.mediaroom.brand.bean.control.base.BaseControlStyle;

import java.util.Map;


public class ControlBaseHelper {

    public static void setXAxis(View view, int xAxis) {
        if (view.getLayoutParams() != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.leftMargin = xAxis;
            view.setLayoutParams(layoutParams);
        }
        if (view instanceof IBaseControl) {
            ((IBaseControl) view).setXAxis(xAxis);
        }
    }

    public static void setYAxis(View view, int yAxis) {
        if (view.getLayoutParams() != null && view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.topMargin = yAxis;
            view.setLayoutParams(layoutParams);
        }
        if (view instanceof IBaseControl) {
            ((IBaseControl) view).setYAxis(yAxis);
        }
    }

    public static void setControlWidth(View view, int width) {
        if (view.getLayoutParams() != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = width;
            view.setLayoutParams(layoutParams);
        }
        if (view instanceof IBaseControl) {
            ((IBaseControl) view).setControlWidth(width);
        }
    }

    public static void setControlHeight(View view, int height) {
        if (view.getLayoutParams() != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = height;
            view.setLayoutParams(layoutParams);
        }
        if (view instanceof IBaseControl) {
            ((IBaseControl) view).setControlHeight(height);
        }
    }

    public static void setControlStyleAttrs(View view, Map<String, Object> attrs) {
        if (!attrs.containsKey("style") || !(attrs.get("style") instanceof Map))
            return;
        Map<String, Object> style = TypeUtil.castToMap_SO(attrs.get("style"));
        setXAxis(view, TypeUtil.objToInt(style.get("left")));
        setYAxis(view, TypeUtil.objToInt(style.get("top")));
        setControlWidth(view, TypeUtil.objToInt(style.get("width")));
        setControlHeight(view, TypeUtil.objToInt(style.get("height")));

        if (view instanceof IBaseControl) {
            IBaseControl baseControl = (IBaseControl) view;
            baseControl.setControlType(TypeUtil.objToStr(attrs.get("type")));
            baseControl.setControlId(TypeUtil.objToStr(attrs.get("id")));
            baseControl.setZIndex(TypeUtil.objToInt(attrs.get("zIndex")));
        }
    }

    public static void setControlStyle(View view, BaseControlStyle style) {
        setXAxis(view, style.getLeft());
        setYAxis(view, style.getTop());
        setControlWidth(view, style.getWidth());
        setControlHeight(view, style.getHeight());
    }

    /**
     * 递归刷新组件数据
     */
    public static void refreshControlRecursion(View view) {
        if (view instanceof IBaseControl) {
            IBaseControl control = (IBaseControl) view;
            control.refreshControlData();
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                refreshControlRecursion(viewGroup.getChildAt(i));
            }
        }
    }
}
