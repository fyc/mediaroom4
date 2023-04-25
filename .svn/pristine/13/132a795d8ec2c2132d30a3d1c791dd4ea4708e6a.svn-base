package com.sunmnet.mediaroom.brand.view;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;

public class ChildVisibilityGridLayout extends GridLayout {

    View[] mChild = null;

    public ChildVisibilityGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ChildVisibilityGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChildVisibilityGridLayout(Context context) {
        super(context);
    }

    private void arrangeElements() {
        mChild = new View[getChildCount()];
        for (int i = 0; i < getChildCount(); i++) {
            mChild[i] = getChildAt(i);
        }
        removeAllViews();
        for (int i = 0; i < mChild.length; i++) {
            if (mChild[i].getVisibility() != GONE)
                addView(mChild[i]);
        }
        for (int i = 0; i < mChild.length; i++) {
            if (mChild[i].getVisibility() == GONE)
                addView(mChild[i]);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        arrangeElements();
        super.onLayout(changed, left, top, right, bottom);

    }

}
