package com.sunmnet.mediaroom.brand.common.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtilCache {
    private static final String CHARSET_NAME = "UTF-8";
    //懒汉式
    private static OkHttpClient mOkHttpClient;
    //缓存路径
    private static final String FilePath = "OKHTTPCache";
    //缓存大小
    private static final int cacheSize = 10 * 1024 * 1024; // 10 MiB
    //链接缓存时间    单位秒
    private static final int conectTimeout = 10;
    //写入缓存时间    单位秒
    private static final int writeTimeout = 10;
    //读取缓存时间    单位秒
    private static final int readTimeout = 30;

    //默认缓存时间 单位秒
    private static final int cacheTime = 10;

    private OkHttpUtilCache() {
    }

    //初始化
    public static void initOkHttpUtil(Context context) {
        if (mOkHttpClient == null) {
            synchronized (OkHttpUtilCache.class) {
                if (mOkHttpClient == null) {
                    File cacheDirectory = new File(context.getExternalCacheDir(), FilePath);
                    Cache cache = new Cache(cacheDirectory, cacheSize);
                    mOkHttpClient = new OkHttpClient().newBuilder()
                            .cache(cache)       //最好不要更改cache目录
                            .connectTimeout(conectTimeout, TimeUnit.SECONDS)//设置相应时间
                            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                            .readTimeout(readTimeout, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }


    // 克隆一个client，更改链接时间等参数设置
    public static OkHttpClient OkHttpClientClone(Context context, int conTime, int WriteTime, int ReadTime) {
        if (mOkHttpClient == null) {
            initOkHttpUtil(context);
        }
        OkHttpClient build = mOkHttpClient.newBuilder()
                .connectTimeout(conTime, TimeUnit.SECONDS)
                .writeTimeout(WriteTime, TimeUnit.SECONDS)
                .readTimeout(ReadTime, TimeUnit.SECONDS)
                .build();

        return build;
    }

    public static OkHttpClient getOkHttpClient(Context context){
        if (mOkHttpClient == null) {
            initOkHttpUtil(context);
        }
        return mOkHttpClient;
    }

    /**
     * 异步请求数据，有回调参数
     */
    public static void get(Context context, String url, Callback callback) {
        get(context, url, callback, cacheTime);
    }

    /**
     * 异步请求数据，有回调参数
     */
    public static void get(Context context, String url, Callback callback, int cacheTime) {
        Request request = new Request.Builder()
                .url(url)
                .cacheControl(new CacheControl.Builder()
                        .maxAge(cacheTime, TimeUnit.SECONDS)
                        .build())
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil(context);
        }
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(callback);
    }

    /**
     * url参数较多时，可以使用map来拼接参数
     */
    public static void get(Context context, String url, Map<String, String> params, Callback callback) {
        get(context, url, params, callback, cacheTime);
    }

    /**
     * url参数较多时，可以使用map来拼接参数
     */
    public static void get(Context context, String url, Map<String, String> params, Callback callback, int cacheTime) {
        String urls = buildParams(url, params);
        get(context, urls, callback, cacheTime);
    }

    /**
     * 增加自定义client
     */
    public static void get(Context context, OkHttpClient client, String urls, Map<String, String> params, Map<String, String> headerMap, Callback callback, int cacheTime, boolean sync) {
        String url = buildParams(urls, params);
        Request.Builder builder = new Request.Builder();
        if (headerMap != null)
            for (String str : headerMap.keySet()) {
                if (headerMap.get(str) != null)
                    builder.addHeader(str, headerMap.get(str));
                else
                    builder.addHeader(str, "null");
            }
        Request request = builder
                .url(url)
                .cacheControl(new CacheControl.Builder()
                        .maxAge(cacheTime, TimeUnit.SECONDS)
                        .build())
                .build();
        OkHttpClient mClient;
        if (client != null) {
            mClient = client;
        } else {
            if (mOkHttpClient == null)
                initOkHttpUtil(context);
            mClient = mOkHttpClient;
        }
        Call newCall = mClient.newCall(request);
        if (sync) {
            try {
                Response response = newCall.execute();
                callback.onResponse(newCall, response);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(newCall, e);
            }
        } else {
            newCall.enqueue(callback);
        }
    }

    /**
     * 带header的get请求 如果url参数比较多，可以传递map，直观点
     */
    public static void get(Context context, String urls, Map<String, String> params, Map<String, String> headerMap, Callback callback, int cacheTime, boolean sync) {
        get(context, null, urls, params, headerMap, callback, cacheTime, false);
    }

    public static void get(Context context, String urls, Map<String, String> params, Map<String, String> headerMap, Callback callback, int cacheTime) {
        get(context, urls, params, headerMap, callback, cacheTime, false);
    }

    public static void get(Context context, String urls, Map<String, String> params, Map<String, String> headerMap, Callback callback) {
        get(context, urls, params, headerMap, callback, cacheTime);
    }


    /**
     * post请求，增加自定义client
     */
    public static void post(Context context, OkHttpClient client, String url, Map<String, String> params, Map<String, String> headerMap, Callback callback, int cacheTime, boolean sync) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null)
            for (String key : params.keySet()) {
                if (params.get(key) != null)
                    builder.add(key, params.get(key));
                else
                    builder.add(key, "null");
            }
        RequestBody body = builder.build();

        Request.Builder requestBuilder = new Request.Builder();

        if (headerMap != null)
            for (String str : headerMap.keySet()) {
                requestBuilder.addHeader(str, headerMap.get(str));
            }

        Request request = requestBuilder
                .url(url)
                .post(body)
                .cacheControl(new CacheControl.Builder()
                        .maxAge(cacheTime, TimeUnit.SECONDS)
                        .build())
                .build();
        OkHttpClient mClient;
        if (client != null) {
            mClient = client;
        } else {
            if (mOkHttpClient == null)
                initOkHttpUtil(context);
            mClient = mOkHttpClient;
        }
        Call newCall = mClient.newCall(request);
        if (sync) {
            try {
                Response response = newCall.execute();
                callback.onResponse(newCall, response);
            } catch (IOException e) {
                e.printStackTrace();
                callback.onFailure(newCall, e);
            }
        } else {
            newCall.enqueue(callback);
        }
    }

    /**
     * 简单的post请求
     */
    public static void post(Context context, String url, Map<String, String> params, Map<String, String> headerMap, Callback callback, int cacheTime, boolean sync) {
        post(context, null, url, params, headerMap, callback, cacheTime, sync);
    }

    public static void post(Context context, String url, Map<String, String> params, Map<String, String> headerMap, Callback callback, int cacheTime) {
        post(context, url, params, headerMap, callback, cacheTime, false);
    }

    public static void post(Context context, String url, Map<String, String> params, Callback callback) {
        post(context, url, params, null, callback, cacheTime);
    }

    public static void post(Context context, String url, Map<String, String> params, Callback callback, int cacheTime) {
        post(context, url, params, null, callback, cacheTime);
    }

    /**
     * 提交文件
     */
    public void postFile(Context context, String url, File file, Callback callback) {
        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil(context);
        }
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(callback);
    }


    /**
     * 拼接url参数
     */
    public static String buildParams(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (params == null || params.size() == 0) {
            return url;
        }
        url += (url.indexOf('?') == -1 ? '?' : '&');
        Set<String> keySet = params.keySet();
        StringBuilder urlBuilder = new StringBuilder(url);
        for (String key : keySet) {
            urlBuilder.append(key).append("=").append(params.get(key)).append("&");
        }
        url = urlBuilder.substring(0, urlBuilder.length() - 1);
        return url;
    }
}

