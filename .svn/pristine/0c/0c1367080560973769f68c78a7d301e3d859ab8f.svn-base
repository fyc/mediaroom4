package com.sunmnet.mediaroom.brand.utils;

import android.os.Build;
import android.view.View;
import android.view.Window;

public class DisplayUtil {

    public static void hideNavigationBar(Window window) {
        if (window == null)
            return;
        View decorView = window.getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            decorView.setVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                int uiOptions1 = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                if (visibility != uiOptions1 && decorView.isAttachedToWindow()) {
                    decorView.setSystemUiVisibility(uiOptions1);
                }
            });
        }
    }

}
