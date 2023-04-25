package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by dengzl_pc on 2017/12/26.
 * 带有对角线的自定义控件
 */

public class DiagonalView extends LinearLayout {
    public DiagonalView(Context context) {
        super(context);
        init();
    }

    public DiagonalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagonalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        TextView tv = new TextView(getContext());
        tv.setText("输出");
        tv.setPadding(10, 0, 0, 10);
        tv.setTextSize(18);
        tv.setTextColor(Color.WHITE);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        tv.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        this.addView(tv, params);
        tv = new TextView(getContext());
        tv.setText("输入");
        params = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        tv.setPadding(0, 10, 10, 0);
        tv.setGravity(Gravity.RIGHT | Gravity.TOP);
        tv.setTextSize(18);
        tv.setTextColor(Color.WHITE);
        this.addView(tv, params);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        setWillNotDraw(false);
    }

    protected Paint paint;

    protected void paint(Canvas canvas) {
        paint.setStrokeWidth(2.5f);
        //上边框
        canvas.drawLine(0, 0, getWidth(), 0, paint);
        //左边框
        canvas.drawLine(0, 0, 0, getHeight(), paint);
        canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint(canvas);
        //canvas.drawLine(0,0,0,getHeight(),paint);
        //canvas.drawLine(0,0,getWidth(),0,paint);
        //canvas.drawLine(getWidth(),0,getWidth(),getHeight(),paint);
        //canvas.drawLine(0,getHeight(),getWidth(),getHeight(),paint);

    }
}
