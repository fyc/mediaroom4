package com.sunmnet.mediaroom.brand.interfaces;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;

import com.sunmnet.mediaroom.brand.MainActivity;

/**
 * Created by dengzl on 2019/5/1.
 */

public interface IFlavorMaker {
    public static final String SCREEN_SLEEP_TAG="SLEEP_SCREEN";
    /***
     * 根据flavor初始化通信协议，基于滁州学院需要增加屏幕保护时间做此操作
     */
    public void initProtocol();
    /**
     * 旧版本中由于存在绑定onTouch事件
     * */
    public void setTouchListener(View view, View.OnTouchListener onTouchListener);
    /**
     * 旧版本中由于存在绑定dispatchTouchEvent事件，导致onClick时间出现问题
     * */
    public boolean dispatchTouchEvent(MotionEvent ev, MainActivity activity);
    public void setScreenSleep(Context context, int seconds);
    public void setScreenSleep(Context context);
    public void setActivity(FragmentActivity activity);
    public void onActivityPause();
    public void onActivityResume();
    public void onActivityDestroy();
    public void initControl();

    public void onAppCreate();
}
