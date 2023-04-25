package com.sunmnet.mediaroom.common.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public final class ToastUtil {

    private final static Handler mHandler = new Handler(Looper.getMainLooper());

    private static WeakReference<Toast> toastWeakReference;

    public static void show(final Context context, final CharSequence text, final boolean cancelLast) {
        if (Thread.currentThread() != mHandler.getLooper().getThread()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (cancelLast && toastWeakReference != null && toastWeakReference.get() != null) {
                        toastWeakReference.get().cancel();
                    }
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    toastWeakReference = new WeakReference<>(toast);
                    toast.show();
                }
            });
        } else {
            if (cancelLast && toastWeakReference != null && toastWeakReference.get() != null) {
                toastWeakReference.get().cancel();
            }
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toastWeakReference = new WeakReference<>(toast);
            toast.show();
        }
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, true);
    }

    public static void show(Context context, int text) {
        show(context, context.getResources().getText(text), true);
    }

    public static void show(Context context, int text, boolean cancelLast) {
        show(context, context.getResources().getText(text), cancelLast);
    }
}
