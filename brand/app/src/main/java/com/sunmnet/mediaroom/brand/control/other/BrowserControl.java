package com.sunmnet.mediaroom.brand.control.other;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.brand.control.base.BaseFrameControl;
import com.sunmnet.mediaroom.brand.control.base.HandlerTimerTask;
import com.sunmnet.mediaroom.brand.bean.control.other.BrowserControlAttr;
import com.sunmnet.mediaroom.brand.bean.control.other.BrowserControlProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class BrowserControl extends BaseFrameControl<BrowserControlProperty> {

    private WebView webView;

    private int carousel = 10;//轮播时间 单位：秒

    private List<Map<String, Object>> urls;

    private int position = 0;//当前播放

    private Timer timer;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (urls != null && urls.size() > 0) {
                int cur = (position + 1) % urls.size();
                if (cur != position) {
                    refreshControlData();
                }
            }
        }
    };

    public BrowserControl(Context context) {
        super(context);
    }

    public BrowserControl(Context context, MarginLayoutParams layoutParams) {
        super(context, layoutParams);
    }

    public BrowserControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        super.init();
        webView = new WebView(getContext());
        webView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //webview缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false;
                }
                try {
                    if (getContext() != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        getContext().startActivity(intent);
                    }
                } catch (Exception e) {
                    RunningLog.error(e);
                }
                return true;
            }
        });

        removeAllViews();
        addView(webView);
    }


//    @Override
//    public void setControlDataMap(Map<String, Object> dataMap) {
//        super.setControlDataMap(dataMap);
//        urls = TypeUtil.castToMapList_SO(attrDataMap.get("urls"));
//        carousel = TypeUtil.objToIntDef(attrDataMap.get("carousel"), 10);
//    }

    @Override
    public void setProperty(BrowserControlProperty property) {
        super.setProperty(property);
        urls = new ArrayList<>();
        for (BrowserControlAttr.Url url : property.getAttr().getUrls()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("path", url.getPath());
            urls.add(map);
        }
        carousel = TypeUtil.objToIntDef(property.getAttr().getCarousel(), 10);
    }

    @Override
    public void refreshControlData() {
        if (urls == null || urls.size() == 0)
            return;
        position = position % urls.size();
        String path = TypeUtil.objToStr(TypeUtil.castToMap_SO(urls.get(position)).get("path"));
        String url = path.toLowerCase();
        if (url.startsWith("http://") || url.startsWith("https://"))
            webView.loadUrl(path);
        else
            webView.loadUrl("http://" + path);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onControlShow();
    }

    @Override
    public void onControlShow() {
        if (timer != null)
            onControlHide();
        try {
            if (carousel > 0) {
                timer = new Timer();
                timer.schedule(new HandlerTimerTask(runnable), carousel * 1000, carousel * 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onControlHide();
    }

    @Override
    public void onControlHide() {
        if(timer == null)
            return;
        try {
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
