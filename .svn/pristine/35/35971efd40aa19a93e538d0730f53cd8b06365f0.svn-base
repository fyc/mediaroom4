package com.sunmnet.mediaroom.brand.common.network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sunmnet.mediaroom.common.tools.TypeUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class EasyCallback implements Callback {

    private static final int CALLBACK_SUCCESSFUL = 0x01;
    private static final int CALLBACK_FAILED = 0x02;
    private static Handler mHandler;
    private boolean runOnUIThread = true;


    public EasyCallback() {
        //mHandler = new UIHandler();
    }

    /**
     * 使用handler，令callback在主线程里运行
     */
    public EasyCallback runOnUiThread() {
        runOnUIThread = true;
        return this;
    }

    /**
     * 不使用handler，callback将在原线程里运行
     */
    public EasyCallback runOnCurrentThread() {
        runOnUIThread = false;
        return this;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (runOnUIThread) {
            Message message = Message.obtain();
            message.what = CALLBACK_FAILED;
//            message.obj = e.getMessage();
            message.obj = new CallbackResult(this, e.getMessage());
            getHandler().sendMessage(message);
        } else
            onFail(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            if (runOnUIThread) {
                Message message = Message.obtain();
                if (response.isSuccessful()) {
                    message.what = CALLBACK_SUCCESSFUL;
                    message.obj = new CallbackResult(this, response.body().string());
                } else {
                    message.what = CALLBACK_FAILED;
                    message.obj = new CallbackResult(this, "code:" + response.code()
                            + " , message:" + response.message()
                            + " , body:" + response.body().string());
                }
                getHandler().sendMessage(message);
            } else {
                if (response.isSuccessful()) {
                    onSuccess(response.body().string());
                } else {
                    onFail("code:" + response.code()
                            + " , message:" + response.message()
                            + " , body:" + response.body().string());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (runOnUIThread) {
                Message message = Message.obtain();
                message.what = CALLBACK_FAILED;
//                message.obj = e.getMessage();
                message.obj = new CallbackResult(this, e.getMessage());
                getHandler().sendMessage(message);
            } else
                onFail(e.getMessage());
        }
    }

    protected abstract void onSuccess(String result);

    protected abstract void onFail(String errorMsg);

    private static Handler getHandler() {
        synchronized (EasyCallback.class) {
            if (mHandler == null) {
                mHandler = new UIHandler();
            }
            return mHandler;
        }
    }

    private static class UIHandler extends Handler {

        //private WeakReference<EasyCallback> mWeakReference;

        UIHandler() {
            super(Looper.getMainLooper());
            //mWeakReference = new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            CallbackResult result = (CallbackResult) msg.obj;
            switch (msg.what) {
                case CALLBACK_SUCCESSFUL: {
//                    String str = (String) msg.obj;
//                    EasyCallback callback = mWeakReference.get();
//                    if (callback != null) {
//                        callback.onSuccess(str);
//                    }
                    result.mCallback.onSuccess(TypeUtil.objToStr(result.mData));
                    break;
                }
                case CALLBACK_FAILED: {
//                    String str = (String) msg.obj;
//                    EasyCallback callback = mWeakReference.get();
//                    if (callback != null) {
//                        callback.onFail(str);
//                    }
                    result.mCallback.onFail(TypeUtil.objToStr(result.mData));
                    break;
                }
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private static class CallbackResult {
        final EasyCallback mCallback;
        final String mData;

        CallbackResult(EasyCallback mCallback, String data) {
            this.mCallback = mCallback;
            mData = data;
        }
    }
}
