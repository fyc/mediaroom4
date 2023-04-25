package com.sunmnet.mediaroom.brand.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;


public class DropEditText extends AppCompatTextView implements PopupWindow.OnDismissListener, View.OnClickListener {

    private Drawable mDrawable; // 显示的图
    private PopupWindow mPopupWindow; // 点击图片弹出的popWindow对象
    //private ListView mPopListView; // popWindow的布局
    private int mDropDrawableResId; // 下拉图标
    private int mRiseDrawableResID; // 上拉图标
    private View contentView;
    PopupWindow.OnDismissListener onDismissListener;

    public DropEditText(Context context) {
        this(context, null);
    }

    private boolean isShowingMenu = false;

    public void toggle() {
        if (isShowingMenu) {
            this.mPopupWindow.dismiss();
        } else showPopWindow();
    }

    public DropEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DropEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mDropDrawableResId = R.drawable.icon_drop_down; // 设置下拉图标
        mRiseDrawableResID = R.drawable.icon_pull_up; // 设置上拉图标
        showDropDrawable(); // 默认显示下拉图标
        contentView = new TextView(context);//默认使用一个文本
        ((TextView) contentView).setText("未设置contentView");
        setOnClickListener(this);
    }

    public void setContentView(int layoutId, PopupWindow.OnDismissListener listener) {
        setContentView(LayoutInflater.from(getContext()).inflate(layoutId, null, false), listener);
    }

    public void setContentView(View view, PopupWindow.OnDismissListener listener) {
        this.contentView = view;
        this.onDismissListener = listener;
    }

    boolean isInit = false;

    public void onApply() {
        if (!isInit) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    isInit = true;
                    showDropDrawable();
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }

    public View getContentView() {
        return this.contentView;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mPopupWindow = new PopupWindow(contentView, getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE)); // 设置popWindow背景颜色
            mPopupWindow.setFocusable(true); // 让popWindow获取焦点
            mPopupWindow.setOnDismissListener(this);
        }
    }

    private void showPopWindow() {
        this.isShowingMenu = true;
        mPopupWindow.showAsDropDown(this, 0, 5);
        showRiseDrawable();
    }

    private void showDropDrawable() {
        mDrawable = getResources().getDrawable(mDropDrawableResId);
        int width = getHeight() - 20;
        mDrawable.setBounds(0, 0, width, width);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }

    private void showRiseDrawable() {
        mDrawable = getResources().getDrawable(mRiseDrawableResID);
        int width = getHeight() - 20;
        mDrawable.setBounds(0, 0, width, width);
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawable, getCompoundDrawables()[3]);
    }

    @Override
    public void onDismiss() {
        this.isShowingMenu = false;
        showDropDrawable(); // 当popWindow消失时显示下拉图标
        if (this.onDismissListener != null) this.onDismissListener.onDismiss();
    }

    @Override
    public void onClick(View view) {
        this.toggle();
    }
}