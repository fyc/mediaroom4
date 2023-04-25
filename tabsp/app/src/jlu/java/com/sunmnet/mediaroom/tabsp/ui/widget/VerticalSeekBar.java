package com.sunmnet.mediaroom.tabsp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Class to create a vertical slider
 */
public class VerticalSeekBar extends AppCompatSeekBar {

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    // 可触控标志
    private boolean touchEnable = true;

    public VerticalSeekBar(Context context) {
        super(context);
//        setOnFocusChangeListener(this::onFocusChange);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        setOnFocusChangeListener(this::onFocusChange);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setOnFocusChangeListener(this::onFocusChange);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);

        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isTouchEnable() || !isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onStartTrackingTouch();
                measureProgress(event);
                break;
            case MotionEvent.ACTION_MOVE:
                measureProgress(event);
                break;
            case MotionEvent.ACTION_UP:
                measureProgress(event);
                onStopTrackingTouch();
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
        mOnSeekBarChangeListener = l;
    }

    private void measureProgress(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    private void onStopTrackingTouch() {
        if(mOnSeekBarChangeListener!=null){
            mOnSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    private void onStartTrackingTouch() {
        if(mOnSeekBarChangeListener!=null){
            mOnSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    public boolean isTouchEnable() {
        return touchEnable;
    }

    public void setTouchEnable(boolean touchEnable) {
        this.touchEnable = touchEnable;
    }
}
