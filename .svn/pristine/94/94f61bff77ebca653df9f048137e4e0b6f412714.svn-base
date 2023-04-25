package com.sunmnet.mediaroom.brand.utils;

public class UrlUtil {

    public static String getFullHttpUrl(String hostUrl, String baseUrl) {
        if (baseUrl == null)
            return null;
        String lowerCase = baseUrl.toLowerCase();
        String url;
        if (lowerCase.startsWith("http://") || lowerCase.startsWith("https://")) {
            url = baseUrl;
        } else if (baseUrl.startsWith("/")) {
            url = hostUrl + baseUrl;
        } else {
            url = hostUrl + "/" + baseUrl;
        }
        return url;
    }

    public static boolean checkFullUrl(String suffixPath) {
        if (suffixPath != null) {
            String lowerCase = suffixPath.toLowerCase();
            if (lowerCase.startsWith("android.resource://")
                    || lowerCase.startsWith("res://")
                    || lowerCase.startsWith("file://")
                    || lowerCase.startsWith("http://")
                    || lowerCase.startsWith("https://")) {
                return true;
            }
        }
        return false;
    }
}
