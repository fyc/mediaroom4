package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.interfaces.control.IBaseControl;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextStyle;
import com.sunmnet.mediaroom.brand.bean.control.base.PrefixSuffixTextControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.base.TextStyleControlProperty;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;
import com.sunmnet.mediaroom.brand.utils.ControlTextHelper;


public class BaseTextControl<P extends TextStyleControlProperty> extends TextView implements IBaseControl<P>, ITextStyle {

    protected String controlId;
    protected String controlType;
    protected int xAxis;
    protected int yAxis;
    protected int zIndex;
    protected int width;
    protected int height;

    protected String font;
    protected int controlTextSize;
    protected String controlTextColor;
    protected String textBackgroundColor;

    protected boolean underline;
    protected boolean italic;
    protected boolean bold;

    protected String alignment;

//    protected Map<String, Object> controlDataMap;
//    protected Map<String, Object> attrDataMap;

    protected String resourcePath;

    protected P property;


    public BaseTextControl(Context context) {
        super(context, null);
        init();
    }

    public BaseTextControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, null);
        setLayoutParams(layoutParams);
        init();
    }

    public BaseTextControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseTextControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        if (getLayoutParams() != null && getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
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
        if (getLayoutParams() != null && getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
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
        return null;
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
        ControlTextHelper.setControlTextStyle(this, property.getStyle());
        if (property.getAttr() instanceof PrefixSuffixTextControlAttr) {
            ControlTextHelper.setControlTextAttr(this, (PrefixSuffixTextControlAttr) property.getAttr());
        }
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
//        ControlTextHelper.setControlTextStyle(this, controlDataMap);
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

    @Override
    public void setFont(String font) {
        this.font = font;
        ControlTextHelper.setFont(this, font);
    }

    @Override
    public void setControlTextSize(int textSize) {
        this.controlTextSize = textSize;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    public void setControlTextColor(String textColor) {
        this.controlTextColor = textColor;
    }

    @Override
    public void setTextBackgroundColor(String textBackgroundColor) {
        if (TextUtils.isEmpty(textBackgroundColor))
            return;
        this.textBackgroundColor = textBackgroundColor;
        invalidate();
    }

    @Override
    public void setUnderline(boolean underline) {
        this.underline = underline;
        ControlTextHelper.setUnderline(this, underline);
    }

    @Override
    public void setItalic(boolean italic) {
        this.italic = italic;
        ControlTextHelper.setItalic(this, italic);
    }

    @Override
    public void setBold(boolean bold) {
        this.bold = bold;
        ControlTextHelper.setBold(this, bold);
    }

    @Override
    public void setAlignment(String alignment) {
        this.alignment = alignment;
        switch (alignment) {
            case ALIGN_LEFT:
                setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                break;
            case ALIGN_RIGHT:
                setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                break;
            case ALIGN_CENTER:
                setGravity(Gravity.CENTER);
                break;
        }
    }

    @Override
    public String getFont() {
        return font;
    }

    @Override
    public int getControlTextSize() {
        if (controlTextSize <= 0) {
            controlTextSize = (int) getTextSize();
        }
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

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null)
            text = "--";
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
            return;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        try {
            if (textBackgroundColor != null) {
                BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(ColorUtil.parseColor(textBackgroundColor));
                spannableStringBuilder.setSpan(backgroundColorSpan, 0, spannableStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置新文本时，如果长度改变了，初始化因自适应字体大小而改变的设置
        if (length() != spannableStringBuilder.length()) {
            setControlTextSize(getControlTextSize());
            setMaxLines(Integer.MAX_VALUE);
            setEllipsize(null);
        }
        super.setText(spannableStringBuilder, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getHeight() < getLineHeight() * getLineCount() || (getLineCount() == 1 && getWidth() < getPaint().measureText(getText().toString()))) {
            if (getTextSize() - 1 >= 12) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, (getTextSize() - 1));
                return;
            } else if (getEllipsize() == null) {
                setEllipsize(TextUtils.TruncateAt.END);
                setMaxLines(1);
            }
        }
        super.onDraw(canvas);
    }
}
