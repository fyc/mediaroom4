package com.sunmnet.mediaroom.tabsp.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.sunmnet.mediaroom.common.BaseActivity;

import com.sunmnet.mediaroom.device.DeviceBuilder;
import com.sunmnet.mediaroom.tabsp.TabspApplication;
import com.sunmnet.mediaroom.tabsp.controll.service.UIDispatcher;

import java.util.Locale;

public abstract class AbstractDispatchActivity extends BaseActivity {
    protected UIDispatcher dispatcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 语言切换
     *
     * @param languages
     */
    public void switchLanguage(Locale languages) {
        // 获得res资源对象
        Resources resources = getResources();
// 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics metrics = resources.getDisplayMetrics();
// 获得配置对象
        Configuration config = resources.getConfiguration();
//区别17版本（其实在17以上版本通过 config.locale设置也是有效的，不知道为什么还要区别）
//在这里设置需要转换成的语言，也就是选择用哪个values目录下的strings.xml文件
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(languages);//设置简体中文
        } else {
            config.locale = languages;//设置简体中文
        }
        resources.updateConfiguration(config, metrics);
        TabspApplication.getInstance().getConfig().setLang(languages);
        TabspApplication.getInstance().upgrade();
        DeviceBuilder.initTypeName(getApplicationContext());
    }
}
