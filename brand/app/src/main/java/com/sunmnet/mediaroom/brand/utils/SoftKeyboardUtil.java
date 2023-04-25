package com.sunmnet.mediaroom.brand.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

public class SoftKeyboardUtil {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 强制显示软键盘
     */
    public static void showSoftKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = manager.isActive();//isOpen若返回true，则表示输入法打开
            if (!isOpen) {
                manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    /**
     * 切换软键盘状态，已显示则隐藏，反之则显示
     */
    public static void toggleSoftKeyboard(Context context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断是否触摸了该view
     */
    public static boolean isTouchView(View view, MotionEvent event) {
        if (view == null || event == null) {
            return false;
        }
        int[] leftTop = {0, 0};
        view.getLocationInWindow(leftTop);
        int left = leftTop[0];
        int top = leftTop[1];
        int bottom = top + view.getHeight();
        int right = left + view.getWidth();
        if (event.getRawX() > left && event.getRawX() < right
                && event.getRawY() > top && event.getRawY() < bottom) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否应该收起软键盘
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && v instanceof EditText) {
            return !isTouchView(v, event);
        }
        return false;
    }


    /**
     * 当点击其他View时隐藏软键盘
     *
     * @param activity
     * @param ev
     * @param excludeViews 点击这些View不会触发隐藏软键盘动作
     */
    public static void hideInputWhenTouchOtherView(Activity activity, MotionEvent ev, List<View> excludeViews) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (excludeViews != null && !excludeViews.isEmpty()) {
                for (int i = 0; i < excludeViews.size(); i++) {
                    if (isTouchView(excludeViews.get(i), ev)) {
                        return;
                    }
                }
            }
            View v = activity.getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }

        }
    }

}
