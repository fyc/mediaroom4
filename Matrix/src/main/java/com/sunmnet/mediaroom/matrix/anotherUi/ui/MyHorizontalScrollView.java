package com.sunmnet.mediaroom.matrix.anotherUi.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Create by WangJincheng on 2021/5/13
 * 自定义滑动控件
 */

public class MyHorizontalScrollView extends HorizontalScrollView {

    // 滑动监听器
    private ScrollChangedListener scrollChangedListener;

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.scrollChangedListener != null) {
            this.scrollChangedListener.scrollChanged(l, t, oldl, oldt);
        }
    }

    public ScrollChangedListener getScrollChangedListener() {
        return scrollChangedListener;
    }

    public void setScrollChangedListener(ScrollChangedListener _scrollChangedListener) {
        this.scrollChangedListener = _scrollChangedListener;
    }

    /**
     * 滑动监听接口
     */
    public interface ScrollChangedListener {
        /**
         * 滑动监听
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        void scrollChanged(int l, int t, int oldl, int oldt);
    }
}
