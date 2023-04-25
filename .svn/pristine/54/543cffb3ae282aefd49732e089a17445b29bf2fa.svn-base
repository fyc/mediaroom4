package com.sunmnet.mediaroom.brand.control.button;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sunmnet.mediaroom.brand.bean.control.button.FaceAttendControlProperty;
import com.sunmnet.mediaroom.brand.bean.event.ClickEvent;
import com.sunmnet.mediaroom.brand.control.base.BaseTextControl;
import com.sunmnet.mediaroom.brand.enums.ClickEventType;
import com.sunmnet.mediaroom.brand.utils.ColorUtil;
import com.sunmnet.mediaroom.brand.utils.ControlBaseHelper;

import org.greenrobot.eventbus.EventBus;

public class FaceAttendControl extends BaseTextControl<FaceAttendControlProperty> implements View.OnClickListener {

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public FaceAttendControl(Context context) {
        super(context);
    }

    public FaceAttendControl(Context context, ViewGroup.MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public FaceAttendControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceAttendControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setProperty(FaceAttendControlProperty property) {
        ControlBaseHelper.setControlStyle(this, property.getStyle());
        setControlTextSize(property.getStyle().getFontSize());
        setControlTextColor(property.getStyle().getColor());
        setTextColor(ColorUtil.parseColor(getControlTextColor()));
        setFont(property.getStyle().getFontFamily());
        setUnderline(property.getStyle().isUnderline());
        setItalic(property.getStyle().isItalic());
        setBold(property.getStyle().isBold());
        setAlignment(property.getStyle().getTextAlign());
        Drawable drawable = new ButtonControlDrawable().getDrawable(property.getStyle().getBackgroundColor());
        setBackground(drawable);
        setText("人脸签到");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onControlShow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onControlHide();
    }

    @Override
    public void onControlShow() {
        this.setOnClickListener(this);
    }

    @Override
    public void onControlHide() {
        this.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new ClickEvent(ClickEventType.FACE_ATTEND));
    }
}
