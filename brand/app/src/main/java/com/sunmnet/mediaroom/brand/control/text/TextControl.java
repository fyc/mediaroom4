package com.sunmnet.mediaroom.brand.control.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.control.base.BaseTextControl;
import com.sunmnet.mediaroom.brand.control.base.MultilineRollTextControl;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.bean.control.text.TextControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.text.TextControlProperty;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文本组件
 */
public class TextControl extends BaseFrameControl<TextControlProperty> implements ITextStyle {

    protected String font;
    protected int controlTextSize;
    protected String controlTextColor;
    protected String textBackgroundColor;

    protected boolean underline;
    protected boolean italic;
    protected boolean bold;

    protected String alignment;

    private List<Map<String, Object>> contents;

    private int textState;//文本状态。静态：0；动态：1

    private int rollSpeed;//滚动速度
    private String rollDirection;//滚动方向 UTD从上到下 DTU从下到上 LTR从左到右 RTL从右到左


    private BaseTextControl staticTextControl;
    private MultilineRollTextControl rollTextControl;

    public TextControl(Context context) {
        super(context);
    }

    public TextControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public TextControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//
//        if (controlDataMap.containsKey("style") && (controlDataMap.get("style") instanceof Map)) {
//            Map<String, Object> textStyle = (Map<String, Object>) controlDataMap.get("style");
//            setTextBackgroundColor(TypeUtil.objToStr(textStyle.get("backgroundColor")));
//            setControlTextSize(TypeUtil.objToInt(textStyle.get("fontSize")));
//            setControlTextColor(TypeUtil.objToStr(textStyle.get("color")));
//            setFont(TypeUtil.objToStrNotNull(textStyle.get("fontFamily")));
//            setUnderline(TypeUtil.objToBoolean(textStyle.get("underline")));
//            setItalic(TypeUtil.objToBoolean(textStyle.get("italic")));
//            setBold(TypeUtil.objToBoolean(textStyle.get("bold")));
//            setAlignment(TypeUtil.objToStrDef(textStyle.get("textAlign"), ITextStyle.ALIGN_CENTER));
//        }
//
//        textState = TypeUtil.objToInt(attrDataMap.get("textState"));
//
//        rollDirection = TypeUtil.objToStrDef(attrDataMap.get("rollDirection"), "DTU");
//        rollSpeed = TypeUtil.objToIntDef(attrDataMap.get("rollSpeed"), 5);
//        try {
//            contents = TypeUtil.castToMapList_SO(attrDataMap.get("contents"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void setProperty(TextControlProperty property) {
        super.setProperty(property);
        setTextBackgroundColor(property.getStyle().getBackgroundColor());
        setControlTextSize(property.getStyle().getFontSize());
        setControlTextColor(property.getStyle().getColor());
        setFont(property.getStyle().getFontFamily());
        setUnderline(property.getStyle().isUnderline());
        setItalic(property.getStyle().isItalic());
        setBold(property.getStyle().isBold());
        setAlignment(TypeUtil.objToStrDef(property.getStyle().getTextAlign(), ITextStyle.ALIGN_CENTER));

        textState = TypeUtil.objToInt(property.getAttr().getTextState());

        rollDirection = TypeUtil.objToStrDef(property.getAttr().getRollDirection(), "DTU");
        rollSpeed = TypeUtil.objToIntDef(property.getAttr().getRollSpeed(), 5);
        try {
            contents = new ArrayList<>();
            for (TextControlAttr.Content content : property.getAttr().getContents()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("text", content.getText());
                map.put("signature", content.getSignature());
                contents.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshControlData() {
        removeAllViews();
        if (textState == 0) {//静态
            if (staticTextControl == null) {
                staticTextControl = new BaseTextControl(getContext(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
//            ControlTextHelper.setControlTextStyle(staticTextControl, controlDataMap);
            ControlTextHelper.setControlTextStyle(staticTextControl, property.getStyle());
            staticTextControl.setText("");
            addView(staticTextControl);
            if (contents.size() > 0) {
                String text = TypeUtil.objToStr(contents.get(0).get("text"));
                String signature = TypeUtil.objToStr(contents.get(0).get("signature"));
                StringBuffer stringBuffer = new StringBuffer(text);
                if (!TextUtils.isEmpty(signature)) {
                    stringBuffer.append("\n").append("      ————").append(signature);
                }
                staticTextControl.setText(stringBuffer.toString());
            }
        } else {
            if (rollTextControl == null) {
                rollTextControl = new MultilineRollTextControl(getContext());
            }
//            if (controlDataMap.containsKey("style") && (controlDataMap.get("style") instanceof Map)) {
//                Map<String, Object> textStyle = TypeUtil.castToMap_SO(controlDataMap.get("style"));
//                rollTextControl.setTextBackgroundColor(TypeUtil.objToStr(textStyle.get("backgroundColor")));
//                rollTextControl.setControlTextSize(TypeUtil.objToInt(textStyle.get("fontSize")));
//                rollTextControl.setControlTextColor(TypeUtil.objToStr(textStyle.get("color")));
//                rollTextControl.setFont(TypeUtil.objToStrNotNull(textStyle.get("fontFamily")));
//                rollTextControl.setUnderline(TypeUtil.objToBoolean(textStyle.get("underline")));
//                rollTextControl.setItalic(TypeUtil.objToBoolean(textStyle.get("italic")));
//                rollTextControl.setBold(TypeUtil.objToBoolean(textStyle.get("bold")));
//                rollTextControl.setAlignment(TypeUtil.objToStrDef(textStyle.get("textAlign"), ITextStyle.ALIGN_CENTER));
//            }
            if (property.getStyle() != null) {
                rollTextControl.setTextBackgroundColor(property.getStyle().getBackgroundColor());
                rollTextControl.setControlTextSize(property.getStyle().getFontSize());
                rollTextControl.setControlTextColor(property.getStyle().getColor());
                rollTextControl.setFont(TypeUtil.objToStrNotNull(property.getStyle().getFontFamily()));
                rollTextControl.setUnderline(property.getStyle().isUnderline());
                rollTextControl.setItalic(property.getStyle().isItalic());
                rollTextControl.setBold(property.getStyle().isBold());
                rollTextControl.setAlignment(TypeUtil.objToStrDef(property.getStyle().getTextAlign(), ITextStyle.ALIGN_CENTER));
            }
            rollTextControl.setRollSpeed(rollSpeed);
            rollTextControl.setRollDirection(rollDirection);
            rollTextControl.setText(contents);
            addView(rollTextControl);
        }
    }

    @Override
    public void setFont(String font) {
        this.font = font;
        if (rollTextControl != null)
            rollTextControl.setFont(font);
        if (staticTextControl != null)
            staticTextControl.setFont(font);
    }

    @Override
    public void setControlTextSize(int textSize) {
        this.controlTextSize = textSize;
        if (rollTextControl != null)
            rollTextControl.setControlTextSize(textSize);
        if (staticTextControl != null)
            staticTextControl.setControlTextSize(textSize);
    }

    @Override
    public void setControlTextColor(String textColor) {
        this.controlTextColor = textColor;
        if (rollTextControl != null)
            rollTextControl.setControlTextColor(textColor);
        if (staticTextControl != null)
            staticTextControl.setControlTextColor(textColor);
    }

    @Override
    public void setTextBackgroundColor(String textBackgroundColor) {
        if (TextUtils.isEmpty(textBackgroundColor))
            return;
        this.textBackgroundColor = textBackgroundColor;
        if (rollTextControl != null)
            rollTextControl.setTextBackgroundColor(textBackgroundColor);
        if (staticTextControl != null)
            staticTextControl.setTextBackgroundColor(textBackgroundColor);
    }

    @Override
    public void setUnderline(boolean underline) {
        this.underline = underline;
        if (rollTextControl != null)
            rollTextControl.setUnderline(underline);
        if (staticTextControl != null)
            staticTextControl.setUnderline(underline);
    }

    @Override
    public void setItalic(boolean italic) {
        this.italic = italic;
        if (rollTextControl != null)
            rollTextControl.setItalic(italic);
        if (staticTextControl != null)
            staticTextControl.setItalic(italic);
    }

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
        if (rollTextControl != null)
            rollTextControl.setBold(bold);
        if (staticTextControl != null)
            staticTextControl.setBold(bold);
    }

    @Override
    public void setAlignment(String alignment) {
        this.alignment = alignment;
        if (rollTextControl != null)
            rollTextControl.setAlignment(alignment);
        if (staticTextControl != null)
            staticTextControl.setAlignment(alignment);
    }

    public void setRollSpeed(int rollSpeed) {
        this.rollSpeed = rollSpeed;
        if (rollTextControl != null)
            rollTextControl.setRollSpeed(rollSpeed);
    }

    public void setRollDirection(String rollDirection) {
        this.rollDirection = rollDirection;
        if (rollTextControl != null)
            rollTextControl.setRollDirection(rollDirection);
    }

    @Override
    public String getFont() {
        return font;
    }

    @Override
    public int getControlTextSize() {
        return controlTextSize;
    }

    @Override
    public String getControlTextColor() {
        return controlTextColor;
    }

    @Override
    public String getTextBackgroundColor() {
        return textBackgroundColor;
    }

    @Override
    public boolean isUnderline() {
        return underline;
    }

    @Override
    public boolean isItalic() {
        return italic;
    }

    @Override
    public boolean isBold() {
        return bold;
    }

    @Override
    public String getAlignment() {
        return alignment;
    }

    public int getRollSpeed() {
        return rollSpeed;
    }

    public String getRollDirection() {
        return rollDirection;
    }

    @Override
    public void onControlShow() {
        if (rollTextControl != null)
            rollTextControl.onControlShow();
        if (staticTextControl != null)
            staticTextControl.onControlShow();
    }

    @Override
    public void onControlHide() {
        if (rollTextControl != null)
            rollTextControl.onControlHide();
        if (staticTextControl != null)
            staticTextControl.onControlHide();
    }
}
