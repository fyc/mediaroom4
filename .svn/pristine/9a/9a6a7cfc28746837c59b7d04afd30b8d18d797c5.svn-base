package com.sunmnet.mediaroom.brand.control.other;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.bean.control.other.ShapeControlProperty;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;

public class ShapeControl extends BaseFrameControl<ShapeControlProperty> {

    private String borderColor;
    private int borderSize;
    private String fillColor;

    public ShapeControl(Context context) {
        super(context);
    }

    public ShapeControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public ShapeControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        Map<String, Object> style = TypeUtil.castToMap_SO(dataMap.get("style"));
//        borderColor = TypeUtil.objToStrNotNull(style.get("borderColor"));
//        borderSize = TypeUtil.objToInt(style.get("borderSize"));
//        fillColor = TypeUtil.objToStrNotNull(style.get("fillColor"));
//    }

    @Override
    public void setProperty(ShapeControlProperty property) {
        super.setProperty(property);
        borderColor = TypeUtil.objToStrNotNull(property.getStyle().getBorderColor());
        borderSize = TypeUtil.objToInt(property.getStyle().getBorderSize());
        fillColor = TypeUtil.objToStrNotNull(property.getStyle().getFillColor());
    }

    @Override
    public void refreshControlData() {
        super.refreshControlData();
        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(ColorUtil.parseColor(fillColor));
            gd.setStroke(borderSize, ColorUtil.parseColor(borderColor));
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            setBackgroundDrawable(gd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
