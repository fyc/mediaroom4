package com.sunmnet.mediaroom.brand.control.button;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.utils.ColorUtil;

public class ButtonControlDrawable {

    private static final int[] STATE_PRESSED = {android.R.attr.state_pressed};
    private static final int[] STATE_SELECTED = {android.R.attr.state_selected};
    private static final int[] STATE_CHECKED = {android.R.attr.state_checked};

    public Drawable getDrawable(String color) {
        if (TextUtils.isEmpty(color)) {
            return null;
        }
        int normalColor = ColorUtil.parseColor(color);
        int pressedColor = Color.parseColor("#2E2E2E");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float[] outerR = new float[]{8, 8, 8, 8, 8, 8, 8, 8};
            RoundRectShape shape = new RoundRectShape(outerR, null, null);
            ShapeDrawable drawable = new ShapeDrawable(shape);
            drawable.getPaint().setColor(normalColor);
            drawable.getPaint().setStyle(Paint.Style.FILL);
            return new RippleDrawable(ColorStateList.valueOf(pressedColor), drawable, null);
        } else {
            ColorDrawable normal = new ColorDrawable(normalColor);
            ColorDrawable pressed = new ColorDrawable(pressedColor);
            StateListDrawable drawable = new StateListDrawable();
            drawable.addState(STATE_PRESSED, pressed);
            drawable.addState(STATE_SELECTED, pressed);
            drawable.addState(STATE_CHECKED, pressed);
            drawable.addState(new int[]{}, normal);
            return drawable;
        }
    }
}
