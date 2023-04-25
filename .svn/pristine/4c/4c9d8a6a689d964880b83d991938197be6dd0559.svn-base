package com.sunmnet.mediaroom.brand.common.network;

import android.content.Context;

import com.sunmnet.mediaroom.brand.common.utils.OkHttpUtilCache;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class EasyOkHttp {

    private String url;
    private Map<String, String> params;
    private Map<String, String> headerMap;
    private int cacheTime;
    private Callback callback;
    private Context context;
    private boolean sync = false;
    private OkHttpClient client;

    private EasyOkHttp(Context context) {
        this.context = context;
    }

    public static EasyOkHttp create(Context context) {
        return new EasyOkHttp(context);
    }

    public EasyOkHttp url(String url) {
        this.url = url;
        return this;
    }

    public EasyOkHttp header(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        return this;
    }

    public EasyOkHttp params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public EasyOkHttp cacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
        return this;
    }

    public EasyOkHttp callback(Callback callback) {
        this.callback = callback;
        return this;
    }

    /**
     *  阻塞线程，同步请求数据
     */
    public EasyOkHttp sync() {
        sync = true;
        return this;
    }

    /**
     * 不阻塞线程，异步请求数据
     */
    public EasyOkHttp async() {
        sync = false;
        return this;
    }

    /**
     * 不阻塞线程，异步请求数据
     */
    public EasyOkHttp client(OkHttpClient client) {
        this.client = client;
        return this;
    }

    public void post() {
        OkHttpUtilCache.post(context, client, url, params, headerMap, callback, cacheTime, sync);
    }

    public void get() {
        OkHttpUtilCache.get(context, client, url, params, headerMap, callback, cacheTime, sync);
    }
}
