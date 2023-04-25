package com.sunmnet.mediaroom.brand.utils;

import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitManager {

    private static Map<String, Retrofit> cache = new HashMap<>();

    public static Retrofit getRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null, true);
    }

    public static Retrofit getRetrofit(String baseUrl, OkHttpClient okHttpClient, boolean useCache) {
        Retrofit cacheRetrofit = cache.get(baseUrl);
        if (useCache && cacheRetrofit != null)
            return cacheRetrofit;
        Retrofit.Builder builder;
        if (cacheRetrofit == null) {
            builder = new Retrofit.Builder();
        } else {
            builder = cacheRetrofit.newBuilder();
        }

        if (okHttpClient != null) {
            builder.client(okHttpClient);
        }
        Retrofit newRetrofit = builder
                .baseUrl(baseUrl)//base的网络地址  baseUrl不能为空,且强制要求必需以 / 斜杠结尾
                .addConverterFactory(JacksonConverterFactory.create(JacksonUtil.getObjectMapper()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        if (useCache) {
            cache.put(baseUrl, newRetrofit);
        }
        return newRetrofit;
    }


    public static Retrofit getPLRetrofit() {
        return DeviceApp.getApp().isRegistered() ? getRetrofit(DeviceApp.getApp().getPlServerAddr_Slash()) : null;
    }

    public static Retrofit getPLRetrofit(OkHttpClient okHttpClient) {
        return DeviceApp.getApp().isRegistered() ? getRetrofit(DeviceApp.getApp().getPlServerAddr_Slash(), okHttpClient, false) : null;
    }

    public static Retrofit newRetrofit(String baseUrl, OkHttpClient okHttpClient) {
        return getRetrofit(baseUrl, okHttpClient, false);
    }

    public static Retrofit newRetrofit(String baseUrl, OkHttpClient okHttpClient, boolean isOverride) {
        return getRetrofit(baseUrl, okHttpClient, isOverride);
    }

}
