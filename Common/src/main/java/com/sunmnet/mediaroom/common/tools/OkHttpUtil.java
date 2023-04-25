package com.sunmnet.mediaroom.common.tools;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dengzl_pc on 2018/2/27.
 */

public class OkHttpUtil {
    private static final String CHARSET_NAME = "UTF-8";
    //懒汉式
    private static OkHttpClient mOkHttpClient;
    //缓存路径
    private static final String FilePath = "OKHTTPCacheFile";
    //缓存大小
    private static final int cacheSize = 10 * 1024 * 1024; // 10 MiB
    //链接缓存时间    单位秒
    private static final int conectTimeout = 10;
    //写入缓存时间    单位秒
    private static final int writeTimeout = 10;
    //读取缓存时间    单位秒
    private static final int readTimeout = 30;


    private OkHttpUtil() {
    }

    //初始化
    public static void initOkHttpUtil() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (mOkHttpClient == null) {
                    File cacheDirectory = new File(FilePath);
                    Cache cache = new Cache(cacheDirectory, cacheSize);
                    mOkHttpClient = new OkHttpClient().newBuilder().hostnameVerifier(new HostnameVerifier() {

                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).cache(cache)       //最好不要更改cache目录
                            .connectTimeout(conectTimeout, TimeUnit.SECONDS)//设置相应时间
                            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                            .readTimeout(readTimeout, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }


    // 克隆一个client，更改链接时间等参数设置
    public static OkHttpClient OkHttpClientClone(int conTime, int WriteTime, int ReadTime) {
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        OkHttpClient build = mOkHttpClient.newBuilder()
                .connectTimeout(conTime, TimeUnit.SECONDS)
                .writeTimeout(WriteTime, TimeUnit.SECONDS)
                .readTimeout(ReadTime, TimeUnit.SECONDS)
                .build();

        return build;
    }


    /**
     * 异步请求数据，有回调参数
     *
     * @param url
     * @param callback
     */
    public static void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(callback);
    }

    /**
     * url参数较多时，可以使用map来拼接参数
     *
     * @param url
     * @param headerMap
     * @param callback
     */
    public static void get(String url, Map<String, String> params, Callback callback) {
        String urls = buildParams(url, params);
        get(urls, callback);
    }


    /**
     * 带header的get请求 如果url参数比较多，可以传递map，直观点
     *
     * @param urls
     * @param params
     * @param headerMap
     * @param callback
     */
    public static void get(String urls, Map<String, String> params, Map<String, String> headerMap, Callback callback) {
        String url = buildParams(urls, params);
        Request.Builder builder = new Request.Builder();
        for (String str : headerMap.keySet()) {
            builder.addHeader(str, headerMap.get(str));
        }
        Request request = builder
                .url(url)
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(callback);
    }


    /**
     * 简单的post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void post(String url, Map<String, String> params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        RequestBody body = builder.build();
        Request requeset = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call newCall = mOkHttpClient.newCall(requeset);
        newCall.enqueue(callback);

    }

    /**
     * 提交文件
     *
     * @param url
     * @param file
     * @param callback
     */
    public static void postFile(String url, File file, Callback callback) {
        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(callback);
    }

    public static void postFile(String url, File file, Map<String, String> params, Map<String, String> headers, Callback callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key));
            }
        }
        builder.setType(MultipartBody.FORM).addFormDataPart("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        Request.Builder reqBody = new Request.Builder();
        if (headers != null) {
            for (String key : headers.keySet()) {
                reqBody.addHeader(key, headers.get(key));
            }
        }
        Request request = reqBody.url(url).post(builder.build()).build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call newCall = mOkHttpClient.newCall(request);
        newCall.enqueue(callback);
    }

    /**
     * 拼接url参数
     *
     * @param url
     * @param params
     * @return
     */
    public static String buildParams(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (params == null || params.size() == 0) {
            return url;
        }
        url += "?";
        Set<String> keySet = params.keySet();
        Iterator<String> itr = keySet.iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            url += key + "=" + params.get(key) + "&";
        }
        url = url.substring(0, url.length() - 1);
        return url;
    }

    public static Response post(final String url, final Map<String, String> values, final Map<String, String> headers) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (values != null) {
            Iterator<String> keys = values.keySet().iterator();
            while (keys.hasNext()) {
                String next = keys.next();
                builder.add(next, values.get(next));
            }
        }

        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Request.Builder requestBuilder = new Request.Builder().url(url).post(builder.build());
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.build();
        Call call = mOkHttpClient.newCall(request);
        return call.execute();
    }

    public static Response postJson(final String url, String jsonStr) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call call = mOkHttpClient.newCall(request);
        return call.execute();
    }

    public static Response postJson(final String url, String jsonStr, Map<String, String> headerMap) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        body.contentType().charset(null);
        Request.Builder builder = new Request.Builder();
        builder.url(url).post(body);
        if (headerMap != null) {
            for (String str : headerMap.keySet()) {
                builder.addHeader(str, headerMap.get(str));
            }
        }
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call call = mOkHttpClient.newCall(builder.build());
        return call.execute();
    }

    /**
     * 带header的get请求 如果url参数比较多，可以传递map，直观点
     *
     * @param urls
     * @param params
     * @param headerMap
     */
    public static Response get(String urls, Map<String, String> params, Map<String, String> headerMap) throws IOException {
        String url = buildParams(urls, params);
        Request.Builder builder = new Request.Builder();
        if (headerMap != null) {
            for (String str : headerMap.keySet()) {
                builder.addHeader(str, headerMap.get(str));
            }
        }
        Request request = builder
                .url(url)
                .build();
        if (mOkHttpClient == null) {
            initOkHttpUtil();
        }
        Call newCall = mOkHttpClient.newCall(request);
        return newCall.execute();
    }
    public static interface DownloadListener{
        public void onDownloading(int progress);
        public void onDownloadFailed(Exception e);
        public void onDownloadFailed(String msg);
        public void onDownloadSuccess(File file);
    }
    public static void downloadFileByGet(String urls, Map<String, String> params, Map<String, String> headerMap,String savingDirector,String destFileName,DownloadListener listener) {
        try{
            Response response=get(urls, params, headerMap);
            if (response.isSuccessful()){
                handleResponse(response,listener,savingDirector,destFileName);
            }else {
                listener.onDownloadFailed("文件获取失败!不存在文件:"+urls);
            }

        }catch (Exception e){
            listener.onDownloadFailed(e);
        }

    }
    private static void handleResponse(Response response,DownloadListener listener,String destFileDir,String destFileName){
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        //储存下载文件的目录
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, destFileName);
        if (file.exists()) file.delete();
        try {

            is = response.body().byteStream();
            long total = response.body().contentLength();
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                //下载中更新进度条
                listener.onDownloading(progress);
            }
            fos.flush();
            //下载完成
            listener.onDownloadSuccess(file);
        } catch (Exception e) {
            listener.onDownloadFailed(e);
            listener.onDownloadFailed("数据读取或写入异常");
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
        }
    }
    public static void downloadFileByPost(String urls, Map<String, String> params, Map<String, String> headerMap,String savingDirector,String destFileName,DownloadListener listener)  {
        try{
            Response response=post(urls, params, headerMap);
            handleResponse(response,listener,savingDirector,destFileName);
        }catch (Exception e){
            listener.onDownloadFailed(e);
        }
    }
    public static void cancelAllCall() {
        if (mOkHttpClient == null) {
            return;
        }
    }
}

