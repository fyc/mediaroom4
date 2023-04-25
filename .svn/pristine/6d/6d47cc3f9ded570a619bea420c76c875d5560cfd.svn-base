package com.sunmnet.mediaroom.tabsp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.sunmnet.mediaroom.common.tools.CommonUtil;
import com.sunmnet.mediaroom.tabsp.R;

public class DimmerPowerView extends View {
    Paint fullPaint, emptyPaint;
    int power;
    int columnCount = 5;
    int columnWidth = 0;
    int fullPaintCount = 0;
    int maxValue = 100;

    public DimmerPowerView(Context context) {
        super(context);
        fullPaint = new Paint();
        emptyPaint = new Paint();
        initPaint(emptyPaint, CommonUtil.getColorByAttribute(context, R.attr.dimmer_power_less));
        initPaint(fullPaint, CommonUtil.getColorByAttribute(context, R.attr.colorPrimary));
        /*initPaint(emptyPaint, Color.RED);
        initPaint(fullPaint, Color.GREEN);*/
    }

    public DimmerPowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        fullPaint = new Paint();
        emptyPaint = new Paint();
        initPaint(emptyPaint, CommonUtil.getColorByAttribute(context, R.attr.dimmer_power_less));
        initPaint(fullPaint, CommonUtil.getColorByAttribute(context, R.attr.colorPrimary));
    }

    public DimmerPowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        fullPaint = new Paint();
        emptyPaint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DimmerPowerView, defStyleAttr, 0);
        int color = typedArray.getColor(R.styleable.DimmerPowerView_power_full, CommonUtil.getColorByAttribute(context, R.attr.colorPrimary));
        initPaint(fullPaint, color);
        color = typedArray.getColor(R.styleable.DimmerPowerView_power_empty, CommonUtil.getColorByAttribute(context, R.attr.device_off_background_color));
        initPaint(emptyPaint, color);
        typedArray.recycle();
    }

    public void plus(int value) {
        int cache = this.fullPaintCount + value;
        if (cache < 0)
            this.setPower(0);
        else if (cache > columnCount)
            this.setPower(maxValue);
        else
            this.setPower(cache * (maxValue / columnCount));
    }

    public void initPaint(Paint paint, int color) {
        paint.setColor(color);
        paint.setAntiAlias(false);
    }

    public void setPower(int value) {
        this.power = value;
        int column = maxValue / columnCount;//每格占用的值
        fullPaintCount = value / column;//显示的格数
        this.postInvalidate();
    }

    public void setPower(String value) {
        setPower(TextUtils.isDigitsOnly(value) ? Integer.parseInt(value) : 0);
    }

    public int getPower() {
        return this.power;
    }

    public boolean isOpened() {
        return this.fullPaintCount > 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measure(widthMeasureSpec, true);
        columnWidth = Math.round(width / (columnCount * 1.0f) / (2 * 1.0f));
        this.fullPaint.setStrokeWidth(columnWidth);
        this.emptyPaint.setStrokeWidth(columnWidth);
        int height = measure(heightMeasureSpec, false);
        setMeasuredDimension(width, height);
    }

    protected int measure(int measureSpec, boolean WOH) {
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        int measured;
        if (mode == MeasureSpec.EXACTLY) {
            measured = size;
        } else {
            int measureMinimum = WOH ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();

            // 根据内容计算最小值
            // measureMinimum = Math.max(measureMinimum, MIN_CONTENT_SIZE);

            if (WOH) {
                measureMinimum = Math.max(measureMinimum, measureMinimum + getPaddingLeft() + getPaddingRight());
            } else {
                measureMinimum = Math.max(measureMinimum, measureMinimum + getPaddingTop() + getPaddingBottom());
            }
            measured = measureMinimum;
            if (mode == MeasureSpec.AT_MOST) {
                measured = Math.min(measured, size);
            }
        }
        return measured;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = this.fullPaint;
        int left = this.getPaddingLeft() + columnWidth / 2;
        for (int i = 0; i < this.columnCount; i++) {
            if (i >= fullPaintCount) paint = emptyPaint;
            canvas.drawLine(left, 0, left, this.getHeight(), paint);
            left = (int) (left + paint.getStrokeWidth());
            left = left + columnWidth;
        }
    }
}
