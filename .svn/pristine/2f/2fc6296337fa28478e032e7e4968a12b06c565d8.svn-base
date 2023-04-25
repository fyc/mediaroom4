package com.sunmnet.mediaroom.brand.control.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.interfaces.control.ITextPrefixSuffix;
import com.sunmnet.mediaroom.brand.bean.control.base.TextStyleControlProperty;

/**
 * 带前后缀的文本类组件
 */
public class PrefixSuffixTextControl<P extends TextStyleControlProperty> extends BaseTextControl<P> implements ITextPrefixSuffix {

    protected String prefixText, suffixText;

    public PrefixSuffixTextControl(Context context) {
        super(context);
    }

    public PrefixSuffixTextControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public PrefixSuffixTextControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PrefixSuffixTextControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void setPrefixText(String prefixText) {
        this.prefixText = prefixText;
    }

    @Override
    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
    }

    @Override
    public String getPrefixText() {
        return prefixText;
    }

    @Override
    public String getSuffixText() {
        return suffixText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text == null)
            text = "--";
//        if (text != null)
            text = TypeUtil.objToStrNotNull(prefixText) + text + TypeUtil.objToStrNotNull(suffixText);
        super.setText(text, type);
    }
}
