package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by dengzl_pc on 2018/3/16.
 * 自适应宽度文本
 */

@SuppressLint("AppCompatCustomView")
public class AutoTextView extends TextView {
    public AutoTextView(Context context) {
        super(context);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        autoFitTextSize();
    }

    private void autoFitTextSize() {
        Paint p = getPaint();
        p.setTypeface(getTypeface());
        p.setTextSize(getTextSize());

        float needWidth = getPaddingLeft() + getPaddingRight() + p.measureText(getText().toString());
        if (needWidth > getWidth()) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize() - 0.5f);
            autoFitTextSize();
        }
    }
}
