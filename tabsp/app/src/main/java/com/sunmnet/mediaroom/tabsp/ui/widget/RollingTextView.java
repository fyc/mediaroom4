package com.sunmnet.mediaroom.tabsp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class RollingTextView extends TextView {
    public RollingTextView(Context context) {
        super(context);
    }

    public RollingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RollingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean isFocused() {
        return true;
    }
}
