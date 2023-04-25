package com.sunmnet.mediaroom.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.sunmnet.mediaroom.common.interfaces.UIInterface;
import com.sunmnet.mediaroom.common.tools.ThreadUtils;

public abstract class BaseFragment extends Fragment {
    protected UIInterface uiInterface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof  UIInterface){
            uiInterface= (UIInterface) getActivity();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.uiInterface=null;
    }
    /**
     * 异步线程执行初始化
     * */
    protected void initialize(Runnable runnable){
        ThreadUtils.execute(runnable);
    }
    /**
     * 在主线程中初始化
     * */
    protected void initialize(){}
   /* public abstract   <T> void bindData(T t);
    @Override
    public <T> void binding(final T t) {
        if (Looper.myLooper() == Looper.getMainLooper()){
            bindData(t);
        }else if (contentView!=null){
            contentView.post(new Runnable() {
                @Override
                public void run() {
                    bindData(t);
                }
            });
        }
    }
    @Override
    public void onBindError(String message){

    }*/
}
