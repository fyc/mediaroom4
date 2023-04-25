package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;

import com.sunmnet.mediaroom.brand.common.utils.OkHttpUtilCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class OkHttpClientManager {

    private static Map<String, OkHttpClient> cache = new HashMap<>();

    public static OkHttpClient getHttpClient(Context context, String url) {
        HttpUrl httpUrl = HttpUrl.get(url);
        OkHttpClient client = cache.get(httpUrl.toString());
        if (client == null) {
            client = OkHttpUtilCache.getOkHttpClient(context).newBuilder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .cookieJar(new SimpleCookieJar()).build();
            cache.put(httpUrl.toString(), client);
        }
        return client;
    }

    public static void resetHttpClientCookie(String url) {
        HttpUrl httpUrl = HttpUrl.get(url);
        OkHttpClient client = cache.get(httpUrl.toString());
        if (client != null) {
            client.cookieJar().saveFromResponse(httpUrl, new ArrayList<Cookie>());
        }
    }

    static class SimpleCookieJar implements CookieJar {
        Map<String, List<Cookie>> cookieMap = new HashMap<>();

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            if (cookieMap != null && cookieMap.containsKey(httpUrl.toString())) {
                return cookieMap.get(httpUrl.toString());
            } else {
                List<Cookie> arrayList = new ArrayList<>();
                cookieMap.put(httpUrl.toString(), arrayList);
                return arrayList;
            }
        }

        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
            cookieMap.put(httpUrl.toString(), cookies);
        }

    }

}
