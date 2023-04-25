package com.sunmnet.mediaroom.brand.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by dengzl_pc on 2018/3/8.
 * 自定义radioGroup
 */

public class ImageTextRadioGroup extends LinearLayout implements View.OnClickListener {
    public ImageTextRadioGroup(Context context) {
        super(context);
    }

    public ImageTextRadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextRadioGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnSelectListener {
        public void onSelected(int id, View view);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    OnSelectListener listener;

    @Override
    public void addView(View child, int width, int height) {
        super.addView(child, width, height);
        child.setOnClickListener(this);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        child.setOnClickListener(this);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        child.setOnClickListener(this);
    }

    ImageTextRadio checked;

    public void reset() {
        if (checked != null) checked.setCheck(false);
        checked = null;
    }

    public void setCheckedByTag(Object tag) {
        View view = this.findViewWithTag(tag);
        if (view == null) return;
        if (checked != null)
            checked.setCheck(false);
        this.checked = (ImageTextRadio) view;
        this.checked.setCheck(true);
    }

    public void setCheckedById(int id) {

    }

    @Override
    public void onClick(View view) {
        if (checked != null)
            checked.setCheck(false);
        this.checked = (ImageTextRadio) view;
        this.checked.setCheck(true);
        if (listener != null) this.listener.onSelected(this.getId(), view);
    }
}
